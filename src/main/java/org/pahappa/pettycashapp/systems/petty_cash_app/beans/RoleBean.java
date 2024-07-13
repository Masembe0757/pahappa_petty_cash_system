package org.pahappa.pettycashapp.systems.petty_cash_app.beans;

import org.pahappa.pettycashapp.systems.petty_cash_app.models.Permission;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Role;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.User;
import org.pahappa.pettycashapp.systems.petty_cash_app.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.el.MethodExpression;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@SessionScope
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



    public List<Permission> permissions(){

       return roleService.retunAllPermissions();
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

    public List<Role> getRoleByName(String name) {
        List<Role> roles = roleService.getAllRoles();
        List<Role> roleList = new ArrayList<>();
        if(name.isEmpty()){
            roleList.addAll(roles);

        }else {
            List<Role> returnedRoles = roleService.returnRoleOfName(name);
            if (returnedRoles.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "No role found for that name", null));
            }else {
                roleList.addAll(returnedRoles);
            }
        }
        return roleList;
    }

    public String updateRole(String name , int permission) {
        String message = roleService.updateRoleOfId(name,permission);
        if(message.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Role updated successfully ", null));
           // return routes.getUsers();
        }else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        }
        return "";
    }

    public void deleteRoleOfId(int roleId) {
        roleService.deleteRoleOfId(roleId);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Role deleted successfully", null));
    }
    public List<Role> getRoles(){
        return  roleService.getAllRoles();
    }

}
