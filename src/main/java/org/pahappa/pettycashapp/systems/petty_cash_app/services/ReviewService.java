package org.pahappa.pettycashapp.systems.petty_cash_app.services;

import org.pahappa.pettycashapp.systems.petty_cash_app.dao.ReviewDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.BudgetLine;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Review;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    ReviewDao reviewDao;
    public void saveBudgetlineReview(String information, Date date, BudgetLine budgetLine, User user) {
        Review review = new Review();
        review.setDescription(information);
        review.setReviewedDate(date);
        review.setBudgetLine(budgetLine);
        review.setUser(user);
        reviewDao.saveBudgetlineReview(review);
    }

    public List<Review> getReviewsOfUser(int id) {
        return reviewDao.getReviewsOfUser(id);
    }

    public void deleteReview(int reviewId) {
        reviewDao.deleteReview(reviewId);
    }
}
