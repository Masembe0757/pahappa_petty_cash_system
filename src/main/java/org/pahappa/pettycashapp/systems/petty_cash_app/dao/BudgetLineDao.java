package org.pahappa.pettycashapp.systems.petty_cash_app.dao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.pahappa.pettycashapp.systems.petty_cash_app.configurations.SessionConfiguration;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.BudgetLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.ArrayList;
import java.util.List;
@ApplicationScope
@Repository
public class BudgetLineDao {
    @Autowired
    SessionConfiguration sessionConfiguration;
    public void deleteBudgetLine(int budgetLineId) {
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();

            BudgetLine BudgetLineToDelete = (BudgetLine) session.get(BudgetLine.class, budgetLineId);

            if (BudgetLineToDelete != null) {
                session.delete(BudgetLineToDelete);

                trs.commit();
                sessionConfiguration.shutdown();
            } else {

                sessionConfiguration.shutdown();
            }
        } catch (Exception e) {
            sessionConfiguration.shutdown();
            e.printStackTrace();
        }
    }
    public void saveBudgetLine(BudgetLine budgetLine) {
        try {

            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.getCurrentSession();
            Transaction trs = session.beginTransaction();
            session.saveOrUpdate(budgetLine);
            trs.commit();
            sessionConfiguration.shutdown();
        }
        catch (Exception e){
            sessionConfiguration.shutdown();
        }
    }

    public void updateBudgetLIne(int budgetLineId, int balance, String status) {
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            BudgetLine budgetLine = (BudgetLine) session.get(BudgetLine.class,budgetLineId);
            budgetLine.setBalance(balance);
            budgetLine.setStatus(status);
            session.update(budgetLine);
            trs.commit();
            sessionConfiguration.shutdown();
        }
        catch (Exception e){
            sessionConfiguration.shutdown();
        }
    }

    public void changeBudgetLineStatus(int budgetLineId,String status) {
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("UPDATE BudgetLine set status = :status where id = :budgetLineId");
            qry.setParameter("status", status);
            qry.setParameter("budgetLineId",budgetLineId);
            qry.executeUpdate();
            trs.commit();
            sessionConfiguration.shutdown();
        }
        catch (Exception e){
            sessionConfiguration.shutdown();
        }

    }

    public List<BudgetLine> getDraftedBudgetLines(String drafted) {
        List<BudgetLine> budgetLines = new ArrayList<>();
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from BudgetLine where status = :drafted");
            qry.setParameter("drafted", drafted);
            budgetLines = qry.list();
            trs.commit();
            sessionConfiguration.shutdown();
        }
        catch (Exception e){
            sessionConfiguration.shutdown();
        }
        return budgetLines;
    }
    public List<BudgetLine> getApprovedBudgetLines(String approved) {
        List<BudgetLine> budgetLines = new ArrayList<>();
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from BudgetLine where status = :approved");
            qry.setParameter("approved", approved);
            budgetLines = qry.list();
            trs.commit();
            sessionConfiguration.shutdown();
        }
        catch (Exception e){
            sessionConfiguration.shutdown();
        }
        return budgetLines;
    }
    public List<BudgetLine> getRejectedBudgetLines() {
        List<BudgetLine> budgetLines = new ArrayList<>();
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from BudgetLine where status =:rejected ");
            qry.setParameter("rejected", "rejected");
            budgetLines = qry.list();
            trs.commit();
            sessionConfiguration.shutdown();
        }
        catch (Exception e){
            sessionConfiguration.shutdown();
        }
        return budgetLines;
    }

    public List<BudgetLine> getPendingBudgetLInes(String pending) {
        List<BudgetLine> budgetLines = new ArrayList<>();
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from BudgetLine where status = :pending");
            qry.setParameter("pending", pending);
            budgetLines = qry.list();
            trs.commit();
            sessionConfiguration.shutdown();
        }
        catch (Exception e){
            sessionConfiguration.shutdown();
        }
        return budgetLines;
    }

    public List<BudgetLine> getAllBudgetLines() {
        List<BudgetLine> budgetLines = new ArrayList<>();
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from BudgetLine ");
            budgetLines = qry.list();
            trs.commit();
            sessionConfiguration.shutdown();
        }
        catch (Exception e){
            sessionConfiguration.shutdown();
        }
        return budgetLines;
    }


    public BudgetLine returnBudgetLineofId(int budgetLineId) {
        BudgetLine budgetLine = new BudgetLine();
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from BudgetLine where id = :budgetLineId");
            qry.setParameter("budgetLineId", budgetLineId);
            budgetLine = (BudgetLine) qry.uniqueResult();
            trs.commit();
            sessionConfiguration.shutdown();
        }
        catch (Exception e){
            sessionConfiguration.shutdown();
        }
        return budgetLine;
    }


    public void updateDrafted(BudgetLine budgetLine) {
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            BudgetLine oldBL =(BudgetLine) session.get(BudgetLine.class, budgetLine.getId());
            oldBL.setStatus(budgetLine.getStatus());
            oldBL.setName(budgetLine.getName());
            oldBL.setAmountDelegated(budgetLine.getAmountDelegated());
            oldBL.setEndDate(budgetLine.getEndDate());
            oldBL.setCategory(budgetLine.getCategory());
            oldBL.setStartDate(budgetLine.getStartDate());
            session.update(oldBL);
            trs.commit();
            sessionConfiguration.shutdown();
        }
        catch (Exception e){
            sessionConfiguration.shutdown();
        }
    }

}
