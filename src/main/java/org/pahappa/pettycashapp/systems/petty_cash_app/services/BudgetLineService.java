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
@SessionScope
@Service
public class BudgetLineService {

    @Autowired
    BudgetLineDao budgetLineDao;
    @Autowired
    CategoryDao categoryDao;

    BudgetLineService budgetLineService;

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
        }else if (endDate.toInstant().isBefore(new Date().toInstant())) {
            error_message = "End date can be before current date";
        }else if (startDate.toInstant().isBefore(new Date().toInstant())) {
            error_message = "Start date can be before current date";
        }else if (endDate.toInstant().isBefore(startDate.toInstant())) {
            error_message = "End date can be before start date";
        } else {
            BudgetLine budgetLine = new BudgetLine();
            Category category = categoryDao.getCategoryOfId(categoryId);
            budgetLine.setAmountDelegated(amount);
            budgetLine.setBalance(amount);
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
            error_message = " Name can not be Empty";
        }else if (budgetLine.getStartDate().after(budgetLine.getEndDate())) {
            error_message = "Start date can not be beyond end date";
        } else if (budgetLine.getEndDate().toInstant().isBefore(new Date().toInstant())) {
            error_message = "End date can be before current date";
        } else if (budgetLine.getStartDate().toInstant().isBefore(new Date().toInstant())) {
            error_message = "Start date can be before current date";
        }else if (budgetLine.getEndDate().toInstant().isBefore(budgetLine.getStartDate().toInstant())) {
            error_message = "End date can be before start date";
        } else {
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
        budgetLineDao.changeBudgetLineStatus(budgetLneId, "approved");
    }
    //Reviewied by HR awaiting CEO approval


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
        return budgetLineDao.getApprovedBudgetLines("expired");
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

    public int countAllBL(){
        return getAllBudgetLines().size();
    }

    public void submitBudgetLine(int budgetLineId){
        budgetLineDao.changeBudgetLineStatus(budgetLineId,"pending");
    }

    public List<BudgetLine> getPendingBudgetLInes() {
        return budgetLineDao.getPendingBudgetLInes("pending");
    }

    public List<BudgetLine> getAllBudgetLines() {
        return budgetLineDao.getAllBudgetLines();
    }

    public void setRejectionStatus(int budgetLineId) {
        budgetLineDao.changeBudgetLineStatus(budgetLineId,"rejected");
    }
    public List<BudgetLine> getRejectedBudgetLines() {
        return  budgetLineDao.getRejectedBudgetLines();
    }
    public  void updateBudgetLine(int budgetLineId, int balance, String status){
        budgetLineDao.updateBudgetLIne(budgetLineId,balance,status);

    }


}