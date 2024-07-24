package org.pahappa.pettycashapp.systems.petty_cash_app.services;
import org.pahappa.pettycashapp.systems.petty_cash_app.dao.*;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.*;
@ApplicationScope
@Service
public class RequisitionService {
    @Autowired
    RequisitionDao requisitionDao;
    @Autowired
    BudgetLineDao budgetLineDao;
    @Autowired
    ReviewDao reviewDao;
    @Autowired
    UserDao userDao;
    @Autowired
    AccountabilityDao accountabilityDao;
    @Autowired
    RequisitionService requisitionService;

    private User getCurrentUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        return (User) externalContext.getSessionMap().get("currentUser");
    }

    public String generateReferenceNumber() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder referenceNumber = new StringBuilder("REQ-");

        for (int i = 0; i < 5; i++) {
            referenceNumber.append(characters.charAt(random.nextInt(characters.length())));
        }

        return referenceNumber.toString();
    }

    //REQUISITIONS
    public String makeRequisition(int amount, Date dateNeeded, String description, int budgetLineId){
        String error_message ="";
        boolean hasUnAccountedRequisitions = false;
        BudgetLine budgetLine= budgetLineDao.returnBudgetLineofId(budgetLineId);
        List<Requisition> requisitions = requisitionDao.getRequisitionsForUser(getCurrentUser().getId());
        for(Requisition requisition: requisitions){
            Accountability accountability = accountabilityDao.getAccountabilityOnRequisition(requisition.getId());
            if(accountability==null){
                hasUnAccountedRequisitions = true;
            }

        }
        if(budgetLine.getBalance()<amount){
            error_message="Amount specified is more than what is on budget line";
        } else if (hasUnAccountedRequisitions) {
            error_message="User still has un accounted requisitions ";
        } else if (dateNeeded.getYear() + 1900 > Calendar.getInstance().get(Calendar.YEAR)) {
            error_message="Date needed is a past date";
        } else if (description.length()<10) {
            error_message = "Please provide more description";
        } else if (!budgetLine.getStatus().equals("approved")) {
            error_message = "Budget line not yet approved";
        } else if (amount<=0) {
            error_message = "Enter a valid amount";
        } else {
            Requisition requisition = new Requisition();
            requisition.setAmount(amount);
            requisition.setReferenceNumber(requisitionService.generateReferenceNumber());
            requisition.setDateNeeded(dateNeeded);
            requisition.setDescription(description);
            requisition.setBudgetLine(budgetLine);
            requisition.setUser(requisitionService.getCurrentUser());
            requisitionDao.saveRequisition(requisition);


        }
        return error_message;
    }

    public List<Requisition> getAllRequisitions() {
        return  requisitionDao.getAllRequisitions();
    }
    //Finance issuing out money
    public void fulfillRequisition(int requisitionId){
        requisitionDao.fulfillRequisition(requisitionId,"fulfilled");
    }
    //For finance
    public List<Requisition> getApprovedRequisitions(){

        return requisitionDao.getApprovedRequisitions("approved");

    }
    //for ceo
    public List<Requisition> getRequisitionsWithReqs(){
        return requisitionDao.getRequisitionsWithReqs("change");
    }
    //for user
    public List<Requisition> getFulfilledRequisitions(){
        return requisitionDao.getFulfilledRequisitions("fulfilled");
    }

    public List<Requisition> getRejectedRequisitions(){ return requisitionDao.getRequisitionsWithReqs("rejected"); }
    //for user
    public List<Requisition> getDraftedRequisitions(){
        return requisitionDao.getDraftedRequisitions("drafted");
    }
    public void approveRequisitionRequest(int requisitionId){

        requisitionDao.approveRequisitionRequest(requisitionId,new Date(),"drafted");

    }
    //CEO approval
    public void approveRequisition(int requisitionId){
        requisitionDao.updateRequisistion(requisitionId);
    }
    public void makeRequisitionChangeRequest(int requisitionId){
        requisitionDao.makeRequisitionChangeRequest(requisitionId,"change");
    }

    public String updateRequisition(int requisitionId, int amount,Date dateNeeded,String description,int budgetLineId){
        String error_message = "";
        BudgetLine budgetLine= budgetLineDao.returnBudgetLineofId(budgetLineId);
        Requisition requisition = requisitionDao.getRequisitionOfId(requisitionId);
        if(!requisition.getStatus().equals("drafted")){
            error_message ="Requistion can not be edited";
        } else if (description.length()<10) {
            error_message="Please provide more description";
        } else if (amount>budgetLine.getAmountDelegated()) {
            error_message="Amount specified is greater than amount on budget line";
        }else if (!budgetLine.getStatus().equals("approved")) {
            error_message = "Budget line not yet approved";
        }else {
            requisitionDao.updateRequisition(requisitionId,amount,dateNeeded,description,budgetLine);
        }
        return error_message;
    }

    public void setRejectionStatus(int id) {

        requisitionDao.setRejectionStatus(id);
    }

    public int countRejectedRequisitions(){
        return getRejectedRequisitions().size();
    }

    public int countApprovedRequisitions(){
        return getApprovedRequisitions().size();
    }

    public int countRequisitionsWithRequests(){
        return requisitionService.getRequisitionsWithReqs().size();
    }

    public int countFulfilledRequisitions(){
        return getFulfilledRequisitions().size();
    }

    public int countAllRequisitions(){
        return getAllRequisitions().size();
    }


    public void deleteRequisition(int requisitionId) {
        requisitionDao.deleteRequisition(requisitionId);

    }

    public List<Requisition> getPendingRequisitions() {
        return requisitionDao.getPendingRequisitions("pending");
    }

    public void submitRequisition(Requisition requisition ) {
        requisitionDao.submitRequisition(requisition.getId(),"pending");

        //Securing required  money
        int balance = requisition.getBudgetLine().getBalance()-requisition.getAmount();
        budgetLineDao.updateBudgetLIne(requisition.getBudgetLine().getId(), balance,requisition.getBudgetLine().getStatus());

    }


    public Number countAllRejectedRequisitions() {
        return  requisitionDao.getApprovedRequisitions("rejected").size();

    }

    public Number countAllApprovedRequisitions() {
        return requisitionDao.getApprovedRequisitions("approved").size();
    }
}
