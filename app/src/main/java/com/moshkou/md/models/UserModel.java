package com.moshkou.md.models;

public class UserModel {
    private String token = "";
    private String sg_token = "";
    private String _id = "";
    private String name = "";
    private String phone = "";
    private String email = "";

    public UserModel() {}
    public UserModel(String token,
                     String sg_token,
                     String _id,
                     String name,
                     String phone,
                     String email) {
        this.token = token;
        this._id = _id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public void setToken(String token) { this.token = token; }
    public void setSgToken(String sg_token) { this.sg_token = sg_token; }
    public void setId(String ic_number) { this._id = ic_number;}
    public void setName(String first_name) { this.name = first_name; }
    public void setPhone(String telephone) { this.phone = telephone; }
    public void setEmail(String email) { this.email = email; }

    public String getToken() { return token; }
    public String getSgToken() { return sg_token; }
    public String getId() { return _id;}
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }

}
