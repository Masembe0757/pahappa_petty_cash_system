package org.pahappa.pettycashapp.systems.petty_cash_app.routes;
import org.springframework.stereotype.Component;

@Component
public class Routes {
    private final String login  = "/pages/login.xhtml";
    private final String dashboard  = "/pages/protected/admin/dashboard.xhtml";
    private final String budgetLines  = "/pages/protected/admin/budgetLines.xhtml";
    private final String users  = "/pages/protected/admin/users.xhtml?faces-redirect=true";
    private final String requisitions  = "/pages/protected/admin/requisitions.xhtml";
    private final String requisitionDrafts = "/pages/protected/admin/requisitionsDrafts.xhtml";
    private final String accountability = "/pages/protected/admin/accountability.xhtml";

    public String getRequisitionDrafts() {
        return requisitionDrafts;
    }
    public String getAccountability() {
        return accountability;
    }
    public String getLogin() {
        return login;
    }

    public String getDashboard() {
        return dashboard;
    }

    public String getBudgetLines() {
        return budgetLines;
    }

    public String getUsers() {
        return users;
    }

    public String getRequisitions() {
        return requisitions;
    }
}
