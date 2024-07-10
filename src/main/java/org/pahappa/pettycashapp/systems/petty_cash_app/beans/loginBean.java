package org.pahappa.pettycashapp.systems.petty_cash_app.beans;
import org.pahappa.pettycashapp.systems.petty_cash_app.dao.UserDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.User;


import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.Base64;
@ManagedBean
@RequestScoped
public class loginBean {
    public loginBean(){}

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
        User admin = new User();
        admin.setFirstName("manager");
        admin.setLastName("manager");
        admin.setUserName("manager");
        admin.setPassword(Base64.getEncoder().encodeToString("manager".getBytes()));
        admin.setEmail("manager@gmail.com");
        admin.setRole(1);
        User user = UserDao.getUserDao().returnUser(admin.getUserName());
        if(user==null){
            UserDao.getUserDao().createAdmin(admin);
        }
    }


    public String login(String userName, String passWord) {
        User user = UserDao.getUserDao().returnUser(userName);
        if (user != null) {
            String decodedPassword = new String(Base64.getDecoder().decode(user.getPassword()));
            if(decodedPassword.equals(passWord)){
                    FacesContext context = FacesContext.getCurrentInstance();
                    ExternalContext externalContext = context.getExternalContext();
                    externalContext.getSessionMap().put("currentUser", user);
                    return "/pages/protected/home/home.xhtml";
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid username or password", null));
                return "/pages/login.xhtml";
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "User does not exist in database", null));
            return "/pages/login.xhtml";
        }
    }

    public void logout() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.getSessionMap().put("currentUser", null);
        externalContext.redirect(externalContext.getRequestContextPath() +"/pages/login.xhtml");
    }
}
