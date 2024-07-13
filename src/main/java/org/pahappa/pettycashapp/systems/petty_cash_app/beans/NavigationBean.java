package org.pahappa.pettycashapp.systems.petty_cash_app.beans;

import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
@SessionScoped
@Component
public class NavigationBean {
    private String selectedRequisitionsSection = "allRequisitions";
    private String selectedBudgetLineSection = "currentBudgetLines";
    private String selectedUserSection = "availableUsers";

    public String getSelectedUserSection() {
        return selectedUserSection;
    }

    public void setSelectedUserSection(String selectedUserSection) {
        this.selectedUserSection = selectedUserSection;
    }


    public String getSelectedBudgetLineSection() {
        return selectedBudgetLineSection;
    }

    public void setSelectedBudgetLineSection(String selectedBudgetLineSection) {
        this.selectedBudgetLineSection = selectedBudgetLineSection;
    }

    public void changeBudgetLineSection(String BudgetLineSection) {
        this.selectedBudgetLineSection = BudgetLineSection;
    }
    public void changeUserSection(String userSelection) {
        this.selectedUserSection = userSelection;
    }

    public String getSelectedRequisitionsSection() {
        return selectedRequisitionsSection;
    }

    public void setSelectedRequisitionsSection(String selectedRequisitionsSection) {
        this.selectedRequisitionsSection = selectedRequisitionsSection;
    }

    public void changeRequisitionsSections(String RequisitionSection) {
        this.selectedRequisitionsSection = RequisitionSection;
    }
}

