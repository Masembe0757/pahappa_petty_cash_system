package org.pahappa.pettycashapp.systems.petty_cash_app.dao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.*;
import org.pahappa.pettycashapp.systems.petty_cash_app.configurations.SessionConfiguration;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {


    public  void saveUser(User user){
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.getCurrentSession();
            Transaction trs = session.beginTransaction();
            session.save(user);
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
    }

    public User returnUser(String userName) {
        User user = new User();
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from User where userName = :userName");
            qry.setParameter("userName", userName);
            user = (User) qry.uniqueResult();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
        return user;
    }

    public void createAdmin(User user) {
        try {

            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.getCurrentSession();
            Transaction trs = session.beginTransaction();
            session.saveOrUpdate(user);
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

    public void saveRequisition(Requisition requisition) {
        try {
            System.out.println("SAVING REQUISITION3");
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.getCurrentSession();
            Transaction trs = session.beginTransaction();
            session.saveOrUpdate(requisition);
            System.out.println("SAVING REQUISITION4");
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
    }

    public Requisition getRequisitionOfId(int requisitionId) {
        Requisition requisition = new Requisition();
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Requisition where id = :requisitionId");
            qry.setParameter("requisitionId", requisitionId);
            requisition = (Requisition) qry.uniqueResult();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
        return requisition;
    }

    public void saveAccountability(Accountability accountability) {
        try {

            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.getCurrentSession();
            Transaction trs = session.beginTransaction();
            session.saveOrUpdate(accountability);
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
    }

    public void makeReview(Review review) {
        try {

            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.getCurrentSession();
            Transaction trs = session.beginTransaction();
            session.saveOrUpdate(review);
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
    }

    public Category getCategoryOfId(int categoryId) {
        Category category = new Category();
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Category where id = :categoryId");
            qry.setParameter("categoryId", categoryId);
            category = (Category) qry.uniqueResult();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
        return category;
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

    public void updateUser(User user) {
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            session.saveOrUpdate(user);
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
    }

    public void updateRequesition(Requisition requisition) {
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            session.saveOrUpdate(requisition);
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

    public void stageRequisition(int requisitonId, String staged) {
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("UPDATE Requisition set status = :staged where id = :requisitonId");
            qry.setParameter("requisitonId", requisitonId);
            qry.setParameter("staged",staged);
            qry.executeUpdate();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
    }

    public void approveRequisition(int requisitonId, String approved) {
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("UPDATE Requisition set status = :approved where id = :requisitonId");
            qry.setParameter("requisitonId", requisitonId);
            qry.setParameter("approved",approved);
            qry.executeUpdate();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
    }

    public void saveRejection(Rejection rejection) {
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            session.saveOrUpdate(rejection);
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
    }

    public void fulfillRequisition(int requisitionId, String fulfilled) {
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("UPDATE Requisition set status = :fulfilled where id = :requisitionId");
            qry.setParameter("requisitionId", requisitionId);
            qry.setParameter("fulfilled",fulfilled);
            qry.executeUpdate();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
    }

    public List<Requisition> getapprovedRequisitions(String status) {
        List<Requisition> requisitions = new ArrayList<>();
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Requisition where status = :status");
            qry.setParameter("status", status);
            requisitions = qry.list();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
        return requisitions;
    }
    public List<Requisition> getStagedRequisitions(String status) {
        List<Requisition> requisitions = new ArrayList<>();
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Requisition where status = :status");
            qry.setParameter("status", status);
            requisitions = qry.list();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
        return requisitions;
    }

    public List<Requisition> getFulfilledRequisitions(String fulfilled) {
        List<Requisition> requisitions = new ArrayList<>();
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Requisition where status = :fulfilled");
            qry.setParameter("fulfilled", fulfilled);
            requisitions = qry.list();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
        return requisitions;
    }

    public List<Requisition> getDraftedRequisitions(String drafted) {
        List<Requisition> requisitions = new ArrayList<>();
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Requisition where status = :drafted");
            qry.setParameter("drafted", drafted);
            requisitions = qry.list();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
        return requisitions;
    }
    public List<Category> returnCategories() {
        List<Category> categories = new ArrayList<>();
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Category ");
            categories = qry.list();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
        return categories;
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

    public List<User> getAllUsers() {
        List<User> users = null;
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from User");
            users = qry.list();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
        return users;
    }

    public void deleteUserOfUserName(String userName) {
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("delete from User where userName = :userName");
            qry.setParameter("userName", userName);
            qry.executeUpdate();
            trs.commit();
            SessionConfiguration.shutdown();
        }catch (Exception e){
            SessionConfiguration.shutdown();
        }
    }

    public List<Requisition> getAllRequisitions() {
        List<Requisition> requisitions = new ArrayList<>();
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Requisition ");
            requisitions = qry.list();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
        return requisitions;

    }
}
