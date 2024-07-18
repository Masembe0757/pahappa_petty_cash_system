package org.pahappa.pettycashapp.systems.petty_cash_app.services;
import org.pahappa.pettycashapp.systems.petty_cash_app.dao.BudgetLineDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.dao.CategoryDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.BudgetLine;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Category;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@SessionScope
public class BudgetLineService {

    @Autowired
    BudgetLineDao budgetLineDao;
    @Autowired
    CategoryDao categoryDao;

    //Generic method to check if name provided has digits in it
    private boolean hasDigits(String str) {
        boolean hasDigits = false;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                hasDigits = true;
            }
        }
        return hasDigits;
    }

    public String makeBudgetLine(int amount, String name, Date startDate, Date endDate, int categoryId, User user) {
        String error_message = "";
        if (hasDigits(name)) {
            error_message = " Name can not contain digits";
        } else if (startDate.after(endDate)) {
            error_message = "Start date can not be beyond end date";
        } else {
            BudgetLine budgetLine = new BudgetLine();
            Category category = categoryDao.getCategoryOfId(categoryId);
            budgetLine.setAmountDelegated(amount);
            budgetLine.setCategory(category);
            budgetLine.setEndDate(endDate);
            budgetLine.setStartDate(startDate);
            budgetLine.setUser(user);
            budgetLine.setName(name);
            budgetLineDao.saveBudgetLine(budgetLine);
        }
        return error_message;

    }

    public String updateDraftedBL(BudgetLine budgetLine){
        String error_message = "";
        if (hasDigits(budgetLine.getName())) {
            error_message = " Name can not contain digits";
        } else if (budgetLine.getName().isEmpty() || budgetLine.getName() == null ) {
            error_message = " Name can Empty";
        }else if (budgetLine.getStartDate().after(budgetLine.getEndDate())) {
            error_message = "Start date can not be beyond end date";
        }  else {
            budgetLineDao.updateDrafted(budgetLine);
        }

        return error_message;
    }

    //for finance
    public List<BudgetLine> getDraftedBudgetLines() {
        return budgetLineDao.getDraftedBudgetLines("drafted");
    }

    //for user
    public List<BudgetLine> getApprovedBudgetLines() {
        return budgetLineDao.getApprovedBudgetLines("approved");
    }

    //CEO approval
    public void approveBudgetLine(int budgetLneId) {
        budgetLineDao.approveBudgetLine(budgetLneId, "approved");
    }
    //Reviewied by HR awaiting CEO approval


    public List<BudgetLine> budgetLines() {
        return getApprovedBudgetLines();
    }

    public List<BudgetLine> returnCurrentBudgetLines() {
        List<BudgetLine> budgetLines = budgetLineDao.getApprovedBudgetLines("approved");
        List<BudgetLine> budgetLines1 = new ArrayList<>();
        for (BudgetLine budgetLine : budgetLines) {
            if (budgetLine.getEndDate().after(new Date())) {
                budgetLines1.add(budgetLine);
            }
        }
        return budgetLines1;
    }

    public List<BudgetLine> getExpiredBudgetLines() {
        List<BudgetLine> bL = budgetLineDao.getApprovedBudgetLines("approved");
        List<BudgetLine> expiredBL = new ArrayList<>();
        for (BudgetLine budgetLine : bL) {
            if (budgetLine.getEndDate().before(new Date())) {
                expiredBL.add(budgetLine);
            }
        }
        return expiredBL;
    }

    public void deleteBL(int budgetLineId) {
        budgetLineDao.deleteBudgetLine(budgetLineId);
    }

    public List<Number> getExpenditurePerBudgetLine() {
        List<BudgetLine> budgetLines = budgetLineDao.getApprovedBudgetLines("approved");
        List<Number> expendituresPerBudgetLine = new ArrayList<>();
        for (BudgetLine budgetLine : budgetLines) {
           int balance = budgetLine.getBalance();
           int amountDelegated = budgetLine.getAmountDelegated();
           int expenditurePerBudgetline = amountDelegated - balance;

           expendituresPerBudgetLine.add(expenditurePerBudgetline);
        }

        return expendituresPerBudgetLine;
    }

    public List<String> getBLnames() {
        List<BudgetLine> budgetLines = getApprovedBudgetLines();
        List<String> names = new ArrayList<>();
        for (BudgetLine budgetLine : budgetLines) {
            String name = budgetLine.getName();
            names.add(name);
        }

        return names;
    }

    public void submitBudgetLine(int budgetLineId){
        budgetLineDao.submitBudgetLine(budgetLineId,"pending");
    }

    public List<BudgetLine> getPendingBudgetLInes() {
        return budgetLineDao.getPendingBudgetLInes("pending");
    }

    public List<BudgetLine> getAllBudgetLines() {
        return budgetLineDao.getAllBudgetLines();
    }

    public void setRejectionStatus(int budgetLineId) {
        budgetLineDao.setBudgetLineRejectionStatus(budgetLineId);
    }
    public List<BudgetLine> getRejectedBudgetLines() {
        return  budgetLineDao.getRejectedBudgetLines();
    }

    public void saveBudgetline(BudgetLine budgetLine) {
        budgetLineDao.saveBudgetLine(budgetLine);
    }

}