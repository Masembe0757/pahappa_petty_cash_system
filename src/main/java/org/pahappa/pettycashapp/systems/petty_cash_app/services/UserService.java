package org.pahappa.pettycashapp.systems.petty_cash_app.services;
import org.pahappa.pettycashapp.systems.petty_cash_app.dao.UserDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Base64;
import java.util.Calendar;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;

@Service
public class UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    UserService userService;



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
    public  String generateReferenceNumber() {
        UUID uuid = UUID.randomUUID();
        String referenceNumber = uuid.toString().replace("-", "ref_petty").toUpperCase();
        return referenceNumber;
    }
    public  String saveUser(String firstName, String lastName, String userName, String password1, String password2, String email) {
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
        } else  if(!userService.isValidEmail(email)){
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
                        userDao.saveUser(user);
                }

            }
        }
        return  error_message;

    }
    public String makeRequisition(int amount,Date dateNeeded,String description,int budgetLineId,int userId){
        String error_message ="";
        BudgetLine budgetLine= userDao.returnBudgetLineofId(budgetLineId);
        if(budgetLine.getAmountDelegated()<amount){
            error_message="Amount specified is more than what is on budget line";
        } else if (dateNeeded.getYear() + 1900 > Calendar.getInstance().get(Calendar.YEAR)) {
            error_message="Date needed is a past date";
        } else if (description.length()<50) {
            error_message = "Please provide more description";
        } else {
            Requisition requisition = new Requisition();
            requisition.setAmount(amount);
            requisition.setReferenceNumber(userService.generateReferenceNumber());
            requisition.setDateNeeded(dateNeeded);
            requisition.setDescription(description);
            requisition.setBudgetLine(budgetLine);
            userDao.saveRequisition(requisition);
        }
        return error_message;
    }
    public String provideAccountability(String description,int amount,int requisitionId){
        String error_message ="";
        Requisition requisition = userDao.getRequisitionOfId(requisitionId);
        if(amount > requisition.getAmount()){
            error_message = "Amount accounted greater than amount requisitioned";
        } else if (description.length()<50) {
            error_message = "Please provide more description";
        } else {
            Accountability accountability = new Accountability();
            accountability.setAmount(amount);
            accountability.setDescription(description);
            accountability.setRequisition(requisition);
            accountability.setReferenceNumber(userService.generateReferenceNumber());
            userDao.saveAccountability(accountability);
        }

        return error_message;
    }
    public String makeReview(String description,int requistionId,String userName){
        String error_message = "";
        if(description.length()<50){
            error_message = "Please provide more description";
        }else {
            User user = userDao.returnUser(userName);
            Requisition requisition = userDao.getRequisitionOfId(requistionId);
            Review review = new Review();
            review.setUser(user);
            review.setDescription(description);
            review.setRequisition(requisition);
            userDao.makeReview(review);
        }

        return error_message;
    }

    public String makeBudgetLine(int amount,String name, Date startDate,Date endDate,int categoryId){
        String error_message = "";
        if(userService.hasDigits(name)){
            error_message = " Name can not contain didgits";
        }else {
            BudgetLine budgetLine= new BudgetLine();
            Category category = userDao.getCategoryOfId(categoryId);
            budgetLine.setAmountDelegated(amount);
            budgetLine.setCategory(category);
            budgetLine.setEndDate(endDate);
            budgetLine.setStartDate(startDate);
            budgetLine.setName(name);
            userDao.saveBudgetLine(budgetLine);
        }
        return error_message;
    }

}
