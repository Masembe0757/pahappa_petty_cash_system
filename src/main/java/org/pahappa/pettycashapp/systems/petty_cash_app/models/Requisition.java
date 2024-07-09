package org.pahappa.pettycashapp.systems.petty_cash_app.models;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
@Entity
@Component
public class Requisition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private  int amount;
    private  String status;
    private String description;
    private final Date dateCreated = new Date();
    private Date dateNeeded;
    private Date dateApproved;
    private String referenceNumber;
    @ManyToOne
    private User user;

    @ManyToOne
    private BudgetLine budgetLine;

    @OneToOne
    private Review review;

    @OneToOne
    Accountability accountability;

    public BudgetLine getBudgetLine() {
        return budgetLine;
    }

    public void setBudgetLine(BudgetLine budgetLine) {
        this.budgetLine = budgetLine;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Accountability getAccountability() {
        return accountability;
    }

    public void setAccountability(Accountability accountability) {
        this.accountability = accountability;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    @Override
    public String toString() {
        return "Requisition{" +
                "amount=" + amount +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateNeeded=" + dateNeeded +
                ", dateApproved=" + dateApproved +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Requisition that = (Requisition) o;
        return amount == that.amount && Objects.equals(status, that.status) && Objects.equals(description, that.description) && Objects.equals(dateCreated, that.dateCreated) && Objects.equals(dateNeeded, that.dateNeeded) && Objects.equals(dateApproved, that.dateApproved);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, status, description, dateCreated, dateNeeded, dateApproved);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getDateNeeded() {
        return dateNeeded;
    }

    public void setDateNeeded(Date dateNeeded) {
        this.dateNeeded = dateNeeded;
    }

    public Date getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(Date dateApproved) {
        this.dateApproved = dateApproved;
    }
}
