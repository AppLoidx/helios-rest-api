package com.apploidxxx.api;

import com.apploidxxx.api.exceptions.InvalidTokenException;
import com.apploidxxx.api.exceptions.ResponsibleException;
import com.apploidxxx.api.model.ErrorMessage;
import com.apploidxxx.api.model.ErrorResponseFactory;
import com.apploidxxx.api.model.UserNotes;
import com.apploidxxx.api.util.UserManager;
import com.apploidxxx.entity.User;
import com.apploidxxx.entity.UserType;
import com.apploidxxx.entity.dao.note.NoteService;
import com.apploidxxx.entity.dao.user.UserService;
import com.apploidxxx.entity.note.Note;
import com.apploidxxx.entity.note.NoteType;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Arthur Kupriyanov
 */
@Path("/api/user/note")
@Produces(MediaType.APPLICATION_JSON)
public class UserNoteApi {

    @DELETE
    public Response deleteNote(@Valid@NotNull@QueryParam("note_id") Long noteId,
                               @Valid@NotNull@QueryParam("access_token") String accessToken){
        Note note;
        User user;

        if ((note = NoteService.findById(noteId)) == null){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage("note_not_found", "Note is not found. Invalid note id"))
                    .build();
        }
        try { user = UserManager.getUser(accessToken); }
        catch (InvalidTokenException e) { return e.getResponse(); }

        // TODO: rewrite to equal for TEACHER
        if (user.equals(note.getAuthor().getUser())){

            note.getAuthor().removeWrittenNote(note);
            note.getTarget().removeNote(note);

            UserService.updateUser(note.getAuthor().getUser());
            UserService.updateUser(note.getTarget().getUser());

            NoteService.delete(note);
            return Response.ok().build();
        } else {
            return ErrorResponseFactory.getForbiddenErrorResponse();
        }
    }

    @GET
    public Response getNotes(@Valid @NotNull@QueryParam("type") String noteTypeParam,
                             @Valid@NotNull@QueryParam("access_token") String accessToken,
                             @Valid@NotNull@QueryParam("username") String username){
        NoteType noteType;
        User requestUser;
        User user;

        if ((noteType = NoteType.getNoteType(noteTypeParam)) == NoteType.UNKNOWN)
            return ErrorResponseFactory.getInvalidParamErrorResponse("Invalid type param");

        try {
            requestUser = UserManager.getUser(accessToken);
            user = UserManager.getUserByName(username);
        } catch (ResponsibleException e) {
            return e.getResponse();
        }


        // TODO : maybe add all notes return action?

        if (noteType == NoteType.PRIVATE){
            if (requestUser.getUserType() == UserType.TEACHER){
                return Response.ok(new UserNotes(user.getUserdata().getPrivateNotes())).build();
            } else {
                return ErrorResponseFactory.getForbiddenErrorResponse("Only teacher can read private notes");
            }
        } else {
            if (requestUser.getUserType() == UserType.TEACHER || requestUser.equals(user)){
                return Response.ok(new UserNotes(user.getUserdata().getPublicNotes())).build();
            } else {
                return ErrorResponseFactory.getForbiddenErrorResponse("Only student or another teacher can read notes");
            }
        }
    }

    @POST
    public Response createNote(@Valid@NotNull@QueryParam("access_token") String accessToken,
                               @Valid@NotNull@QueryParam("note_type") String queryType,
                               @Valid@NotNull@QueryParam("target") String queryTarget,
                               String message){

        User user;
        User target;
        NoteType type;

        try {
            user = UserManager.getUser(accessToken);
            target = UserManager.getUserByName(queryTarget);
        } catch (ResponsibleException e) {
            return e.getResponse();
        }

        if ((type = NoteType.getNoteType(queryType)) == NoteType.UNKNOWN){
            return ErrorResponseFactory.getInvalidParamErrorResponse("Invalid note_type param");
        }

        if ("".equals(message)){
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage("invalid_content", "Your body is empty")).build();
        }

        // TODO: teachers only method
        //if (user.getUserType() == UserType.TEACHER)

        Note note = new Note(user.getUserdata(), target.getUserdata(), type, message);

        user.getUserdata().addWrittenNote(note);
        target.getUserdata().addNote(note);
        NoteService.save(note);
        UserService.updateUser(user);
        UserService.updateUser(target);

        System.out.println(user.getUserdata().getWrittenNotes());
        System.out.println(user.getUserdata().getNotes());
        System.out.println(target.getUserdata().getWrittenNotes());
        System.out.println(target.getUserdata().getNotes());

        return Response.ok("{note_id : " + note.getId() + "}").build();
    }
}
