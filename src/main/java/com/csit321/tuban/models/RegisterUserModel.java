package com.csit321.tuban.models;

import jakarta.validation.constraints.*;

public class RegisterUserModel {

    @NotEmpty(message = "Email is required.")
    private String email;

    @NotEmpty(message = "first name is required.")
    private String firstName;

    @NotEmpty(message = "last name is required.")
    private String lastName;

    @NotEmpty(message = "Password is required.")
    private String password;

    @NotEmpty(message = "Phone number is required.")
    private String PhoneNumber;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
