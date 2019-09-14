package com.apploidxxx.ds;

import com.apploidxxx.entity.*;
import com.apploidxxx.entity.note.Note;
import com.apploidxxx.entity.queue.Notification;
import com.apploidxxx.entity.queue.Queue;
import com.apploidxxx.entity.queue.SwapContainer;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Queue.class);
                configuration.addAnnotatedClass(ContactDetails.class);
                configuration.addAnnotatedClass(Session.class);
                configuration.addAnnotatedClass(Chat.class);
                configuration.addAnnotatedClass(Message.class);
                configuration.addAnnotatedClass(UserData.class);
                configuration.addAnnotatedClass(Note.class);
                configuration.addAnnotatedClass(Notification.class);
                configuration.addAnnotatedClass(SwapContainer.class);
                configuration.addAnnotatedClass(AuthorizationCode.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Error in Hibernate! Log: " + e);
            }
        }
        return sessionFactory;
    }
}