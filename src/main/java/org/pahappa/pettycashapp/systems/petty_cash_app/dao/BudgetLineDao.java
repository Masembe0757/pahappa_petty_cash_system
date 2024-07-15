package org.pahappa.pettycashapp.systems.petty_cash_app.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.pahappa.pettycashapp.systems.petty_cash_app.configurations.SessionConfiguration;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.BudgetLine;
import org.springframework.stereotype.Repository;

@Repository
public class BudgetLineDao {
    public void deleteBudgetLine(int budgetLineId) {
        System.out.println("Executing");
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();

            BudgetLine BudgetLineToDelete = (BudgetLine) session.get(BudgetLine.class, budgetLineId);

            if (BudgetLineToDelete != null) {
                session.delete(BudgetLineToDelete);

                trs.commit();
                SessionConfiguration.shutdown();
            } else {

                SessionConfiguration.shutdown();
            }
        } catch (Exception e) {
            SessionConfiguration.shutdown();
            e.printStackTrace();
        }
    }

    public void stageBudgetLine(BudgetLine budgetLine) {
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("update BudgetLine set status = :staged where id = :id");
            qry.setParameter("id", budgetLine.getId());
            qry.setParameter("staged", "staged");
            qry.executeUpdate();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
    }

    public void updateDrafted(BudgetLine budgetLine) {
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            BudgetLine oldBL = (BudgetLine) session.createCriteria(BudgetLine.class)
                    .add(Restrictions.eq("id", budgetLine.getId())).uniqueResult();
            session.evict(oldBL);
            session.update(budgetLine);
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
    }
}
