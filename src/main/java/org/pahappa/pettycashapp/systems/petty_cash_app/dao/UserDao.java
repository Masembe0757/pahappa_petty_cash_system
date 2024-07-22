package org.pahappa.pettycashapp.systems.petty_cash_app.dao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.*;
import org.pahappa.pettycashapp.systems.petty_cash_app.configurations.SessionConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@ApplicationScope
@Repository
public class UserDao {
    @Autowired
    SessionConfiguration sessionConfiguration;
    public  void saveUser(User user){
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.getCurrentSession();
            Transaction trs = session.beginTransaction();
            session.save(user);
            trs.commit();
            sessionConfiguration.shutdown();
        }
        catch (Exception e){
            sessionConfiguration.shutdown();
        }
    }

    public User returnUser(String userName) {
        User user = new User();
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from User where userName = :userName");
            qry.setParameter("userName", userName);
            user = (User) qry.uniqueResult();
            trs.commit();
            sessionConfiguration.shutdown();
        }
        catch (Exception e){
            sessionConfiguration.shutdown();
        }
        return user;
    }
    public User returnUserOfId(int userId) {
        User user = new User();
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from User where id = :userId");
            qry.setParameter("userId", userId);
            user = (User) qry.uniqueResult();
            trs.commit();
            sessionConfiguration.shutdown();
        }
        catch (Exception e){
            sessionConfiguration.shutdown();
        }
        return user;
    }

    public void createAdmin(User user) {
        try {

            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.getCurrentSession();
            Transaction trs = session.beginTransaction();
            session.saveOrUpdate(user);
            trs.commit();
            sessionConfiguration.shutdown();
        }
        catch (Exception e){
            sessionConfiguration.shutdown();
        }
    }

    public void updateUser(int userId,String firstName, String lastName, String userName,String password, String email,String role) {
        try {

            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            User user =(User) session.get(User.class,userId);

            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUserName(userName);
            user.setPassword(password);
            user.setEmail(email);
            user.setRole(role);

            session.update(user);

            trs.commit();
            sessionConfiguration.shutdown();
        }
        catch (Exception e){
            sessionConfiguration.shutdown();
        }

    }

    public List<User> getAllUsers() {
        List<User> users = null;
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from User where deleted = false");
            users = qry.list();
            trs.commit();
            sessionConfiguration.shutdown();
        }
        catch (Exception e){
            sessionConfiguration.shutdown();
        }
        return users;
    }

    public List<User> getDeletedUsers() {
        List<User> deletedUsers = null;
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from User where deleted = true");
            deletedUsers = qry.list();
            trs.commit();
            sessionConfiguration.shutdown();
        }
        catch (Exception e){
            sessionConfiguration.shutdown();
        }
        return deletedUsers;
    }

    public void deleteUserOfId(int userId) {
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("update User set deleted = true where id = :userId");
            qry.setParameter("userId", userId);
            qry.executeUpdate();
            trs.commit();
            sessionConfiguration.shutdown();
        }catch (Exception e){
            sessionConfiguration.shutdown();
        }
    }
}
