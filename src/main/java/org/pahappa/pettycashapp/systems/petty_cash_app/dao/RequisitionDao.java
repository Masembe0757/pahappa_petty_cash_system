package org.pahappa.pettycashapp.systems.petty_cash_app.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.pahappa.pettycashapp.systems.petty_cash_app.configurations.SessionConfiguration;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.BudgetLine;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Requisition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@ApplicationScope
@Repository
public class RequisitionDao {
    @Autowired
    SessionConfiguration sessionConfiguration;
    public List<Requisition> getAllRequisitions() {
        List<Requisition> requisitions = new ArrayList<>();
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Requisition ");
            requisitions = qry.list();
            trs.commit();
            sessionConfiguration.shutdown();
        } catch (Exception e) {
            sessionConfiguration.shutdown();
        }
        return requisitions;

    }

    public void deleteRequisition(int requisitionId) {
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("delete from Requisition where id = :requisitionId");
            qry.setParameter("requisitionId", requisitionId);
            qry.executeUpdate();
            trs.commit();
            sessionConfiguration.shutdown();
        } catch (Exception e) {
            sessionConfiguration.shutdown();
        }
    }

    public List<Requisition> getPendingRequisitions(String pending) {
        List<Requisition> requisitions = new ArrayList<>();
        try {
            System.out.println("PENDING REQS........");
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Requisition where status = :pending");
            qry.setParameter("pending", pending);
            requisitions = qry.list();
            trs.commit();
            sessionConfiguration.shutdown();
        } catch (Exception e) {
            sessionConfiguration.shutdown();
        }
        return requisitions;
    }

    public void submitRequisition(int requisitionId, String pending) {
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("UPDATE Requisition set status = :pending where id = :requisitionId");
            qry.setParameter("pending", pending);
            qry.setParameter("requisitionId", requisitionId);
            qry.executeUpdate();
            trs.commit();
            sessionConfiguration.shutdown();
        } catch (Exception e) {
            sessionConfiguration.shutdown();
        }

    }

    public void setRejectionStatus(int id) {
        String status = "rejected";
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("UPDATE Requisition set status = :status where id = :id");
            qry.setParameter("id", id);
            qry.setParameter("status", status);
            qry.executeUpdate();
            trs.commit();
            sessionConfiguration.shutdown();
        } catch (Exception e) {
            sessionConfiguration.shutdown();
        }
    }

    public void fulfillRequisition(int requisitionId, String fulfilled) {
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("UPDATE Requisition set status = :fulfilled where id = :requisitionId");
            qry.setParameter("requisitionId", requisitionId);
            qry.setParameter("fulfilled", fulfilled);
            qry.executeUpdate();
            trs.commit();
            sessionConfiguration.shutdown();
        } catch (Exception e) {
            sessionConfiguration.shutdown();
        }
    }

    public List<Requisition> getApprovedRequisitions(String status) {
        List<Requisition> requisitions = new ArrayList<>();
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Requisition where status = :status");
            qry.setParameter("status", status);
            requisitions = qry.list();
            trs.commit();
            sessionConfiguration.shutdown();
        } catch (Exception e) {
            sessionConfiguration.shutdown();
        }
        return requisitions;
    }

    public List<Requisition> getFulfilledRequisitions(String fulfilled) {
        List<Requisition> requisitions = new ArrayList<>();
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Requisition where status = :fulfilled");
            qry.setParameter("fulfilled", fulfilled);
            requisitions = qry.list();
            trs.commit();
            sessionConfiguration.shutdown();
        } catch (Exception e) {
            sessionConfiguration.shutdown();
        }
        return requisitions;
    }

    public List<Requisition> getDraftedRequisitions(String drafted) {
        List<Requisition> requisitions = new ArrayList<>();
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Requisition where status = :drafted");
            qry.setParameter("drafted", drafted);
            requisitions = qry.list();
            trs.commit();
            sessionConfiguration.shutdown();
        } catch (Exception e) {
            sessionConfiguration.shutdown();
        }
        return requisitions;
    }

    public void approveRequisitionRequest(int requisitonId, String drafted) {
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("UPDATE Requisition set status = :drafted where id = :requisitonId");
            qry.setParameter("requisitonId", requisitonId);
            qry.setParameter("drafted", drafted);
            qry.executeUpdate();
            trs.commit();
            sessionConfiguration.shutdown();
        } catch (Exception e) {
            sessionConfiguration.shutdown();
        }
    }

    public void approveRequisition(int requisitionId, String approved) {
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("UPDATE Requisition set status = :approved where id = :requisitionId");
            qry.setParameter("requisitionId", requisitionId);
            qry.setParameter("approved", approved);
            qry.executeUpdate();
            trs.commit();
            sessionConfiguration.shutdown();
        } catch (Exception e) {
            sessionConfiguration.shutdown();
        }
    }
    public void makeRequisitionChangeRequest(int requisitionId, String change) {
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("UPDATE Requisition set status = :change where id = :requisitionId");
            qry.setParameter("requisitionId", requisitionId);
            qry.setParameter("change", change);
            qry.executeUpdate();
            trs.commit();
            sessionConfiguration.shutdown();
        } catch (Exception e) {
            sessionConfiguration.shutdown();
        }
    }

    public void updateRequisition(int requisitionId, int amount, Date dateNeeded, String description, BudgetLine budgetLine) {
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Requisition requisition = (Requisition) session.get(Requisition.class, requisitionId);
            requisition.setAmount(amount);
            requisition.setDateNeeded(dateNeeded);
            requisition.setDescription(description);
            requisition.setBudgetLine(budgetLine);
            session.update(requisition);
            trs.commit();
            sessionConfiguration.shutdown();
        } catch (Exception e) {
            sessionConfiguration.shutdown();
            e.printStackTrace();
        }
    }

    public void saveRequisition(Requisition requisition) {
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.getCurrentSession();
            Transaction trs = session.beginTransaction();
            session.saveOrUpdate(requisition);
            trs.commit();
            sessionConfiguration.shutdown();
        } catch (Exception e) {
            sessionConfiguration.shutdown();
            e.printStackTrace();
        }
    }

    public Requisition getRequisitionOfId(int requisitionId) {
        Requisition requisition = new Requisition();
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Requisition where id = :requisitionId");
            qry.setParameter("requisitionId", requisitionId);
            requisition = (Requisition) qry.uniqueResult();
            trs.commit();
            sessionConfiguration.shutdown();
        } catch (Exception e) {
            sessionConfiguration.shutdown();
        }
        return requisition;
    }

    public List<Requisition> getRequisitionsWithReqs(String change) {
        List<Requisition> requisitions = null;
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Requisition where status = :change");
            qry.setParameter("change", change);
            requisitions = qry.list();
            trs.commit();
            sessionConfiguration.shutdown();
        } catch (Exception e) {
            sessionConfiguration.shutdown();
        }
        return requisitions;
    }

}
