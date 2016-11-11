package com.example.budget.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by eaglebrosi on 11/7/16.
 */

public class User {
    //Username is really email- i know- it's silly!
    @SerializedName("username")
    private String username;

    @SerializedName("UserName")
    private String notAnEmail;

    @SerializedName("access_token")
    private String access_token;

    @SerializedName(".expires")
    private Date expires;

    @SerializedName("email")
    private String email;

    @SerializedName("FullName")
    private String name;

    @SerializedName("password")
    private String password;

    @SerializedName("UserId")
    private String userId;

    @SerializedName("confirm")
    private String confirm;

    @SerializedName("apiKey")
    private String apiKey;

    @SerializedName("AvatarBase64")
    private String avatar64;

    @SerializedName("grantType")
    private String grantType;

    @SerializedName("Longitude")
    private Double longitude;

    @SerializedName("Latitude")
    private Double latitude;

    @SerializedName("Created")
    private Date created;

    private int radius;

    //for changing user info.
    public User (String name, String avatar64){
        this.name = name;
        this.avatar64 = avatar64;
    }

    // for Loggin in at Auth but I couldn't use it because email has to be identified as USERNAME
    public User(String username, String password, String grantType) {
        this.username = username;
        this.password = password;
        this.grantType = grantType;
    }

    // for registering the account
    public User(String email, String name, String avatar64, String apiKey, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.avatar64 = avatar64;
        this.apiKey = apiKey;
    }

    public User(String userId, String username, String avatar64, Double Longitude, Double Latitude, Date created){
        this.userId = userId;
        this.username = username;
        this.avatar64 = avatar64;
        this.longitude = longitude;
        this.latitude = latitude;
        this.created = created;
    }

    public User (String userId, int radius){
        this.userId = userId;
        this.radius = radius;
    }

    public String getNotAnEmail() {
        return notAnEmail;
    }

    public void setNotAnEmail(String notAnEmail) {
        this.notAnEmail = notAnEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getLongitude() {
            return longitude;
        }

    public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

    public Double getLatitude() {
            return latitude;
        }

    public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAvatar64() {
        return avatar64;
    }

    public void setAvatar64(String avatar64) {
        this.avatar64 = avatar64;
    }
}
