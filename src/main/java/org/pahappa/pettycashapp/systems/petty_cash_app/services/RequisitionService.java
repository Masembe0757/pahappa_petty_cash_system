package org.pahappa.pettycashapp.systems.petty_cash_app.services;

import org.pahappa.pettycashapp.systems.petty_cash_app.dao.UserDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class RequisitionService {
    @Autowired
    UserDao userDao;

    @Autowired
    RequisitionService requisitionService;
    private User getCurrentUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        return (User) externalContext.getSessionMap().get("currentUser");
    }

    public  String generateReferenceNumber() {
        UUID uuid = UUID.randomUUID();
        String referenceNumber = uuid.toString().replace("-", "REQ").toUpperCase();
        return referenceNumber;
    }


    //REQUISITIONS
    public String makeRequisition(int amount, Date dateNeeded, String description, int budgetLineId){
        String error_message ="";
        BudgetLine budgetLine= userDao.returnBudgetLineofId(budgetLineId);
        if(budgetLine.getAmountDelegated()<amount){
            error_message="Amount specified is more than what is on budget line";
        } else if (dateNeeded.getYear() + 1900 > Calendar.getInstance().get(Calendar.YEAR)) {
            error_message="Date needed is a past date";
        } else if (description.length()<10) {
            error_message = "Please provide more description";
        } else if (!budgetLine.getStatus().equals("approved")) {
            error_message = "Budget line not yet approved";
        } else {
            Requisition requisition = new Requisition();
            requisition.setAmount(amount);
            requisition.setReferenceNumber(requisitionService.generateReferenceNumber());
            requisition.setDateNeeded(dateNeeded);
            requisition.setDescription(description);
            requisition.setBudgetLine(budgetLine);
            requisition.setUser(requisitionService.getCurrentUser());
            System.out.println("SAVING REQUISITION2");
            userDao.saveRequisition(requisition);
        }
        return error_message;
    }

    public List<Requisition> getAllRequisitions() {
        return  userDao.getAllRequisitions();
    }
    //Finance issuing out money
    public void fulfillRequisition(int requisitionId){
        userDao.fulfillRequisition(requisitionId,"fulfilled");
    }
    //For finance
    public List<Requisition> getApprovedRequisitions(){
        return userDao.getapprovedRequisitions("approved");
    }
    //for ceo
    public List<Requisition> getStagedRequisitions(){
        return userDao.getStagedRequisitions("staged");
    }
    //for user
    public List<Requisition> getFulfilledRequisitions(){
        return userDao.getFulfilledRequisitions("fulfilled");
    }

    public List<Requisition> getRejectedRequisitions(){ return userDao.getStagedRequisitions("rejected"); }
    //for user
    public List<Requisition> getDraftedRequisitions(){
        return userDao.getDraftedRequisitions("drafted");
    }
    public void stageRequisition(int requisitionId){
        userDao.stageRequisition(requisitionId,"staged");
    }
    //CEO approval
    public void approveRequisition(int requisitionId){
        userDao.approveRequisition(requisitionId,"approved");
    }

    public String updateRequistion(int requisitionId, int amount,Date dateNeeded,String description,int budgetLineId){
        String error_message = "";
        BudgetLine budgetLine= userDao.returnBudgetLineofId(budgetLineId);
        Requisition requisition = userDao.getRequisitionOfId(requisitionId);
        if(!requisition.getStatus().equals("draft")){
            error_message ="Requistion can not be edited";
        } else if (description.length()<50) {
            error_message="Please provide more description";
        } else if (amount>budgetLine.getAmountDelegated()) {
            error_message="Amount specified is greater than amount on budget line";
        }else if (!budgetLine.getStatus().equals("approved")) {
            error_message = "Budget line not yet approved";
        }else {
            requisition.setAmount(amount);
            requisition.setDateNeeded(dateNeeded);
            requisition.setDescription(description);
            requisition.setBudgetLine(budgetLine);
            userDao.updateRequesition(requisition);
        }
        return error_message;
    }
    public void rejectRequisition(int requisitionId,String information){
        Rejection rejection = new Rejection();
        Requisition requisition = userDao.getRequisitionOfId(requisitionId);
        rejection.setInformation(information);
        rejection.setRequisition(requisition);
        userDao.saveRejection(rejection);
    }

    public void setRejectionStatus(int id) {
        userDao.setRejectionStatus(id);
    }

    public int countRejectedRequisitions(){
        return getRejectedRequisitions().size();
    }

    public int countApprovedRequisitions(){
        return getApprovedRequisitions().size();
    }

    public int countStagedRequisitions(){
        return getStagedRequisitions().size();
    }

    public int countFulfilledRequisitions(){
        return getFulfilledRequisitions().size();
    }


}
