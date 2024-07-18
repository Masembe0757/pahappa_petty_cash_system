package org.pahappa.pettycashapp.systems.petty_cash_app.dao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.pahappa.pettycashapp.systems.petty_cash_app.configurations.SessionConfiguration;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.BudgetLine;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
    public void saveBudgetLine(BudgetLine budgetLine) {
        try {

            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.getCurrentSession();
            Transaction trs = session.beginTransaction();
            session.saveOrUpdate(budgetLine);
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
    }

    public void updateBudgetLIne(BudgetLine budgetLine) {
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            session.saveOrUpdate(budgetLine);
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
    }

    public void approveBudgetLine(int budgetLineId,String status) {
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("UPDATE BudgetLine set status = :status where id = :budgetLineId");
            qry.setParameter("status", status);
            qry.setParameter("budgetLineId",budgetLineId);
            qry.executeUpdate();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }

    }

    public List<BudgetLine> getDraftedBudgetLines(String drafted) {
        List<BudgetLine> budgetLines = new ArrayList<>();
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from BudgetLine where status = :drafted");
            qry.setParameter("drafted", drafted);
            budgetLines = qry.list();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
        return budgetLines;
    }
    public List<BudgetLine> getApprovedBudgetLines(String approved) {
        List<BudgetLine> budgetLines = new ArrayList<>();
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from BudgetLine where status = :approved");
            qry.setParameter("approved", approved);
            budgetLines = qry.list();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
        return budgetLines;
    }
    public List<BudgetLine> getRejectedBudgetLines() {
        List<BudgetLine> budgetLines = new ArrayList<>();
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from BudgetLine where status =:rejected ");
            qry.setParameter("rejected", "rejected");
            budgetLines = qry.list();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
        return budgetLines;
    }

    public List<BudgetLine> getPendingBudgetLInes(String pending) {
        List<BudgetLine> budgetLines = new ArrayList<>();
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from BudgetLine where status = :pending");
            qry.setParameter("pending", pending);
            budgetLines = qry.list();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
        return budgetLines;
    }

    public List<BudgetLine> getAllBudgetLines() {
        List<BudgetLine> budgetLines = new ArrayList<>();
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from BudgetLine ");
            budgetLines = qry.list();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
        return budgetLines;
    }



    public void setBudgetLineRejectionStatus(int budgetLineId) {
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("UPDATE BudgetLine set status = :status where id = :budgetLineId");
            qry.setParameter("status", "rejected");
            qry.setParameter("budgetLineId",budgetLineId);
            qry.executeUpdate();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
    }

    public BudgetLine returnBudgetLineofId(int budgetLineId) {
        BudgetLine budgetLine = new BudgetLine();
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from BudgetLine where id = :budgetLineId");
            qry.setParameter("budgetLineId", budgetLineId);
            budgetLine = (BudgetLine) qry.uniqueResult();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
        return budgetLine;
    }

    public void submitBudgetLine(BudgetLine budgetLine) {
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("update BudgetLine set status = :pending where id = :id");
            qry.setParameter("id", budgetLine.getId());
            qry.setParameter("pending", "pending");
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
