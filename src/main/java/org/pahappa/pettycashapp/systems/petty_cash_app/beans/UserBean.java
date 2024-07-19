package org.pahappa.pettycashapp.systems.petty_cash_app.beans;

import org.pahappa.pettycashapp.systems.petty_cash_app.dao.UserDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.*;
import org.pahappa.pettycashapp.systems.petty_cash_app.routes.Routes;
import org.pahappa.pettycashapp.systems.petty_cash_app.services.RoleService;
import org.pahappa.pettycashapp.systems.petty_cash_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.*;

@Component
@SessionScope
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
    private int userId;
    private User selectedUser;


    @Autowired
    private UserDao userDao;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

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

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public void getSelectedUserForUpdate(User user){
        this.selectedUser = user;
    }

    public User getCurrentUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        return (User) externalContext.getSessionMap().get("currentUser");
    }

    public String saveUser() {
        System.out.println("name   ......." + getLastname());
        String message = userService.saveUser(firstname, lastname, username, password1, password2, email, role);
        if (message.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCESS", "User added successfully "));
            return routes.getUsers();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", message));
        }
        return "";

    }

    public String updateUser(User userForUpdate) {
        String message = userService.updateUserOfUserName(userForUpdate);
        if (message.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCESS", "User updated successfully "));
            return routes.getUsers();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", message));
        }
        return "";

    }


    public void deleteUser(User user) {
        System.out.println("delete user");
        userService.deleteUserOfUserName(user.getUserName());
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,"SUCCESS" , "User deleted successfully"));
    }

    public void deleteAllUsers() {
        userService.deleteAllUsers();
    }

    public List<User> getUsersByName(String name) {
        List<User> users = userService.getAllUsers();
        List<User> userList = new ArrayList<>();
        if (name.isEmpty()) {
            for(User user : users) {
                if(user.getId()!=getCurrentUser().getId()) {
                    userList.add(user);
                }
            }

        } else {
            List<User> returnedUsers = userService.returnUserOfName(name);
            if (returnedUsers.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "No user found for that name"));
            } else {
                userList.addAll(returnedUsers);
            }
        }
        return userList;
    }

    public int countActiveUsers() {
        return userService.getAllUsers().size();
    }

    public int countDeletedUsers() {
        return userService.getDeletedUsers().size();
    }

}

