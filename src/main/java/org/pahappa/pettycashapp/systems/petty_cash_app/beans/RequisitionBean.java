package org.pahappa.pettycashapp.systems.petty_cash_app.beans;

import org.pahappa.pettycashapp.systems.petty_cash_app.models.BudgetLine;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Permission;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Rejection;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Requisition;
import org.pahappa.pettycashapp.systems.petty_cash_app.services.BudgetLineService;
import org.pahappa.pettycashapp.systems.petty_cash_app.services.RequisitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import javax.el.MethodExpression;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Component
@SessionScope
public class RequisitionBean implements Serializable {

    private int amount;
    private int budgetLineId;
    private String description;
    private Date dateNeeded;
    private  String information;
    private  String username;
    private String budgetLineName;
    private int requisitionId;

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


    //REQUISITIONS CODE
    public void makeRequisition(int amount, Date dateNeeded, String description, int budgetLineId){
        String message = requisitionService.makeRequisition(amount,dateNeeded,description,budgetLineId);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }
    public List<Requisition> rteturnDraftedRequisitions(){
        return  requisitionService.getDraftedRequisitions();
    }


    public List<Requisition> getAllRequisitions() {
        return requisitionService.getAllRequisitions();
    }
    public List<BudgetLine>  getBudgetLines(){
        return budgetLineService.getApprovedBudgetLines() ;
    }

    public void rejectRequisition(int requisitionId, String information) {
        requisitionService.rejectRequisition(requisitionId,information);
        requisitionService.setRejectionStatus(requisitionId);
    }
    public void approveRequisition(int requisitionId) {
        requisitionService.approveRequisition(requisitionId);
    }

    public void updateRequisition(int requisitionId,int amount, Date dateNeeded, String description,int budgetLineId) {
        String message = requisitionService.updateRequisition(requisitionId,amount,dateNeeded,description,budgetLineId);
        if(message.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Requisition updated successfully", null));

        }else {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));}
    }

    public void deleteRequisition(int requisitionId) {
        requisitionService.deleteRequisition(requisitionId);
    }

    public List<Requisition> returnStagedRequisitions() {
        return  requisitionService.getStagedRequisitions();
    }
    public List<Requisition> returnPendingRequisitions() {
        return  requisitionService.getPendingRequisitions();
    }
    public List<Requisition> returnFulfilledRequisitions() {
        return  requisitionService.getFulfilledRequisitions();
    }

    public List<Requisition> returnApprovedRequisitions() {
        return requisitionService.getApprovedRequisitions();
    }

    public List<Requisition> returnRejectedRequisitions() {
        return  requisitionService.getRejectedRequisitions();
    }

    public void stageRequisition(int requisitionId) {
        requisitionService.stageRequisition(requisitionId);
    }

    public void completeRequisition(int requisitionId) {
        requisitionService.fulfillRequisition(requisitionId);
    }

    public void submitRequisition(int requisitionId) {
        requisitionService.submitRequisition(requisitionId);
    }
}
