package org.pahappa.pettycashapp.systems.petty_cash_app.beans;
import org.pahappa.pettycashapp.systems.petty_cash_app.dao.UserDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.BudgetLine;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Category;
import org.pahappa.pettycashapp.systems.petty_cash_app.services.BudgetLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
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
    private  Date endDate;
    private int amount;
    private String name;
    private BudgetLine selectedDraftedBudgetLine;

    @Autowired
    private BudgetLineService budgetLineService;

    @Autowired
    UserDao userDao;

//    @PostConstruct
//    public void init(){
//        List<BudgetLine> budgetLines = budgetLineService.getApprovedBudgetLines();
//        for(BudgetLine budgetLine: budgetLines){
//            if(budgetLine.getEndDate().toInstant().isAfter(new Date().toInstant())){
//                budgetLine.setStatus("expired");
//                userDao.saveBudgetLine(budgetLine);
//            }
//        }
//    }

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

    public BudgetLine getSelectedDraftedBudgetLine() {
        return selectedDraftedBudgetLine;
    }

    public void setSelectedDraftedBudgetLine(BudgetLine selectedDraftedBudgetLine) {
        this.selectedDraftedBudgetLine = selectedDraftedBudgetLine;
    }

    //BUDGET LINE CODE
    public  void createBudgetLIne(int amount,String name, Date startDate,Date endDate,int categoryId){
        String message = budgetLineService.makeBudgetLine(amount,name,startDate,endDate,categoryId);
        if(message.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        }else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Budget line created successfully", null));
        }

    }
    public List<BudgetLine> currentBudgetLines(){
        return budgetLineService.returnCurrentBudgetLines();
    }

    public List<BudgetLine> getDraftedBudgetLines(){
        return budgetLineService.getDraftedBudgetLines();
    }

    public List<BudgetLine> getExpiredBudgetLines() {
        return budgetLineService.getExpiredBudgetLines();
    }

    public List<BudgetLine> getApprovedBudgetLines() {
        return budgetLineService.getApprovedBudgetLines();
    }

    public void selectBudgetLine(BudgetLine budgetLine) {
            this.selectedDraftedBudgetLine = budgetLine;
    }

    public String updateDraftedBL (BudgetLine budgetLine) {
        return "null";

    }
}
