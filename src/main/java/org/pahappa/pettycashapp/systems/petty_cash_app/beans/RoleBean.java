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
import java.util.*;

@Component
@SessionScope
public class RoleBean implements Serializable {
    public  RoleBean(){}
    @Autowired
    RoleService roleService;

    private String name;
    List<String> selectedPermissions;

    public List<String> getSelectedPermissions() {
        return selectedPermissions;
    }

    public void setSelectedPermissions(List<String> selectedPermissions) {
        this.selectedPermissions = selectedPermissions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public List<String> permissions(){

        List<String> permissions = new ArrayList<>();
        permissions.add("Make Category");
        permissions.add("Approve Budget Line");
        permissions.add("Make Requisition");
        permissions.add("Review Requisition");
        permissions.add("Approve Requisition");
        permissions.add("View Users");
        return permissions;


    }


    public void saveRole(String name , List<String> permissions) {
    String message = roleService.saveRole(name,permissions);
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

    public String updateRole(String name , List<String> permissions) {
        String message = roleService.updateRoleOfId(name, permissions);
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

    public List<String> getPermissionsOfRole(int roleId){
        List<String> permissionNames = new ArrayList<>();
        List<Permission> permissions = roleService.getPermissionsOfRole(roleId);
        for(Permission permission : permissions){
            permissionNames.add(permission.getName());
        }
        return  permissionNames;
    }


}
