package com.apploidxxx.entity.dao.oauth;

import com.apploidxxx.ds.HibernateSessionFactoryUtil;
import com.apploidxxx.entity.AuthorizationCode;
import com.apploidxxx.entity.dao.DAOBasicOperations;
import org.hibernate.Session;

import javax.persistence.NoResultException;

/**
 * @author Arthur Kupriyanov
 */
class AuthorizationCodeDAO {
    private final DAOBasicOperations<AuthorizationCode> daoBasicOperations = new DAOBasicOperations<>();

    public void save(AuthorizationCode authorizationCode){
        daoBasicOperations.save(authorizationCode);
    }

    public void update(AuthorizationCode authorizationCode){
        daoBasicOperations.update(authorizationCode);
    }

    public void delete(AuthorizationCode authorizationCode){
        daoBasicOperations.delete(authorizationCode);
    }
    public AuthorizationCode findByCode(String code){
        try (Session session =  HibernateSessionFactoryUtil.getSessionFactory().openSession()
        ){
            return session.createQuery("from AuthorizationCode where authCode='" + code + "'", AuthorizationCode.class).getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }
}
