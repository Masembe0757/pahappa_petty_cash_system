package org.pahappa.pettycashapp.systems.petty_cash_app.beans;

import org.pahappa.pettycashapp.systems.petty_cash_app.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.el.MethodExpression;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RoleBean implements Serializable {
    public  RoleBean(){}
    @Autowired
    RoleService roleService;

    private String name;
    private  int permission;

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public List<Map.Entry<String, Integer>> permissions(){
        final List<Map.Entry<String, Integer>> keyValueList = new ArrayList<>();
        // Add key-value pairs to the list
        keyValueList.add(new AbstractMap.SimpleEntry<>("Make Category", 1));
        keyValueList.add(new AbstractMap.SimpleEntry<>("Make Budget Line", 2));
        keyValueList.add(new AbstractMap.SimpleEntry<>("Approve Budget Line", 3));
        keyValueList.add(new AbstractMap.SimpleEntry<>("Make Requisition", 4));
        keyValueList.add(new AbstractMap.SimpleEntry<>("Review Requisition", 5));
        keyValueList.add(new AbstractMap.SimpleEntry<>("Approve Requisition", 6));
        keyValueList.add(new AbstractMap.SimpleEntry<>("View Users", 7));
        return  keyValueList;
    }


    public void saveRole(String name , int permission) {
    String message = roleService.saveRole(name,permission);
    if(message.isEmpty()){
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Role created successfully", null));
    }else {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }
    }
}
