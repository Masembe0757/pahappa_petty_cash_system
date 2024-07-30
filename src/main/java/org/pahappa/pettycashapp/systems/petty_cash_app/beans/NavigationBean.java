package org.pahappa.pettycashapp.systems.petty_cash_app.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class NavigationBean implements Serializable {
    private String activePage;

    public String getActivePage() {
        return activePage;
    }

    public void setActivePage(String activePage) {
        this.activePage = activePage;
    }

    public NavigationBean(){
        activePage = "dashboard";
    }

    public void updateActivePage(ComponentSystemEvent event){
        FacesContext facesContext = FacesContext.getCurrentInstance();

            String viewId = facesContext.getViewRoot().getViewId();
            if (viewId != null) {
                activePage = viewId.substring(viewId.lastIndexOf('/') + 1, viewId.lastIndexOf('.'));
        }
    }


}
