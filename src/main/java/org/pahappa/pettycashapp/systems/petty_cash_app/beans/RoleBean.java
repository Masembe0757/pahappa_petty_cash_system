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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.*;

@Component
@SessionScope
public class RoleBean implements Serializable {
    public  RoleBean(){}
    @Autowired
    RoleService roleService;
    private  List<String> permissions = new ArrayList<>(Arrays.asList("Make Category","Approve Budget Line","Make " +
            "Requisition","Review Requisition","Approve Requisition","Provide accountability","View Users"));
    private String name;
    List<String> selectedPermissions;

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

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







    public void saveRole(String name , List<String> selectedPermissions) {
        System.out.println("ROLE SAVING");
        System.out.println("PERMISSIONS"+selectedPermissions);
        System.out.println("NAME"+ name);
    String message = roleService.saveRole(name,selectedPermissions);
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


    public List<Permission> permissionsForRole(int role_id) {
        return roleService.returnPermissionsForRole(role_id);
    }

    public void deleteAllRoles() {
    }

    // PERMISSIONS CHECK FOR UI RENDERING


    private User getCurrentUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        return (User) externalContext.getSessionMap().get("currentUser");
    }

    public boolean canViewUsers(){
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for(Permission permission:permissions){
            if (permission.getName().equals("View Users")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean canApproveRequisitions(){
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for(Permission permission:permissions){
            if (permission.getName().equals("Approve Requisition")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean CanReviewRequisitions(){
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for(Permission permission:permissions){
            if (permission.getName().equals("Review Requisition")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean CanApproveBudgetLine(){
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for(Permission permission:permissions){
            if (permission.getName().equals("Approve Budget Line")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }
    public boolean CanMakeCategory(){
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for(Permission permission:permissions){
            if (permission.getName().equals("Make Category")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }
    public boolean CanMakeRequisition(){
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for(Permission permission:permissions){
            if (permission.getName().equals("Make Requisition")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }


    public boolean CanViewBudgetLines(){
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for(Permission permission:permissions){
            if (permission.getName().equals("View BudgetLines")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }



}
