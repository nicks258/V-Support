package com.vsupport.npluslabs.vsupport.HelperClass;

public class Item {
    int id;
    String name;
    String description;
    int price;
    String thumbnail;

    int participatedUsers;

    public Item() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParticipatedUsers() {
        return participatedUsers;
    }

    public void setParticipatedUsers(int participatedUsers) {
        this.participatedUsers = participatedUsers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
