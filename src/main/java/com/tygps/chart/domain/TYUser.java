package com.tygps.chart.domain;

public class TYUser {

    private String userID;

    public TYUser(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
