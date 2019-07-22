package com.apploidxxx.entity.dao.verify;

import com.apploidxxx.entity.User;
import com.apploidxxx.entity.VerificationContainer;

/**
 * @author Arthur Kupriyanov
 */
public class VerifyService {
    VerifyDAO vsDAO = new VerifyDAO();

    public void save(VerificationContainer v){
        vsDAO.save(v);
    }

    public void delete(VerificationContainer v){
        vsDAO.delete(v);
    }

    public VerificationContainer get(User user){
        return vsDAO.get(user);
    }

}
