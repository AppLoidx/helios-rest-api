package com.apploidxxx.entity.dao.user;

import com.apploidxxx.api.exceptions.UserNotFoundException;
import com.apploidxxx.entity.ContactDetails;

/**
 * @author Arthur Kupriyanov
 */
public class ContactDetailsService {
    private static final ContactDetailsDAO contactDetailsDAO = new ContactDetailsDAO();

    public static ContactDetails findByEmail(String email) throws UserNotFoundException {
        ContactDetails data = contactDetailsDAO.findByEmail(email);
        if (data == null){
            throw new UserNotFoundException();
        } else {
            return data;
        }
    }
    public static ContactDetails findByVkId(String vkId) throws UserNotFoundException {
        ContactDetails data = contactDetailsDAO.findByVkId(vkId);
        if (data == null){
            throw new UserNotFoundException();
        } else {
            return data;
        }
    }
}
