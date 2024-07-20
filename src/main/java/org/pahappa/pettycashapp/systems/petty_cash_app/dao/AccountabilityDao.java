package org.pahappa.pettycashapp.systems.petty_cash_app.dao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.pahappa.pettycashapp.systems.petty_cash_app.configurations.SessionConfiguration;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Accountability;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Requisition;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountabilityDao {
    public void saveAccountability(Accountability accountability) {
        try {
            System.out.println("saving accountability 1");

            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.getCurrentSession();
            Transaction trs = session.beginTransaction();
            session.save(accountability);
            System.out.println("saving accountability 2");
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
    }
    public List<Accountability> getAccountabilitiesOfUser(int userId) {
        List<Accountability> accountabilities = new ArrayList<>();
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Accountability where user_id = :userId");
            qry.setParameter("userId", userId);
            accountabilities = qry.list();
            trs.commit();
            SessionConfiguration.shutdown();
        } catch (Exception e) {
            SessionConfiguration.shutdown();
        }
        return accountabilities;
    }
    public List<Accountability> getAllAccountabilities() {
        List<Accountability> accountabilities = new ArrayList<>();
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Accountability ");
            accountabilities = qry.list();
            trs.commit();
            SessionConfiguration.shutdown();
        } catch (Exception e) {
            SessionConfiguration.shutdown();
        }
        return accountabilities;
    }
}
