package org.pahappa.pettycashapp.systems.petty_cash_app.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class NavigationBean {
    private String selectedRequisitionsSection = "allRequisitions";
    private String selectedBudgetLineSection = "currentBudgetLines";

    public String getSelectedBudgetLineSection() {
        return selectedBudgetLineSection;
    }

    public void setSelectedBudgetLineSection(String selectedBudgetLineSection) {
        this.selectedBudgetLineSection = selectedBudgetLineSection;
    }

    public void changeBudgetLineSection(String BudgetLineSection) {
        this.selectedBudgetLineSection = BudgetLineSection;
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

