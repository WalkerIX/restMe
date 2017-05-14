package com.org.restMeaurant.data;


public class User {

    public static final String TYPE = "User";

    private String firstName;
    private String userId;

    public User() {
        firstName = "";
        userId = "";
    }

    public User(String userName, String firstName) {
        this.userId = userName;
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString(){
        return super.toString();
    }
}
