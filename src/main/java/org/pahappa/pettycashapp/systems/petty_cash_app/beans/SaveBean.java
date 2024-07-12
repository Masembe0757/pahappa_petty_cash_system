package org.pahappa.pettycashapp.systems.petty_cash_app.beans;

import org.pahappa.pettycashapp.systems.petty_cash_app.dao.UserDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.BudgetLine;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Category;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Requisition;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.User;
import org.pahappa.pettycashapp.systems.petty_cash_app.routes.Routes;
import org.pahappa.pettycashapp.systems.petty_cash_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@SessionScoped
public class SaveBean {
    @Autowired
    UserService userService;
    @Autowired
    Routes routes;
    private String username;
    private String firstname;
    private String lastname;
    private String password1;
    private String password2;
    private String email;
    private String role;
    private String name;

    //REquisition
    private int amount;
    private int budgetLineId;
    private String description;
    private Date dateNeeded;

    //Budget lines
    private int categoryId;
    private Date startDate;
    private  Date endDate;

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateNeeded() {
        return dateNeeded;
    }

    public void setDateNeeded(Date dateNeeded) {
        this.dateNeeded = dateNeeded;
    }

    public int getBudgetLineId() {
        return budgetLineId;
    }

    public void setBudgetLineId(int budgetLineId) {
        this.budgetLineId = budgetLineId;
    }

    @Autowired
    private UserDao userDao;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String saveUser(String firstName, String lastName, String userName, String password1, String password2, String email, String role){
        String message = userService.saveUser(firstName,lastName,userName,password1,password2,email,Integer.parseInt(role));
        if(message.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "User added successfully ", null));
            return routes.getUsers();
        }else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        }
        return "";

    }
    public String updateUser(String firstName, String lastName, String userName, String password1, String password2, String email,String role){
        String message = userService.updateUserOfUserName(firstName,lastName,userName,password1,password2,email,Integer.parseInt(role));
        if(message.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "User updated successfully ", null));
            return routes.getUsers();
        }else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        }
        return "";

    }
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }


    public void deleteUserOfUserName(String userName) {
        System.out.println("delete user");
        userService.deleteUserOfUserName(userName);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "User deleted successfully", null));
    }

    public void deleteAllUsers() {
        userService.deleteAllUsers();
    }

    public List<User> getUsersByName(String name) {
        List<User> users = userService.getAllUsers();
        List<User> userList = new ArrayList<>();
        if(name.isEmpty()){
            userList.addAll(users);

        }else {
            List<User> returnedUsers = userService.returnUserOfName(name);
            if (returnedUsers.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "No user found for that name", null));
            }else {
                userList.addAll(returnedUsers);
            }
        }
        return userList;
    }
    public String defineRoles(int role){
        if(role==1){
            return "C-E-O";
        } else if (role==2) {
            return "H-R";
        }
        else if(role==0) {
            return "Normal user";
        }
        else {
            return "O-P-S";
        }
    }

    //REQUISITIONS CODE
    public void makeRequistion(int amount,Date dateNeeded,String description,int budgetLineId){
        System.out.println("SAVING REQUISITION1");
        String message = userService.makeRequisition(amount,dateNeeded,description,budgetLineId);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }


    public List<Requisition> getAllRequisitions() {
        return userService.getAllRequisitions();
    }

    public List<BudgetLine> budgetLines() {
       return userService.getApprovedBudgetLines();
    }



    //BUDGET LINE CODE
    public  void createBudgetLIne(int amount,String name, Date startDate,Date endDate,int categoryId){
        String message = userService.makeBudgetLine(amount,name,startDate,endDate,categoryId);
        if(message.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        }else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Budget line created successfully", null));
        }

    }
    public  List<Category> getCategories(){
       return userService.getCategories();
    }













}


