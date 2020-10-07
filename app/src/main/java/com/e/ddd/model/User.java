package com.e.ddd.model;

import java.util.Objects;

public class User {
    private String userName;
    private String userEmail;

    public User(){

    }
    public User(String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userEmail, user.getUserEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUserEmail());
    }



}
