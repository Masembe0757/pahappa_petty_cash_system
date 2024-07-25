package org.pahappa.pettycashapp.systems.petty_cash_app.beans;

import org.pahappa.pettycashapp.systems.petty_cash_app.models.Permission;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Role;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.User;
import org.pahappa.pettycashapp.systems.petty_cash_app.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.*;

@Service
@SessionScope
public class RoleBean implements Serializable {
    public RoleBean() {
    }

    @Autowired
    RoleService roleService;
    private List<String> permissions =
            new ArrayList<>(Arrays.asList(
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


    private String name;
    private String searchName;
    private List<String> selectedPermissions;
    private String nameUp;
    private List<String> selectedPermissionsUp;
    private int role_id;

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public List<String> getSelectedPermissionsUp() {
        return selectedPermissionsUp;
    }

    public void setSelectedPermissionsUp(List<String> selectedPermissionsUp) {
        this.selectedPermissionsUp = selectedPermissionsUp;
    }

    public String getNameUp() {
        return nameUp;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public void setNameUp(String nameUp) {
        this.nameUp = nameUp;
    }

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


    public void saveRole(String name, List<String> selectedPermissions) {
        String message = roleService.saveRole(name, selectedPermissions);
        if (message.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCESS", "Role created successfully"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "FAILED", message));
        }
    }

    public List<Role> getRoleByName(String name) {
        List<Role> roles = roleService.getAllRoles();
        List<Role> roleList = new ArrayList<>();
        if (name.isEmpty()) {
            roleList.addAll(roles);

        } else {
            List<Role> returnedRoles = roleService.returnRoleOfName(name);
            if (returnedRoles.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "FAILED", "No role found for that name"));
            } else {
                roleList.addAll(returnedRoles);
            }
        }
        return roleList;
    }

    public String updateRole(String name, List<String> permissions, int role_id) {
        try {
            String message = roleService.updateRoleOfId(name, permissions, role_id);
            if (message.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCESS", "Role updated successfully"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", message));
            }
        } catch (Exception e) { // Catch any general exception
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "An error occurred while updating the role."));
        }
        return "";
    }

    public void deleteRoleOfId(int roleId) {
        roleService.deletePermissionOfId(roleId);
        roleService.deleteRoleOfId(roleId);
        Role role = roleService.getRoleOfId(roleId);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "DELETED", "Role deleted successfully"));

    }

    public List<Role> getRoles() {
        return roleService.getAllRoles();
    }

    public List<String> getPermissionsOfRole(int roleId) {
        List<String> permissionNames = new ArrayList<>();
        List<Permission> permissions = roleService.getPermissionsOfRole(roleId);
        for (Permission permission : permissions) {
            permissionNames.add(permission.getName());
        }
        return permissionNames;
    }

    public List<Permission> permissionsForRole(int role_id) {
        return roleService.returnPermissionsForRole(role_id);
    }

    public List<String> permsForRole(int role_id) {
        List<String> perms = new ArrayList<>();
        List<Permission> permissions = roleService.returnPermissionsForRole(role_id);
        for (Permission permission : permissions) {
            perms.add(permission.getName());
        }
        return perms;
    }

    public void deleteAllRoles() {
    }

    // PERMISSIONS CHECK FOR UI RENDERING


    private User getCurrentUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        return (User) externalContext.getSessionMap().get("currentUser");
    }

    public boolean canViewUsers() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("VIEW_USERS")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean canViewRequisitions() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("VIEW_REQUISITION")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean canViewRoles() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("VIEW_ROLES")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean canManageUsers() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("MANAGE_USERS")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean canViewCategories() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("VIEW_CATEGORIES")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean canViewExpiredBudgetLines() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("VIEW_EXPIRED_BUDGET_LINES")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean canViewRejectedBudgetLines() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("VIEW_REJECTED_REQUISITIONS")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean canProvideAccountability() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("PROVIDE_ACCOUNTABILITY")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean canApproveRequisitions() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("APPROVE_REQUISITION")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean CanReviewRequisitions() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("REVIEW_REQUISITION")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean CanApproveBudgetLine() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("APPROVE_BUDGET_LINE")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean CanViewBudgetLines() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("VIEW_BUDGET_LINE")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean CanManageCategory() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("MANAGE_CATEGORIES")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean CanMakeRequisition() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("MAKE_REQUISITION")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }


    public boolean CanViewPendingBudgetLines() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("VIEW_PENDING_BUDGET_LINES")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean CanViewDraftedBudgetLines() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("VIEW_DRAFTED_BUDGET_LINES")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean CanViewAdminLogo() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("VIEW_ADMIN_LOGO")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean CanViewApprovedBudgetLines() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("VIEW_APPROVED_BUDGET_LINES")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean CanViewAccountability() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("VIEW_ACCOUNTABILITY")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean CanViewApprovedRequisitions() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("VIEW_APPROVED_REQUISITIONS")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean CanViewFulfilledRequisitions() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("VIEW_FULFILLED_REQUISITIONS")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean CanManageBudgetLines() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("MANAGE_BUDGET_LINES")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean CanManageRequisition() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("MANAGE_REQUISITION")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean CanViewPendingRequisitions() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("VIEW_PENDING_REQUISITIONS")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean CanViewStagedRequisitions() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("VIEW_STAGED_REQUISITIONS")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean CanViewDraftedRequisitions() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("VIEW_DRAFTED_REQUISITIONS")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean CanViewAdminDashboard() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("VIEW_ADMIN_DASHBOARD")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean CanViewReview() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("VIEW_REVIEW")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    public boolean CanManageRoles() {
        boolean allowed = false;
        Role role = roleService.returnRoleOfUniqueName(getCurrentUser().getRole());
        List<Permission> permissions = roleService.getPermissionsOfRole(role.getId());
        for (Permission permission : permissions) {
            if (permission.getName().equals("MANAGE_ROLES")) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

}
