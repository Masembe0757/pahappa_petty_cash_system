package org.pahappa.pettycashapp.systems.petty_cash_app.models;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
@Entity
@Table(name = "budget_line_table")
public class BudgetLine {
    public BudgetLine(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int amountDelegated;
    private int balance;
    private Date startDate;
    private Date endDate;
    private String status ="drafted";
    private final Date dateCreated = new Date();
    @ManyToOne
    private Category category;
    @ManyToOne
    private User user;
    @OneToOne(mappedBy = "budgetLine")
    private Review review;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @OneToMany(mappedBy="budgetLine", cascade = CascadeType.ALL)
    private List<Requisition> requisitions = new ArrayList<>();

    public int getBalance() { return balance; }

    public void setBalance(int balance) { this.balance = balance; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public List<Requisition> getRequisitions() {
        return requisitions;
    }

    public void setRequisitions(List<Requisition> requisitions) {
        this.requisitions = requisitions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmountDelegated() {
        return amountDelegated;
    }

    public void setAmountDelegated(int amountDelegated) {
        this.amountDelegated = amountDelegated;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BudgetLine{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", amountDelegated='" + amountDelegated + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BudgetLine that = (BudgetLine) o;
        return id == that.id && Objects.equals(category, that.category) && Objects.equals(amountDelegated, that.amountDelegated) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, amountDelegated, startDate, endDate, status);
    }
}
