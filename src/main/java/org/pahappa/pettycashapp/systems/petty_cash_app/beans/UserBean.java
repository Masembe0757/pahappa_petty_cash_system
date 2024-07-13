package org.pahappa.pettycashapp.systems.petty_cash_app.beans;

import org.pahappa.pettycashapp.systems.petty_cash_app.dao.UserDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.BudgetLine;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Category;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Requisition;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.User;
import org.pahappa.pettycashapp.systems.petty_cash_app.routes.Routes;
import org.pahappa.pettycashapp.systems.petty_cash_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserBean implements Serializable {
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
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
        String message = userService.saveUser(firstName,lastName,userName,password1,password2,email,role);
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
        String message = userService.updateUserOfUserName(firstName,lastName,userName,password1,password2,email,role);
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
}


