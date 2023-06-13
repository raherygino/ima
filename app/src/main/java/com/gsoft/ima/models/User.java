package com.gsoft.ima.models;

public class User {
    public String lastname;
    public String firstname;
    public String gender;
    public String birthday;
    public String birthplace;
    public String id_card;
    public String country;
    public String city;
    public String phone;
    public String email;
    public String password;
    public String token;

    public User(
            String firstname,
            String lastname,
            String gender,
            String birthday,
            String birthplace,
            String id_card,
            String country,
            String city,
            String phone,
            String email,
            String password) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.gender = gender;
        this.birthday = birthday;
        this.birthplace = birthplace;
        this.id_card = id_card;
        this.country = country;
        this.city = city;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public boolean isValidate() {
        boolean isValidate = true;
        if (this.lastname.isEmpty()) {
            isValidate = false;
        }
        if (this.lastname.isEmpty()) {
            isValidate = false;
        }
        if (this.gender.isEmpty()) {
            isValidate = false;
        }
        if (this.birthday.isEmpty()) {
            isValidate = false;
        }
        if (this.birthplace.isEmpty()) {
            isValidate = false;
        }
        if (this.id_card.isEmpty()) {
            isValidate = false;
        }
        if (this.country.isEmpty()) {
            isValidate = false;
        }
        if (this.city.isEmpty()) {
            isValidate = false;
        }

        if (this.phone.isEmpty()) {
            isValidate = false;
        }
        if (this.email.isEmpty()) {
            isValidate = false;
        }
        if (this.password.isEmpty()) {
            isValidate = false;
        }

        return isValidate;
    }
}
