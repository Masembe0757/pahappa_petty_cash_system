package org.pahappa.pettycashapp.systems.petty_cash_app.models;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "rejection_table")
public class Rejection {
    public Rejection(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String information;

    @OneToOne
    BudgetLine budgetLine;
    @OneToOne
    Requisition requisition;

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
