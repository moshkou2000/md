package com.moshkou.md.models;


import java.util.List;

public class BaseDataModel {

    private String _id;
    private String title;
    private String description;
    private String dateTime;
    private String location;
    private String image;

    private ContactModel child;
    private List<ContactModel> children;


    public BaseDataModel(String _id, String title, String description, String location, String dateTime, String image, ContactModel child, List<ContactModel> children) {

        this._id = _id;
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.location = location;
        this.image = image;
        this.child = child;
        this.children = children;
    }

    public String get_id() { return _id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public String getDateTime() { return dateTime; }
    public String getImage() { return image; }
    public ContactModel getChild() { return child; }
    public List<ContactModel> getChildren() { return children; }

    public void set_id(String _id) { this._id = _id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setLocation(String location) { this.location = location; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }
    public void setImage(String image) { this.image = image; }
    public void setChild(ContactModel contact) { this.child = contact; }
    public void setChildren(List<ContactModel> contacts) { this.children = contacts; }

}
