package org.pahappa.pettycashapp.systems.petty_cash_app.services;

import org.pahappa.pettycashapp.systems.petty_cash_app.dao.UserDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Permission;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Role;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleService roleService;
    @Autowired
    private UserDao userDao;

    private boolean hasSpecialCharacters(String s){
        boolean hasCharacter = false;
        for (int i = 0; i < s.length(); i++) {
            // Checking the character for not being a letter,digit or space
            if (!Character.isDigit(s.charAt(i))
                    && !Character.isLetter(s.charAt(i))
                    && !Character.isWhitespace(s.charAt(i))) {
                hasCharacter =true;

            }
        }
        return hasCharacter;
    }
    //Generic method to check if name provided has digits in it
    private boolean hasDigits(String str){
        boolean hasDigits = false;
        for(int i =0 ; i < str.length(); i++){
            if(Character.isDigit(str.charAt(i))){
                hasDigits =  true;
            }
        }
        return hasDigits;
    }

    public String saveRole(String name, List<String> permissions) {

        String error_message = "";
        if(roleService.hasSpecialCharacters(name)){
            error_message ="Role name can not contain special characters";
        } else if (hasDigits(name)) {
            error_message = "Role name can not contain digits";
        }else {
            try {


                Role role = new Role();
                role.setName(name);

                for (String s : permissions ) {
                    Permission permission = new Permission();
                    role.getPermissions().add(permission);
                    permission.setRole(role);
                    permission.setName(s);

                }

                userDao.saveRole(role);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return  error_message;
    }

    public List<Role> getAllRoles() {
        return userDao.getAllRoles();
    }

    public List<Role> returnRoleOfName(String name) {
        List<Role> allRoles = userDao.getAllRoles();
        List<Role> returnedRoles = new ArrayList<>();
        for(Role role : allRoles){
            if(role.getName().toLowerCase().contains(name.toLowerCase())){
                returnedRoles.add(role);
            }
        }
        return returnedRoles;
    }
    public Role returnRoleOfUniqueName(String name) {
        return userDao.getRoleOfname(name);
    }

    public String updateRoleOfId(String name, List<String> permissions) {
        String error_message = "";
        if(roleService.hasSpecialCharacters(name)){
            error_message ="Role name can not contain special characters";
        } else if (hasDigits(name)) {
            error_message = "Role name can not contain digits";
        }else {
            Role role1 = userDao.getRoleOfname(name);
            userDao.deletePermissionsOfroleId(role1.getId());

            Role role = new Role();
            role.setName(name);

            for (String s : permissions ) {
                Permission permission = new Permission();
                role.getPermissions().add(permission);
                permission.setRole(role);
                permission.setName(s);
                userDao.savePermission(permission);
            }

            userDao.updateRole(role.getId(),role.getName());
        }
        return  error_message;
    }

    public void deleteRoleOfId(int roleId) {
        userDao.deleteRoleOdId(roleId);
    }

    public List<Permission> getPermissionsOfRole(int roleId) {
        return userDao.getPermissionsOfRole(roleId);
    }

    public List<Permission> returnPermissionsForRole(int roleId) {
        return  userDao.getPermissionsOfRole(roleId);
    }
}
