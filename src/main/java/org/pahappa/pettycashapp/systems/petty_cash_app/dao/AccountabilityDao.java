package org.pahappa.pettycashapp.systems.petty_cash_app.dao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.pahappa.pettycashapp.systems.petty_cash_app.configurations.SessionConfiguration;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Accountability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.ArrayList;
import java.util.List;
@ApplicationScope
@Repository
public class AccountabilityDao {
    @Autowired
    SessionConfiguration sessionConfiguration;
    public void saveAccountability(Accountability accountability) {
        try {
            System.out.println("saving accountability 1");

            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.getCurrentSession();
            Transaction trs = session.beginTransaction();
            session.save(accountability);
            System.out.println("saving accountability 2");
            trs.commit();
            sessionConfiguration.shutdown();
        }
        catch (Exception e){
            sessionConfiguration.shutdown();
        }
    }
    public List<Accountability> getAccountabilitiesOfUser(int userId) {
        List<Accountability> accountabilities = new ArrayList<>();
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Accountability where user_id = :userId");
            qry.setParameter("userId", userId);
            accountabilities = qry.list();
            trs.commit();
            sessionConfiguration.shutdown();
        } catch (Exception e) {
            sessionConfiguration.shutdown();
        }
        return accountabilities;
    }
    public List<Accountability> getAllAccountabilities() {
        List<Accountability> accountabilities = new ArrayList<>();
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Accountability ");
            accountabilities = qry.list();
            trs.commit();
            sessionConfiguration.shutdown();
        } catch (Exception e) {
            sessionConfiguration.shutdown();
        }
        return accountabilities;
    }

    public Accountability getAccountabilityOnRequisition(int requisitionId) {
        Accountability accountability =new Accountability();
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Accountability where requisition_id =:requisitionId");
            qry.setParameter("requisitionId",requisitionId);
            accountability =(Accountability) qry.uniqueResult();
            trs.commit();
            sessionConfiguration.shutdown();
        } catch (Exception e) {
            sessionConfiguration.shutdown();
        }
        return accountability;
    }
}
