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
}
