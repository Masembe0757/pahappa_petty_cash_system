package org.pahappa.pettycashapp.systems.petty_cash_app.beans;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.BudgetLine;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Category;
import org.pahappa.pettycashapp.systems.petty_cash_app.services.BudgetLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Component
public class BudgetLineBean implements Serializable {
    //Budget lines
    private int categoryId;
    private Date startDate;
    private  Date endDate;
    private int amount;
    private String name;
    @Autowired
    BudgetLineService budgetLineService;

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

}
