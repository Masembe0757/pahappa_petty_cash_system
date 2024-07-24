package org.pahappa.pettycashapp.systems.petty_cash_app.beans;

import org.pahappa.pettycashapp.systems.petty_cash_app.models.*;
import org.pahappa.pettycashapp.systems.petty_cash_app.services.BudgetLineService;
import org.pahappa.pettycashapp.systems.petty_cash_app.services.RequisitionService;
import org.pahappa.pettycashapp.systems.petty_cash_app.services.ReviewService;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Component
@SessionScope
public class RequisitionBean implements Serializable {
    private static final long serialVersionUID = 5L;

    private int amount;
    private int budgetLineId;
    private String description;
    private Date dateNeeded;
    private String information;
    private String username;
    private String budgetLineName;
    private int requisitionId;
    private Requisition selectedRequisition;
    @Autowired
    private ReviewService reviewService;

    public int getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(int requisitionId) {
        this.requisitionId = requisitionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBudgetLineName() {
        return budgetLineName;
    }

    public void setBudgetLineName(String budgetLineName) {
        this.budgetLineName = budgetLineName;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }


    @Autowired
    RequisitionService requisitionService;
    @Autowired
    BudgetLineService budgetLineService;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getBudgetLineId() {
        return budgetLineId;
    }

    public void setBudgetLineId(int budgetLineId) {
        this.budgetLineId = budgetLineId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateNeeded() {
        return dateNeeded;
    }

    public void setDateNeeded(Date dateNeeded) {
        this.dateNeeded = dateNeeded;
    }

    public Requisition getSelectedRequisition() {
        return selectedRequisition;
    }

    public void setSelectedRequisition(Requisition selectedRequisition) {
        this.selectedRequisition = selectedRequisition;
    }

    public User currentUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        return (User) externalContext.getSessionMap().get("currentUser");
    }

    //REQUISITIONS CODE
    public void makeRequisition(int amount, Date dateNeeded, String description, int budgetLineId) {
        String message = requisitionService.makeRequisition(amount, dateNeeded, description, budgetLineId);

        if (message != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCESS", "Requisition sent to drafts"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "FAILURE", message));
        }
    }

    public List<Requisition> returnDraftedRequisitions() {
        return requisitionService.getDraftedRequisitions();
    }


    public List<Requisition> getAllRequisitions() {
        return requisitionService.getAllRequisitions();
    }

    public List<BudgetLine> getBudgetLines() {
        return budgetLineService.getApprovedBudgetLines();
    }

    public void approveRequisition(int requisitionId) {

        requisitionService.approveRequisition(requisitionId);

    }

    public void updateRequisition( int amount, Date dateNeeded, String description, int budgetLineId) {
        String message = requisitionService.updateRequisition(requisitionId, amount, dateNeeded, description, budgetLineId);
        if (message.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCESS", "Requisition updated successfully"));

        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "FAILURE", message));
        }
    }

    public void deleteRequisition(int requisitionId) {
        requisitionService.deleteRequisition(requisitionId);
    }

    public List<Requisition> returnRequisitionsWithReqs() {
        return requisitionService.getRequisitionsWithReqs();
    }

    public List<Requisition> returnPendingRequisitions() {
        return requisitionService.getPendingRequisitions();
    }

    public List<Requisition> returnFulfilledRequisitions() {
        return requisitionService.getFulfilledRequisitions();
    }

    public List<Requisition> returnApprovedRequisitions() {
        return requisitionService.getApprovedRequisitions();
    }

    public List<Requisition> returnRejectedRequisitions() {
        return requisitionService.getRejectedRequisitions();
    }

    public void makeRequisitionChangeRequest(int requisitionId) {
        requisitionService.makeRequisitionChangeRequest(requisitionId);
    }

    public void completeRequisition(int requisitionId) {
        requisitionService.fulfillRequisition(requisitionId);
    }

    public void submitRequisition(Requisition requisition) {
        requisitionService.submitRequisition(requisition);
    }

    public void saveReview(String information, Requisition requisition, User user) {
        reviewService.saveRequisitionReview(information, new Date(), requisition, user);
    }

    public void approveBudgetLine(int budgetLineId) {
        budgetLineService.approveBudgetLine(budgetLineId);
    }

    public int countReviewedReqs() {
        return requisitionService.getApprovedRequisitions().size() + requisitionService.getRejectedRequisitions().size();
    }

    public void onRowSelect(SelectEvent event) {
        selectedRequisition = (Requisition) event.getObject();
    }

}
