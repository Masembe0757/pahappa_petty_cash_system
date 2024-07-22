package org.pahappa.pettycashapp.systems.petty_cash_app.dao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.pahappa.pettycashapp.systems.petty_cash_app.configurations.SessionConfiguration;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Review;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.ArrayList;
import java.util.List;
@ApplicationScope
@Repository
public class ReviewDao {
    @Autowired
    SessionConfiguration sessionConfiguration;
    public void makeReview(Review review) {
        try {

            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.getCurrentSession();
            Transaction trs = session.beginTransaction();
            session.saveOrUpdate(review);
            trs.commit();
            sessionConfiguration.shutdown();
        } catch (Exception e) {
            sessionConfiguration.shutdown();
        }
    }

    public List<Review> getReviewsOfUser(int userId) {
        List<Review> reviews = new ArrayList<>();
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Review where user_id = :userId");
            qry.setParameter("userId", userId);
            reviews = qry.list();
            trs.commit();

            sessionConfiguration.shutdown();
        } catch (Exception e) {
            sessionConfiguration.shutdown();
            e.printStackTrace();
        }
        return reviews;
    }

    public  void saveRequisitionReview(Review review) {
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.getCurrentSession();
            Transaction trs = session.beginTransaction();
            session.save(review);
            trs.commit();
            sessionConfiguration.shutdown();
        } catch (Exception e) {
            sessionConfiguration.shutdown();
            e.printStackTrace();
        }
    }

    public void saveBudgetlineReview(Review review) {
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            session.saveOrUpdate(review);
            trs.commit();
            sessionConfiguration.shutdown();
        } catch (Exception e) {
            sessionConfiguration.shutdown();
        }

    }

    public void deleteReview(int reviewId) {
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("delete from Review where id = :reviewId");
            qry.setParameter("reviewId", reviewId);
            qry.executeUpdate();
            trs.commit();
            sessionConfiguration.shutdown();
        }catch (Exception e){
            sessionConfiguration.shutdown();
        }
    }
}
