package model;

import java.util.regex.Pattern;

public class Customer {
    public String firstName;
    public String lastName;
    public String email;

    //Adding RegEx and a Matcher to compile at runtime

    private final String emailRegex = "^(.+)@(.+).com$";
    private final Pattern pattern = Pattern.compile(emailRegex);

    public Customer() {}

    //Constructor
    public Customer(String firstName, String lastName, String email){

        //If email format do not match, throw this exception

        if(!pattern.matcher(email).matches()){
            throw new IllegalArgumentException("Error, invalid email. Try this format => name@domain.com");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    //Methods

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getEmail(){
        return email;
    }
    public String getFullName() { return firstName + " " + lastName;}

    @Override
    public String toString(){
        return "First Name: '" + firstName + '\'' +
                " Last Name: '" + lastName + '\'' +
                " Email: " + email;
    }
}
