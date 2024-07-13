package org.pahappa.pettycashapp.systems.petty_cash_app.beans;

import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
@SessionScoped
@ManagedBean
public class NavigationBean {
    private String selectedRequisitionsSection = "allRequisitions";
    private String selectedBudgetLineSection = "currentBudgetLines";
    private String selectedUserSection = "availableUsers";
    private String selectedReviewSection = "pendingRequisitions";
    private String selectedUserSection = "usersSection";

    public void setSelectedUserSection(String selectedUserSection) {
        this.selectedUserSection = selectedUserSection;
    }

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

    public String getSelectedReviewSection() {
        return selectedReviewSection;
    }

    public void setSelectedReviewSection(String selectedReviewSection) {
        this.selectedReviewSection = selectedReviewSection;
    }

    public void changeReviewSection(String ReviewSection) {
        this.selectedReviewSection = ReviewSection;
    }

    public String getSelectedUserSection() {
        return selectedUserSection;
    }

    public void changeUserSection(String UserSection) {
        this.selectedUserSection = UserSection;
    }
}

