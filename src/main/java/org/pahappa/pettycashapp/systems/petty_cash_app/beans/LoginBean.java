package org.pahappa.pettycashapp.systems.petty_cash_app.beans;

import org.pahappa.pettycashapp.systems.petty_cash_app.dao.RoleDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.dao.UserDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Permission;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Role;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.User;
import org.pahappa.pettycashapp.systems.petty_cash_app.routes.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.*;

@Service
@RequestScope
public class LoginBean {
    @Autowired
    Routes routes;
    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;

    public LoginBean() {
    }

    private String username;
    private String password;
    private int id;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PostConstruct
    public void init() {
        //ADDING OVEROLL ADMIN WITH A ROLE AND ALL PERMISSIONS

        Role role1 = roleDao.getRoleOfname("admin");
        if (role1 == null) {
            Role role = new Role();
            role.setName("admin");
            List<String> permissions = (Arrays.asList(
                    "MANAGE_CATEGORIES",
                    "APPROVE_BUDGET_LINE",
                    "MAKE_REQUISITION",
                    "REVIEW_REQUISITION",
                    "APPROVE_REQUISITION",
                    "PROVIDE_ACCOUNTABILITY",
                    "MANAGE_USERS",
                    "VIEW_USERS",
                    "VIEW_ROLES",
                    "MANAGE_ROLES",
                    "VIEW_REVIEW",
                    "VIEW_ADMIN_DASHBOARD",
                    "MANAGE_BUDGET_LINES",
                    "VIEW_STAGED_REQUISITIONS",
                    "VIEW_PENDING_REQUISITIONS",
                    "VIEW_DRAFTED_REQUISITIONS",
                    "VIEW_FULFILLED_REQUISITIONS",
                    "VIEW_APPROVED_REQUISITIONS",
                    "VIEW_ACCOUNTABILITY",
                    "VIEW_PENDING_BUDGET_LINES",
                    "VIEW_DRAFTED_BUDGET_LINES",
                    "VIEW_APPROVED_BUDGET_LINES",
                    "VIEW_ADMIN_LOGO",
                    "VIEW_BUDGET_LINE",
                    "VIEW_REQUISITION",
                    "VIEW_CATEGORIES",
                    "VIEW_EXPIRED_BUDGET_LINES",
                    "VIEW_REJECTED_REQUISITIONS",
                    "MANAGE_REQUISITION"
            ));
            for (String s : permissions) {
                Permission permission = new Permission();
                role.getPermissions().add(permission);
                permission.setRole(role);
                permission.setName(s);

            }
            roleDao.saveRole(role);
        }

        User admin = new User();
        admin.setFirstName("manager");
        admin.setLastName("manager");
        admin.setUserName("manager");
        admin.setPassword(Base64.getEncoder().encodeToString("manager".getBytes()));
        admin.setEmail("manager@gmail.com");
        admin.setRole("admin");
        User user = userDao.returnUser(admin.getUserName());
        if (user == null) {
            userDao.createAdmin(admin);
        }


    }


    public String login(String userName, String passWord) {
        User user = userDao.returnUser(userName);
        if (user != null) {
            String decodedPassword = new String(Base64.getDecoder().decode(user.getPassword()));
            if (decodedPassword.equals(passWord)) {
                FacesContext context = FacesContext.getCurrentInstance();
                ExternalContext externalContext = context.getExternalContext();
                externalContext.getSessionMap().put("currentUser", user);
                return routes.getDashboard();
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid username or password", null));
                return routes.getLogin();
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "User does not exist in database", null));
            return routes.getLogin();
        }
    }

    public void logout() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.getSessionMap().put("currentUser", null);
        externalContext.redirect(externalContext.getRequestContextPath() + routes.getLogin());
    }
}
