package org.pahappa.pettycashapp.systems.petty_cash_app.services;
import org.pahappa.pettycashapp.systems.petty_cash_app.dao.BudgetLineDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.dao.RequisitionDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.dao.ReviewDao;
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
    RequisitionDao requisitionDao;
    @Autowired
    BudgetLineDao budgetLineDao;
    @Autowired
    ReviewDao reviewDao;

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
        BudgetLine budgetLine= budgetLineDao.returnBudgetLineofId(budgetLineId);
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
        return requisitionDao.getapprovedRequisitions("approved");
    }
    //for ceo
    public List<Requisition> getStagedRequisitions(){
        return requisitionDao.getStagedRequisitions("staged");
    }
    //for user
    public List<Requisition> getFulfilledRequisitions(){
        return requisitionDao.getFulfilledRequisitions("fulfilled");
    }

    public List<Requisition> getRejectedRequisitions(){ return requisitionDao.getStagedRequisitions("rejected"); }
    //for user
    public List<Requisition> getDraftedRequisitions(){
        return requisitionDao.getDraftedRequisitions("drafted");
    }
    public void stageRequisition(int requisitionId){
        requisitionDao.stageRequisition(requisitionId,"staged");
    }
    //CEO approval
    public void approveRequisition(int requisitionId){
        requisitionDao.approveRequisition(requisitionId,"approved");
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

    public int countStagedRequisitions(){
        return getStagedRequisitions().size();
    }

    public int countFulfilledRequisitions(){
        return getFulfilledRequisitions().size();
    }


    public void deleteRequisition(int requisitionId) {
        requisitionDao.deleteRequisition(requisitionId);

    }

    public List<Requisition> getPendingRequisitions() {
        return requisitionDao.getPendingRequisitions("pending");
    }

    public void submitRequisition(int requisitionId) {
        requisitionDao.submitRequisition(requisitionId,"pending");
    }

    public void saveRequisitionReview(String information, Date date, Requisition requisition, User user) {
        Review review = new Review();
        review.setRequisition(requisition);
        review.setDescription(information);
        review.setReviewedDate(date);
        review.setUser(user);
        reviewDao.saveRequisitionReview(review);
    }

    public List<Review> getReviewsOfUser(int id) {
        return  reviewDao.getReviewsOfUser(id);
    }
}
