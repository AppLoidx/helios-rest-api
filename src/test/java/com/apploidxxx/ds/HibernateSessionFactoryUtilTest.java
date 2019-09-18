package com.apploidxxx.ds;

import org.hibernate.SessionFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Arthur Kupriyanov
 */
public class HibernateSessionFactoryUtilTest {

    @Test
    public void getSessionFactory() {
        SessionFactory sf = HibernateSessionFactoryUtil.getSessionFactory();
        assertNotNull(sf);
        assertEquals(sf, HibernateSessionFactoryUtil.getSessionFactory());
    }
}