package org.pahappa.pettycashapp.systems.petty_cash_app.services;

import org.pahappa.pettycashapp.systems.petty_cash_app.dao.BudgetLineDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.dao.UserDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.BudgetLine;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Category;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Rejection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@SessionScope
public class BudgetLineService {

    @Autowired
    UserDao userDao;

    @Autowired
    BudgetLineDao budgetLineDao;

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

    public String makeBudgetLine(int amount, String name, Date startDate, Date endDate, int categoryId) {
        String error_message = "";
        if (hasDigits(name)) {
            error_message = " Name can not contain digits";
        } else {
            BudgetLine budgetLine = new BudgetLine();
            Category category = userDao.getCategoryOfId(categoryId);
            budgetLine.setAmountDelegated(amount);
            budgetLine.setCategory(category);
            budgetLine.setEndDate(endDate);
            budgetLine.setStartDate(startDate);
            budgetLine.setName(name);
            userDao.saveBudgetLine(budgetLine);
        }
        return error_message;

    }

    public String updateDraftedBL(BudgetLine budgetLine){
        String error_message = "";
        if (hasDigits(budgetLine.getName())) {
            error_message = " Name can not contain digits";
        } else if (budgetLine.getName().isEmpty() || budgetLine.getName() == null ) {
            error_message = " Name can Empty";
        } else {
            budgetLineDao.updateDrafted(budgetLine);
        }

        return error_message;
    }

    //for finance
    public List<BudgetLine> getDraftedBudgetLines() {
        return userDao.getDraftedBudgetLines("drafted");
    }

    //for user
    public List<BudgetLine> getApprovedBudgetLines() {
        return userDao.getApprovedBudgetLines("approved");
    }

    //CEO approval
    public void approveBudgetLine(int budgetLneId) {
        userDao.approveBudgetLine(budgetLneId, "approved");
    }
    //Reviewied by HR awaiting CEO approval

    public void rejectBudgetLine(int budgetLneId, String information) {
        Rejection rejection = new Rejection();
        BudgetLine budgetLine = userDao.returnBudgetLineofId(budgetLneId);
        rejection.setBudgetLine(budgetLine);
        rejection.setInformation(information);
        userDao.saveRejection(rejection);
    }

    public List<BudgetLine> budgetLines() {
        return getApprovedBudgetLines();
    }

    public List<BudgetLine> returnCurrentBudgetLines() {
        List<BudgetLine> budgetLines = userDao.getApprovedBudgetLines("approved");
        List<BudgetLine> budgetLines1 = new ArrayList<>();
        for (BudgetLine budgetLine : budgetLines) {
            if (budgetLine.getEndDate().after(new Date())) {
                budgetLines1.add(budgetLine);
            }
        }
        return budgetLines1;
    }

    public List<BudgetLine> getExpiredBudgetLines() {
        List<BudgetLine> bL = userDao.getApprovedBudgetLines("approved");
        List<BudgetLine> expiredBL = new ArrayList<>();
        for (BudgetLine budgetLine : bL) {
            if (budgetLine.getEndDate().before(new Date())) {
                expiredBL.add(budgetLine);
            }
        }
        return expiredBL;
    }

    public void deleteBL(BudgetLine budgetLine) {
        budgetLineDao.deleteBudgetLine(budgetLine.getId());
    }

    public List<Number> getExpenditurePerBudgetLine() {
        List<BudgetLine> budgetLines = getExpiredBudgetLines();
        List<Number> expendituresPerBudgetLine = new ArrayList<>();
        for (BudgetLine budgetLine : budgetLines) {
           int balance = budgetLine.getBalance();
           int amountDelegated = budgetLine.getAmountDelegated();
           int expenditurePerBudgetLine = amountDelegated - balance;

           expendituresPerBudgetLine.add(expenditurePerBudgetLine);
        }

        return expendituresPerBudgetLine;
    }

    public List<String> getBLnames() {
        List<BudgetLine> budgetLines = getExpiredBudgetLines();
        List<String> names = new ArrayList<>();
        for (BudgetLine budgetLine : budgetLines) {
            String name = budgetLine.getName();
            names.add(name);
        }

        return names;
    }

    public void stageBudgetLine(BudgetLine budgetLine){
        budgetLineDao.stageBudgetLine(budgetLine);
    }
}