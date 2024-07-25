package org.pahappa.pettycashapp.systems.petty_cash_app.beans;

import org.pahappa.pettycashapp.systems.petty_cash_app.dao.UserDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.*;
import org.pahappa.pettycashapp.systems.petty_cash_app.routes.Routes;
import org.pahappa.pettycashapp.systems.petty_cash_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.*;

@Service
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
    private String searchName;

    @Autowired
    private UserDao userDao;

    @PostConstruct
    public void init() {
        selectedUser = new User(); // Initialize with a new User object or fetch from a service
    }

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

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public void getSelectedUserForUpdate(User user) {
        this.selectedUser = user;
    }

    public User getCurrentUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        return (User) externalContext.getSessionMap().get("currentUser");
    }

    public void saveUser() {
        String message = userService.saveUser(firstname, lastname, username, password1, password2, email, role);
        if (message.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCESS", "User added successfully "));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", message));
        }

    }

    public void updateUser(String username, String firstname, String lastname, String password1, String password2, String email, String role) {
        String message = userService.updateUserOfUserName(username, firstname, lastname, password1, password2, email, role);
        if (message.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCESS", "User updated successfully "));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", message));
        }

    }


    public void deleteUser(int userId) {
        userService.deleteUserOfId(userId);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCESS", "User deleted successfully"));
    }

    public void deleteAllUsers() {
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            if (!user.getRole().equals("admin")) {
                userService.deleteUserOfId(user.getId());
            }
        }
    }

    public List<User> getUsersByName(String name) {

        return userService.returnUserOfName(name);
    }

    public int countActiveUsers() {
        return userService.getAllUsers().size();
    }

    public int countDeletedUsers() {
        return userService.getDeletedUsers().size();
    }

}

