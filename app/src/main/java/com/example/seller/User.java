package com.example.seller;

public class User {

    private String email;
    private String fullName;
    private String adress;
    private String phone;
    private String password;
    private String userType;
    private String credit;
    private String cvv_num;
    private String expiryM;
    private String expiryY;
    private String postalcode;



    public User() {}


    public User(String fullName, String adress, String phone,
                String email,String password,String cardNumber,String cvv,String expiryMonth, String expiryYear, String postal) {
        this.email = email;
        this.fullName = fullName;
        this.adress = adress;
        this.phone = phone;
        this.credit = cardNumber;
        this.cvv_num = cvv;
        this.expiryM = expiryMonth;
        this.expiryY = expiryYear;
        this.postalcode = postal;
    }

    public String getCvv_num() {
        return cvv_num;
    }

    public void setCvv_num(String cvv_num) {
        this.cvv_num = cvv_num;
    }

    public String getExpiryM() {
        return expiryM;
    }

    public void setExpiryM(String expiryM) {
        this.expiryM = expiryM;
    }

    public String getExpiryY() {
        return expiryY;
    }

    public void setExpiryY(String expiryY) {
        this.expiryY = expiryY;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCredit() {
        return credit;
    }



    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
