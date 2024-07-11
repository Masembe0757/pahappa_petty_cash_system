package org.pahappa.pettycashapp.systems.petty_cash_app.models;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
@Entity
@Table(name = "review_table")
public class Review {
    public Review(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private Date reviewedDate = new Date();

    @ManyToOne
    private User user;

    @OneToOne
    private Requisition requisition;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Requisition getRequisition() {
        return requisition;
    }

    public void setRequisition(Requisition requisition) {
        this.requisition = requisition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReviewedDate() {
        return reviewedDate;
    }

    public void setReviewedDate(Date reviewedDate) {
        this.reviewedDate = reviewedDate;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", reviewedDate=" + reviewedDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id == review.id && Objects.equals(description, review.description) && Objects.equals(reviewedDate, review.reviewedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, reviewedDate);
    }
}
