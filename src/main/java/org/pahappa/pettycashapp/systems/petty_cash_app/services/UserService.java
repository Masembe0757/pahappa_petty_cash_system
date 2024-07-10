package org.pahappa.pettycashapp.systems.petty_cash_app.services;
import org.pahappa.pettycashapp.systems.petty_cash_app.dao.UserDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired

    UserDao userDao;

    //initialising a singleton
    private static UserService userService = new UserService();
    private UserService(){};
    public static UserService getUserService() {
        return userService;
    }

    private  boolean isValidEmail(String email) {
        final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
        final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false; // Password must be at least 8 characters long
        }
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUpper = true;
            } else if (Character.isLowerCase(ch)) {
                hasLower = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(ch)) {
                hasSpecial = true;
            }
        }
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

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

    //Generic method to check if username has only digits
    private boolean onlyDigits(String str, int n)
    {
        for (int i = 0; i < n; i++) {
            if (str.charAt(i) < '0'
                    || str.charAt(i) > '9') {
                return false;
            }
        }
        return true;
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
    public String saveUser(String firstName, String lastName, String userName, String password1, String password2, String email) {
        String error_message= "";

        if(firstName.isEmpty() || UserService.getUserService().hasDigits(firstName) || UserService.getUserService().hasSpecialCharacters(firstName) ){
            error_message = "First name field  has digits or special characters in it";
        }
        else if(UserService.getUserService().hasDigits(lastName) || UserService.getUserService().hasSpecialCharacters(lastName)){
            error_message = "Last name field has digits or special characters  in it";
        } else if ( userName.length() < 6 || UserService.getUserService().hasSpecialCharacters(userName) ) {
            error_message = "User name field has special characters less than 6 or has special characters";

        } else if (Character.isDigit(userName.charAt(0))) {
            error_message = "User name field  can not start with a digit";

        } else if (UserService.getUserService().onlyDigits(userName, userName.length())) {
            error_message = "User name field can not contain only digits";
        } else  if(!UserService.getUserService().isValidEmail(email)){
            error_message = "Email provided is of incorrect format";
        }else if (!password1.equals(password2)) {
            error_message = "Passwords do not match";
        } else {
            User returnedUser = userDao.returnUser(userName);
            if (returnedUser!= null) {
                error_message ="User name already taken please enter new user name";
            } else {
                if(!UserService.getUserService().isValidPassword(password1)){
                    error_message = "Password must have more than 8 lowercase, uppercase, digits and special characters ";
                }else {
                        //Sending email with credentials
                       // UserService.getUserService().EmailSender(email, userName, password1,firstName,lastName);
                        // Encrypting the password with BCrypt
                        String encodedPassword = Base64.getEncoder().encodeToString(password1.getBytes());

                        User user = new User();
                        user.setFirstName(firstName);
                        user.setLastName(lastName);
                        user.setUserName(userName);
                        user.setPassword(encodedPassword);
                        user.setEmail(email);
                        userDao.saveUser(user);
                }

            }
        }
        return  error_message;

    }

    public static void main(String[] args) {
        UserService.getUserService().saveUser("sendi","joseph","cipher45","ttttBBBB45,","ttttBBBB45,","sendi@gmail.com");
    }

}
