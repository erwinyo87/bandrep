/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpn.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author ErwinYo
 */
@Entity
@Table(name = "t_user")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "userId", nullable = false, unique = true)
    private Integer userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(length = 50)
    private String name;

    @Type(type = "text")
    private String password;

    @Column(length = 100)
    private String email;

    @Column(length = 50)
    private String phoneNumber;

    @Column(length = 10)
    private String profileId;

//    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
//    @Column(name = "last_login")
//    private DateTime lastLogin;
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private LocalDateTime lastLogin;

//    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
//    @Column(name = "lastChangePassword")
//    private DateTime lastChangePassword;
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private LocalDateTime lastChangePassword;

    @Column(length = 1)
    private String status;

    @Column(length = 1)
    private Integer loginTry;

    public User() {
        this.lastChangePassword = LocalDateTime.now(ZoneOffset.UTC);
        this.lastLogin = LocalDateTime.now(ZoneOffset.UTC);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLoginTry() {
        return loginTry;
    }

    public void setLoginTry(Integer loginTry) {
        this.loginTry = loginTry;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public void setPassword(String password) throws NoSuchAlgorithmException {
//        this.password = getMD5(password);
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }
//    public void setPassword(String password) throws NoSuchAlgorithmException {
////        this.password = getMD5(password);
//        this.password = password;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public LocalDateTime getLastLogin() {
        return this.lastLogin;
//        try {
//            DateTimeFormatter fmt = DateTimeFormat.forPattern("d MMMM yyyy HH:mm");
//            String str = lastLogin.toString(fmt);
//            return str;
//        } catch (Exception e) {
//            return null;
//        }
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public LocalDateTime getLastChangePassword() {
        return lastChangePassword;
    }

    public void setLastChangePassword(LocalDateTime lastChangePassword) {
        this.lastChangePassword = lastChangePassword;
    }

    @Override
    public String toString() {
        return name;
    }

}
