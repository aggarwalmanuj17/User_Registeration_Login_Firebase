package com.manuj.user_registeration_login_firebase;

public class User {

    public String Email;
    public String Password;
    public String Name;
    public String Address;
    public String Phone;


    public User() {
    }

    public User(String email, String password, String name, String address, String phone) {
        Email = email;
        Password = password;
        Name = name;
        Address = address;
        Phone = phone;
    }


    @Override
    public String toString() {
        return "User{" +
                "Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                ", Name='" + Name + '\'' +
                ", Address='" + Address + '\'' +
                ", Phone='" + Phone + '\'' +
                '}';
    }
}
