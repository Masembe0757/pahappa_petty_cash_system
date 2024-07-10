package org.pahappa.pettycashapp.systems.petty_cash_app.models;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
public class Rejection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String information;

    @OneToOne
    BudgetLine budgetLine;
    @OneToOne
    Requisition requisition;
    @OneToOne
    Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public BudgetLine getBudgetLine() {
        return budgetLine;
    }

    public void setBudgetLine(BudgetLine budgetLine) {
        this.budgetLine = budgetLine;
    }

    public Requisition getRequisition() {
        return requisition;
    }

    public void setRequisition(Requisition requisition) {
        this.requisition = requisition;
    }
}
