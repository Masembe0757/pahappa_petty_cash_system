package org.pahappa.pettycashapp.systems.petty_cash_app.dao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.pahappa.pettycashapp.systems.petty_cash_app.configurations.SessionConfiguration;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Review;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.User;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewDao {
    public void makeReview(Review review) {
        try {

            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.getCurrentSession();
            Transaction trs = session.beginTransaction();
            session.saveOrUpdate(review);
            trs.commit();
            SessionConfiguration.shutdown();
        } catch (Exception e) {
            SessionConfiguration.shutdown();
        }
    }

    public List<Review> getReviewsOfUser(int userId) {
        List<Review> reviews = new ArrayList<>();
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Review where user_id = :userId");
            qry.setParameter("userId", userId);
            reviews = qry.list();
            trs.commit();

            SessionConfiguration.shutdown();
        } catch (Exception e) {
            SessionConfiguration.shutdown();
            e.printStackTrace();
        }
        return reviews;
    }

    public  void saveRequisitionReview(Review review) {
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.getCurrentSession();
            Transaction trs = session.beginTransaction();
            session.save(review);
            trs.commit();
            SessionConfiguration.shutdown();
        } catch (Exception e) {
            SessionConfiguration.shutdown();
            e.printStackTrace();
        }
    }

    public void saveBudgetlineReview(Review review) {
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            session.saveOrUpdate(review);
            trs.commit();
            SessionConfiguration.shutdown();
        } catch (Exception e) {
            SessionConfiguration.shutdown();
        }

    }

    public void deleteReview(int reviewId) {
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("delete from Review where id = :reviewId");
            qry.setParameter("reviewId", reviewId);
            qry.executeUpdate();
            trs.commit();
            SessionConfiguration.shutdown();
        }catch (Exception e){
            SessionConfiguration.shutdown();
        }
    }
}
