package org.pahappa.pettycashapp.systems.petty_cash_app.services;

import org.pahappa.pettycashapp.systems.petty_cash_app.dao.UserDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public String saveRole(String name, int permission) {
        String error_message = "";
        if(roleService.hasSpecialCharacters(name)){
            error_message ="Role name can not contain special characters";
        } else if (hasDigits(name)) {
            error_message = "Role name can not contain digits";
        }else {
            Role role = new Role();
            role.setName(name);
            role.setPermission(permission);
            userDao.saveRole(role);
        }
        return  error_message;
    }
}
