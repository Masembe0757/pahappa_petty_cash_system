package org.pahappa.pettycashapp.systems.petty_cash_app.beans;

import org.pahappa.pettycashapp.systems.petty_cash_app.models.Review;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.User;
import org.pahappa.pettycashapp.systems.petty_cash_app.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.el.MethodExpression;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.List;
@SessionScope
@Component
public class ReviewBean {
    @Autowired
    ReviewService reviewService;
    private User currentUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        return (User) externalContext.getSessionMap().get("currentUser");
    }

    public void deleteReview(int reviewId) {
        reviewService.deleteReview(reviewId);

    }
    public List<Review> getReviewsOfUser() {
        return  reviewService.getReviewsOfUser(currentUser().getId());
    }
}
