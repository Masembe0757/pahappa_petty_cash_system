package org.pahappa.pettycashapp.systems.petty_cash_app.services;

import org.pahappa.pettycashapp.systems.petty_cash_app.dao.BudgetLineDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.dao.RequisitionDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.dao.ReviewDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.dao.UserDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.BudgetLine;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Requisition;
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
    @Autowired
    RequisitionDao requisitionDao;
    @Autowired
    RequisitionService requisitionService;
    @Autowired
    UserDao userDao;
    @Autowired
    BudgetLineDao budgetLineDao;
    public void saveBudgetlineReview(String information, Date date, BudgetLine budgetLine, User user) {
        Review review = new Review();
        review.setDescription(information);
        review.setReviewedDate(date);
        review.setBudgetLine(budgetLine);
        review.setUser(user);
        reviewDao.saveBudgetlineReview(review);
    }
    public void saveRequisitionReview(String information, Date date, Requisition requisition,User user,BudgetLine budgetLine) {
        requisitionService.setRejectionStatus(requisition.getId());
        //Adding back the amount
        budgetLine.setBalance(budgetLine.getBalance()+requisition.getAmount());
        budgetLineDao.updateBudgetLIne(budgetLine);

        Review review = new Review();
        review.setRequisition(requisition);
        review.setDescription(information);
        review.setReviewedDate(date);
        review.setUser(user);
        reviewDao.saveRequisitionReview(review);
    }

    public List<Review> getReviewsOfUser(int userId) {
        return reviewDao.getReviewsOfUser(userId);

    }

    public void deleteReview(int reviewId) {
        reviewDao.deleteReview(reviewId);
    }
}
