package org.pahappa.pettycashapp.systems.petty_cash_app.beans;

import org.pahappa.pettycashapp.systems.petty_cash_app.dao.CategoryDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.dao.UserDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.BudgetLine;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Category;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.User;
import org.pahappa.pettycashapp.systems.petty_cash_app.services.BudgetLineService;
import org.pahappa.pettycashapp.systems.petty_cash_app.services.ReviewService;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Service
@SessionScope
public class BudgetLineBean implements Serializable {
    //Budget lines
    private static final long serialVersionUID = 1L;
    private int categoryId;
    private Date startDate;
    private Date endDate;
    private int amount;
    private int budgetLineId;
    private String name;
    private BudgetLine selectedBudgetLine;
    private String information;
    private MenuModel stepModel;

    @Autowired
    private CategoryDao categoryDao;

    public int getBudgetLineId() {
        return budgetLineId;
    }

    public void setBudgetLineId(int budgetLineId) {
        this.budgetLineId = budgetLineId;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Autowired
    private BudgetLineService budgetLineService;

    @Autowired
    UserDao userDao;
    @Autowired
    ReviewService reviewService;

    @PostConstruct
    public void init() {
        List<BudgetLine> budgetLines = budgetLineService.getApprovedBudgetLines();
        for (BudgetLine budgetLine : budgetLines) {
            if (budgetLine.getEndDate().toInstant().isBefore(new Date().toInstant())) {
                budgetLineService.updateBudgetLine(budgetLine.getId(),budgetLine.getBalance(),"expired");
            }
        }

        createStepModel();
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BudgetLine getSelectedBudgetLine() {
        return selectedBudgetLine;
    }

    public void setSelectedBudgetLine(BudgetLine selectedBudgetLine) {
        this.selectedBudgetLine = selectedBudgetLine;
    }

    private User getCurrentUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        return (User) externalContext.getSessionMap().get("currentUser");
    }

    //BUDGET LINE CODE
    public void createBudgetLIne(int amount, String name, Date startDate, Date endDate, int categoryId) {
        String message = budgetLineService.makeBudgetLine(amount, name, startDate, endDate, categoryId, getCurrentUser());
        if (message.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCESS", "Budget line created successfully"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "FAILURE", message));
        }

    }

    public MenuModel getStepModel() {
        return stepModel;
    }

    public void setStepModel(MenuModel stepModel) {
        this.stepModel = stepModel;
    }

    public List<BudgetLine> currentBudgetLines() {
        return budgetLineService.returnCurrentBudgetLines();
    }

    public List<BudgetLine> getDraftedBudgetLines() {
        return budgetLineService.getDraftedBudgetLines();
    }

    public List<BudgetLine> getExpiredBudgetLines() {
        return budgetLineService.getExpiredBudgetLines();
    }


    public String updateDraftedBL() {
        Category category = categoryDao.getCategoryOfId(categoryId);
        BudgetLine budgetLine = new BudgetLine();
        budgetLine.setName(name);
        budgetLine.setCategory(category);
        budgetLine.setStartDate(startDate);
        budgetLine.setEndDate(endDate);
        budgetLine.setAmountDelegated(amount);
        budgetLine.setId(budgetLineId);


        String message = budgetLineService.updateDraftedBL(budgetLine);
        if (message.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", message));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Success", "Budget line created successfully"));
        }
        return message;
    }

    public void cancelBudgetLine(BudgetLine budgetLine) {
        budgetLineService.deleteBL(budgetLine.getId());
    }

    public void submitBudgetLine(int budgetLineId) {
        budgetLineService.submitBudgetLine(budgetLineId);
    }

    public int countRunningBL() {
        return budgetLineService.returnCurrentBudgetLines().size();
    }

    public int countExpiredBL() {
        return budgetLineService.getExpiredBudgetLines().size();
    }

    public int countReviewedBL() {
        return budgetLineService.getApprovedBudgetLines().size() + budgetLineService.getRejectedBudgetLines().size();
    }

    public List<BudgetLine> getPendingBudgetLines() {
        return budgetLineService.getPendingBudgetLInes();
    }

    public List<BudgetLine> getAllBudgetLines() {
        return budgetLineService.getAllBudgetLines();
    }

    public List<BudgetLine> getRejectBudgetLineS() {
        return budgetLineService.getRejectedBudgetLines();
    }

    public void saveReview(String information, BudgetLine budgetLine, User user) {
        budgetLineService.setRejectionStatus(budgetLine.getId());
        reviewService.saveBudgetlineReview(information, new Date(), budgetLine, user);
    }

    public void deleteBudgetLine(int budgetLIneId) {
        budgetLineService.deleteBL(budgetLIneId);
    }

    public int getExpenditureBudgetOnApprovedBudgetLines() {
        int money = 0;
        List<BudgetLine> budgetLines = budgetLineService.getApprovedBudgetLines();
        for (BudgetLine budgetLine : budgetLines) {
            money = money + (budgetLine.getAmountDelegated() - budgetLine.getBalance());
        }
        return money;

    }

    public int getCurrentRunningBudgetLinesAmount() {
        int balance = 0;
        List<BudgetLine> budgetLines = budgetLineService.getApprovedBudgetLines();
        for (BudgetLine budgetLine : budgetLines) {
            balance = balance + budgetLine.getBalance();
        }
        return balance;
    }

    public void onRowSelect(SelectEvent event) {
        selectedBudgetLine = (BudgetLine) event.getObject();
    }

    private void createStepModel() {
        stepModel = new DefaultMenuModel();

        // Create steps using the builder pattern
        DefaultMenuItem step1 = DefaultMenuItem.builder()
                .value("Drafted")
                .build();

        DefaultMenuItem step2 = DefaultMenuItem.builder()
                .value("Pending")
                .build();

        DefaultMenuItem step3 = DefaultMenuItem.builder()
                .value("Approved")
                .build();

        DefaultMenuItem step4 = DefaultMenuItem.builder()
                .value("Expired")
                .build();

        // Add steps to the model
        stepModel.getElements().add(step1);
        stepModel.getElements().add(step2);
        stepModel.getElements().add(step3);
        stepModel.getElements().add(step4);

    }

    public int getActiveIndex() {
        String status = selectedBudgetLine.getStatus();
        switch (status) {
            case "drafted":
                return 0;
            case "pending":
                return 1;
            case "approved":
                return 2;
            case "expired":
                return 3;
            default:
                return -1;
        }
    }

    public void resetDialog(){
        this.name = null;
        this.startDate = null;
        this.endDate = null;
        this.amount = 0;
        this.categoryId = 0;
    }

}
