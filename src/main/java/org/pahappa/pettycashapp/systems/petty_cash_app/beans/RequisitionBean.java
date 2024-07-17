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
    String information;

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
        System.out.println("SAVING REQUISITION1");
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
        System.out.println(information+"  "+requisitionId);
        System.out.println("INFORMATION  "+ information);
        requisitionService.rejectRequisition(requisitionId,information);
        requisitionService.setRejectionStatus(requisitionId);
    }
    public void approveRequisition(int requisitionId) {
        requisitionService.approveRequisition(requisitionId);
    }
}
