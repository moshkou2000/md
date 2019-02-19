package com.moshkou.md.models;


public class BaseDataModel {

    private String _id;
    private String title;
    private String description;
    private String dateTime;
    private String location;
    private String image;

    private ContactModel contact;


    public BaseDataModel(String _id, String title, String description, String location, String dateTime, String image, ContactModel contact) {

        this._id = _id;
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.location = location;
        this.image = image;
        this.contact = contact;
    }

    public String get_id() { return _id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public String getDateTime() { return dateTime; }
    public String getImage() { return image; }
    public ContactModel getContact() { return contact; }

    public void set_id(String _id) { this._id = _id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setLocation(String location) { this.location = location; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }
    public void setImage(String image) { this.image = image; }
    public void setContact(ContactModel contact) { this.contact = contact; }

}
