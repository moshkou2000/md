package com.moshkou.md.models;

public class UserModel {

    private int email_verified = 0;
    private String ic_number = "";
    private String first_name = "";
    private String last_name = "";
    private String gender = "male";
    private String birthday = "";
    private String cellphone = "";
    private String telephone = "";
    private String email = "";
    private String address = "";
    private String city = "";
    private String region = "";
    private String postcode = "";
    private String country = "";
    private String picture = "";
    private String cpm_cd = "";
    private String gcm_token = "";
    private String token = "";
    private String unique_id = "";
    private int user_id = 0;

    public UserModel() {}
    public UserModel(int email_verified,
                     String ic_number,
                     String first_name,
                     String last_name,
                     String gender,
                     String birthday,
                     String cellphone,
                     String telephone,
                     String email,
                     String address,
                     String city,
                     String region,
                     String postcode,
                     String country,
                     String picture,
                     String cpm_cd,
                     String gcm_token,
                     String token,
                     String unique_id,
                     int user_id) {
        this.email_verified = email_verified;
        this.ic_number = ic_number;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.birthday = birthday;
        this.cellphone = cellphone;
        this.telephone = telephone;
        this.email = email;
        this.address = address;
        this.city = city;
        this.region = region;
        this.postcode = postcode;
        this.country = country;
        this.picture = picture;
        this.cpm_cd = cpm_cd;
        this.gcm_token = gcm_token;
        this.token = token;
        this.unique_id = unique_id;
        this.user_id = user_id;
    }

    public void setEmail_verified(int email_verified) { this.email_verified = email_verified;}
    public void setIc_number(String ic_number) { this.ic_number = ic_number;}
    public void setFirst_name(String first_name) { this.first_name = first_name; }
    public void setLast_name(String last_name) { this.last_name = last_name; }
    public void setGender(String gender) { this.gender = gender; }
    public void setBirthday(String birthday) { this.birthday = birthday; }
    public void setCellphone(String cellphone) { this.cellphone = cellphone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
    public void setCity(String city) { this.city = city; }
    public void setRegion(String region) { this.region = region; }
    public void setPostcode(String postcode) { this.postcode = postcode; }
    public void setCountry(String country) { this.country = country; }
    public void setPicture(String picture) { this.picture = picture; }
    public void setCpm_cd(String cpm_cd) { this.cpm_cd = cpm_cd; }
    public void setGcm_token(String gcm_token) { this.gcm_token = gcm_token; }
    public void setToken(String token) { this.token = token; }
    public void setUnique_id(String unique_id) { this.unique_id = unique_id; }
    public void setUser_id(int user_id) { this.user_id = user_id; }

    public int getEmail_verified() { return email_verified;}
    public String getIc_number() { return ic_number;}
    public String getFirst_name() { return first_name; }
    public String getLast_name() { return last_name; }
    public String getGender() { return gender; }
    public String getBirthday() { return birthday; }
    public String getCellphone() { return cellphone; }
    public String getTelephone() { return telephone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public String getRegion() { return region; }
    public String getPostcode() { return postcode; }
    public String getCountry() { return country; }
    public String getPicture() { return picture; }
    public String getCpm_cd() { return cpm_cd; }
    public String getGcm_token() { return gcm_token; }
    public String getToken() { return token; }
    public String getUnique_id() { return this.unique_id; }
    public int getUser_id() { return this.user_id; }

}
