package org.pahappa.pettycashapp.systems.petty_cash_app.services;
import org.pahappa.pettycashapp.systems.petty_cash_app.dao.UserDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    UserService userService;
    private User getCurrentUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        return (User) externalContext.getSessionMap().get("currentUser");
    }

    private  boolean isValidEmail(String email) {
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
         final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
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
    public  String generateReferenceNumber() {
        UUID uuid = UUID.randomUUID();
        String referenceNumber = uuid.toString().replace("-", "ref_petty").toUpperCase();
        return referenceNumber;
    }
    public  String saveUser(String firstName, String lastName, String userName, String password1, String password2, String email,String role) {
        String error_message= "";

        if(firstName.isEmpty() || userService.hasDigits(firstName) || userService.hasSpecialCharacters(firstName) ){
            error_message = "First name field  has digits or special characters in it";
        }
        else if(userService.hasDigits(lastName) || userService.hasSpecialCharacters(lastName)){
            error_message = "Last name field has digits or special characters  in it";
        } else if ( userName.length() < 6 || userService.hasSpecialCharacters(userName) ) {
            error_message = "User name field has special characters less than 6 or has special characters";

        } else if (Character.isDigit(userName.charAt(0))) {
            error_message = "User name field  can not start with a digit";

        } else if (userService.onlyDigits(userName, userName.length())) {
            error_message = "User name field can not contain only digits";
        } else  if(!isValidEmail(email)){
            error_message = "Email provided is of incorrect format";
        }else if (!password1.equals(password2)) {
            error_message = "Passwords do not match";
        } else {
            User returnedUser = userDao.returnUser(userName);
            if (returnedUser!= null) {
                error_message ="User name already taken please enter new user name";
            } else {
                if(!userService.isValidPassword(password1)){
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
                        user.setRole(role);
                        userDao.saveUser(user);
                }

            }
        }
        return  error_message;

    }

    public String makeReview(String description,int requistionId,String userName){
        String error_message = "";
        Requisition requisition = userDao.getRequisitionOfId(requistionId);

        if(description.length()<50){
            error_message = "Please provide more description";
        } else if (requisition.getStatus().equals("draft")) {
            error_message ="Can not review a drafted requisition";
        } else {
            User user = userDao.returnUser(userName);
             requisition = userDao.getRequisitionOfId(requistionId);
            Review review = new Review();
            review.setUser(user);
            review.setDescription(description);
            review.setRequisition(requisition);
            userDao.makeReview(review);
        }

        return error_message;
    }



    public String updateUserOfUserName(String firstName, String lastName, String userName,String password1,String password2, String email,String role) {
        String error_message = "";
            User returnedUser = userDao.returnUser(userName);
            if (returnedUser!=null) {
                //update validation

                if( userService.hasDigits(firstName) || userService.hasSpecialCharacters(firstName) ){
                    error_message ="First name field has digits or special characters  in it";

                }
                else if(userService.hasDigits(lastName) || userService.hasSpecialCharacters(lastName)){
                    error_message ="Last name field has digits or special characters in it";

                }else  if(!userService.isValidEmail(email)){
                    error_message = "Email provided is of incorrect format";
                } else if (!password1.equals(password2)) {
                    error_message = "Passwords do not match";
                } else {
                    if (!userService.isValidPassword(password1)) {
                        error_message = "Password must have more than 8 lowercase, uppercase, digits and special characters";
                    } else {

                            //Sending email with credentials
                          //  UserService.getUserService().EmailSender(email, userName, password1,firstName,lastName);
                            // Encrypting the password with BCrypt
                            String encodedPassword = Base64.getEncoder().encodeToString(password1.getBytes());
                            returnedUser.setFirstName(firstName);
                            returnedUser.setLastName(lastName);
                            returnedUser.setPassword(encodedPassword);
                            returnedUser.setEmail(email);
                            returnedUser.setRole(role);
                            userDao.updateUser(returnedUser);

                    }
                }
            }
            else {
                error_message = "User not registered in the database";
            }

        return  error_message;
    }

    public String updateBudgetLine(int budgetLineId, int amount,String name, Date startDate,Date endDate,int categoryId){
        String error_message = "";
        BudgetLine budgetLine = userDao.returnBudgetLineofId(budgetLineId);
        if(userService.hasDigits(name)){
            error_message = "Name can not contain digits";
        }else {
            Category category = userDao.getCategoryOfId(categoryId);
            budgetLine.setName(name);
            budgetLine.setAmountDelegated(amount);
            budgetLine.setStartDate(startDate);
            budgetLine.setEndDate(endDate);
            budgetLine.setCategory(category);
            userDao.updateBudgetLIne(budgetLine);
        }
        return error_message;
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void deleteUserOfUserName(String userName) {
        userDao.deleteUserOfUserName(userName);
    }

    public void deleteAllUsers() {
    }

    public List<User> returnUserOfName(String name) {
        List<User> allUsers = userDao.getAllUsers();
        List<User> returnedUsers = new ArrayList<>();
        for(User u : allUsers){
            if(u.getUserName().toLowerCase().contains(name.toLowerCase())){
                returnedUsers.add(u);
            } else if (u.getFirstName().toLowerCase().contains(name.toLowerCase())) {
                returnedUsers.add(u);
            } else if (u.getLastName().toLowerCase().contains(name.toLowerCase())) {
                returnedUsers.add(u);
            }
        }
        for(User u : returnedUsers){
            String decodedPassword = new String(Base64.getDecoder().decode(u.getPassword()));
            u.setPassword(decodedPassword);
        }
        return returnedUsers;
    }




}




