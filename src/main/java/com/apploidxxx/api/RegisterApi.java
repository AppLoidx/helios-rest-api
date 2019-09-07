package com.apploidxxx.api;

import com.apploidxxx.Main;
import com.apploidxxx.api.exceptions.VulnerabilityException;
import com.apploidxxx.api.model.ErrorMessage;
import com.apploidxxx.api.util.ErrorResponseFactory;
import com.apploidxxx.api.util.GroupChecker;
import com.apploidxxx.api.util.Password;
import com.apploidxxx.api.util.VulnerabilityChecker;
import com.apploidxxx.entity.User;
import com.apploidxxx.entity.dao.user.UserService;
import org.jboss.logging.Logger;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * @author Arthur Kupriyanov
 */
@Path("/api/register")
public class RegisterApi {
    private final Logger logger = Logger.getLogger(RegisterApi.class);

    @POST
    public Response register(@Valid@NotNull@QueryParam("username") String username,
                             @Valid@NotNull@QueryParam("password") String password,
                             @Valid@NotNull@QueryParam("first_name") String firstName,
                             @Valid@NotNull@QueryParam("last_name") String lastName,
                             @Valid@NotNull@QueryParam("email") String email,
                             @Valid@QueryParam("group") String group){
        logger.debug(String.format("Params \nusername: %s,\npassword: %s, \nfirst_name: %s,\nlast_name: %s\nemail: %s,\ngroup: %s",
                username, password, firstName, lastName, email, group));

        logger.debug("Validating the password");
        if ("".equals(password) || (password.length() < 8 && Main.validatePassword)){
            return ErrorResponseFactory.getInvalidParamErrorResponse("Your password length is too small");
        }

        logger.info("Validating the email");
        if (!email.matches(".+@.+")){
            return ErrorResponseFactory.getInvalidParamErrorResponse("Invalid email param");
        }

        logger.debug("Validating first_name param");
        if (!firstName.matches("[^\\s]+") || !lastName.matches("[^\\s]+")){
            return ErrorResponseFactory.getInvalidParamErrorResponse("Invalid first_name or last_name param");
        }

        logger.debug("Validating last_name param");
        if (!username.matches("[^\\s]+")){
            return ErrorResponseFactory.getInvalidParamErrorResponse("Invalid username param");
        }

        logger.debug("Validating group");
        if ("".equals(group)) group = null;
        if (group != null && !group.matches("[^\\s]+")){
            return ErrorResponseFactory.getInvalidParamErrorResponse("Invalid group name");
        } else {
            if (group != null && !GroupChecker.isValid(group)) return ErrorResponseFactory.getInvalidParamErrorResponse("Group not found");
        }

        logger.debug("Checking params vulnerabilities");

        try {
            VulnerabilityChecker.checkWord(firstName);
            VulnerabilityChecker.checkWord(lastName);
            VulnerabilityChecker.checkWord(username);
            if (group != null) VulnerabilityChecker.checkWord(group);
        } catch (VulnerabilityException e) {
            logger.debug("Founded exception", e);
            return e.getResponse();
        }

        if (UserService.findByName(username)==null){
            logger.debug("Saving new user");
            UserService.saveUser(new User(username, Password.hash(password), firstName, lastName, email, group));
            return Response.ok().build();
        }
        else {
            return ErrorResponseFactory.getInvalidParamErrorResponse("This username already is taken");
        }
    }

    @DELETE
    public Response deleteUser(@Valid@NotNull@QueryParam("username") String username,
                               @Valid@NotNull@QueryParam("password") String password)
    {
        User user = UserService.findByName(username);
        if (user!=null && Password.isEqual(password, user.getPassword())) {
            UserService.deleteUser(user);
            return Response.ok().build();
        } else {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage("invalid_credentials", "invalid username or password"))
                    .build();
        }
    }
}
