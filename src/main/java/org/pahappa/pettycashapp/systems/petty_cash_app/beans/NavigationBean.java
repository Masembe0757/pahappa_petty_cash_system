package org.pahappa.pettycashapp.systems.petty_cash_app.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean
public class NavigationBean {
    private String selectedRequisitionsSection = "allRequisitions";
    private String selectedBudgetLineSection = "currentBudgetLines";
    private String selectedReviewSection = "pendingRequisitions";

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

    public String getSelectedReviewSection() {
        return selectedReviewSection;
    }

    public void setSelectedReviewSection(String selectedReviewSection) {
        this.selectedReviewSection = selectedReviewSection;
    }

    public void changeReviewSection(String ReviewSection) {
        this.selectedReviewSection = ReviewSection;
    }
}

