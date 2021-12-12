package com.example.project.User;

import java.util.Objects;

public class userDTO {
    private String userID;
    private String userName;
    private String userPhoneNo;
    private String userEmail;

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhoneNo() {
        return userPhoneNo;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPhoneNo(String userPhoneNo) {
        this.userPhoneNo = userPhoneNo;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        userDTO userDTO = (userDTO) o;
        return Objects.equals(userID, userDTO.userID) && Objects.equals(userName, userDTO.userName) && Objects.equals(userPhoneNo, userDTO.userPhoneNo) && Objects.equals(userEmail, userDTO.userEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, userName, userPhoneNo, userEmail);
    }
}
