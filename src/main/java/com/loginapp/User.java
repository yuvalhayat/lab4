package main.java.com.loginapp;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class User implements Comparable<User> {
    private static final String DEFAULT_TIME = "deftm";

    private String email;
    private String password;
    private int attempts;
    private String blockHour;
    boolean blocked;

    public User(String email, String password) throws Exception  {

        this.email = email;
        this.password = password;
        this.attempts = 0;
        this.blockHour = DEFAULT_TIME;
        this.blocked = false;

        validateEmail(email);
        validatePassword(password);
    }

    public int getFailedAttempts() {
        return this.attempts;
    }

    public void setBlockHourToCurrentTime() {
        if (this.blockHour.equals(DEFAULT_TIME)) {
        // Get the current time
        LocalTime now = LocalTime.now();

        // Define the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Format the time to a string
        this.blockHour = now.format(formatter);
        }
    }

    public void incrementAttempts() {
        this.attempts++;
    }

    public void blockUser() {
        this.blocked = true;
    }

    public void unblockUser() {
        this.blocked = false;
        this.attempts = 0;
    }

    public boolean getBlocked() {
        return this.blocked;
    }

    //check if the email is valid,if it isn't then it throws an appropriate exception,otherwise does nothing
    private static void validateEmail(String email) throws Exception{
        String LONG_EMAIL_MESSAGE = "Username is too long, try something shorter";
        String INVALID_EMAIL_MESSAGE = "Please enter a valid Email as username";

        String[]emailParts = email.split("@");

        //throw exception if the email is too long
        if(email.length() > 50){
            throw new Exception(LONG_EMAIL_MESSAGE);
        }

        //throw exception if the email doesn't have @ at all
        if(emailParts.length != 2) {
            throw new Exception(INVALID_EMAIL_MESSAGE);
        }

        //regular expression for strings consisting only of letters,numbers, '-','+','%' and'_'
        String regexPattern = "^[a-zA-Z0-9-+%_]+$";

        //throw exception if the first part include invalid characters
        if(!Pattern.matches(regexPattern, emailParts[0])){
            throw new Exception(INVALID_EMAIL_MESSAGE);
        }

        //regular expression for strings consisting only of letters,numbers, '-' and '.'
        regexPattern = "^[a-zA-Z0-9-.]+$";


        //throw exception if either the second or third part include invalid characters
        if(!Pattern.matches(regexPattern, emailParts[1])){
            throw new Exception(INVALID_EMAIL_MESSAGE);
        }
        String[]emailDomainParts = emailParts[1].split("\\.");

        //throw exception if the email doesn't have a third part at all
        if(emailDomainParts.length <= 1) {
            throw new Exception(INVALID_EMAIL_MESSAGE);
        }

        //regular expression for strings consisting only of letters and has at least 2 of them
        regexPattern = "^[a-zA-Z]{2,}$";

        //throw exception if the third part of the email either have non-letters characters
        // or if it has less than 2 characters
        if(!Pattern.matches(regexPattern, emailDomainParts[emailDomainParts.length-1])){
            throw new Exception(INVALID_EMAIL_MESSAGE);
        }

    }

    //check if the password is valid,if it isn't then it throws an appropriate exception,otherwise does nothing
    private static void validatePassword(String password) throws Exception {

        String SHORT_PASSWORD_MESSAGE = "Your password is too short, add more characters";
        String LONG_PASSWORD_MESSAGE = "Your password is too long, try a shorter one";
        String INVALID_PASSWORD_MESSAGE = "Please enter a valid password";


        //throw exception if the password is too short
        if(password.length() < 8){
            throw new Exception(SHORT_PASSWORD_MESSAGE);
        }

        //throw exception if the password is too long
        if( password.length() > 12){
            throw new Exception(LONG_PASSWORD_MESSAGE);
        }

        //regular expression for strings consisting only of letters,numbers and the special characters:!@#$%^&*)(_+
        //and the string includes at least one instance of each type of characters ( letters,numbers and special characters)
        String regexPattern = "^(?=.*[a-zA-Z])" +   //include at least one letter
                "(?=.*[0-9])" +                     //include at least one digit
                "(?=.*[!@#$%^&*)(_+])" +            //include at least one special character
                "[a-zA-Z0-9!@#$%^&*)(_+]+$";        //consist only of letters,numbers and special characters

        //throw exception if the password is invalid
        if(!Pattern.matches(regexPattern, password)){
            throw new Exception(INVALID_PASSWORD_MESSAGE);
        }
    }

    @Override
    public String toString() {
        return this.email;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null) return false;
        if(obj == this) return true;
        if(obj.getClass() != this.getClass()) return false;
        final User other = (User) obj;
        return  this.email.equals(other.email) && this.password.equals(other.password);
    }


    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public int compareTo(User other) {
        if (this.email.compareTo(other.getEmail()) != 0)
            return this.email.compareTo(other.getEmail());
        return this.password.compareTo(other.getPassword());
    }
}

