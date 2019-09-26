package com.apploidxxx.entity.dao.user;

import com.apploidxxx.ds.HibernateSessionFactoryUtil;
import com.apploidxxx.entity.ContactDetails;
import com.apploidxxx.entity.dao.DAOBasicOperations;
import org.hibernate.Session;

import javax.persistence.NoResultException;

/**
 * @author Arthur Kupriyanov
 */
class ContactDetailsDAO {
    private final DAOBasicOperations<ContactDetailsDAO> basicOperations = new DAOBasicOperations<>();

    public ContactDetails findByEmail(String email){
        try (Session session =  HibernateSessionFactoryUtil.getSessionFactory().openSession()
        ){
            return session.createQuery("from ContactDetails where email='" + email + "'", ContactDetails.class).getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }
    public ContactDetails findByVkId(String vkid){
        try (Session session =  HibernateSessionFactoryUtil.getSessionFactory().openSession()
        ){
            return session.createQuery("from ContactDetails where vkontakteId='" + vkid + "'", ContactDetails.class).getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }
}
