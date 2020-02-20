package com.freetime.dva.ubisecureexercise.Services;

import com.freetime.dva.ubisecureexercise.models.User;

import org.junit.Test;

import static org.junit.Assert.*;

public class DatabaseHelperTest {

    @Test
    public void addUser() {
        User testUser = new User("dva", "12345", "Anh", "VD", "dunghoi@gmail.xy");
        DatabaseHelper db = new DatabaseHelper();


    }

    @Test
    public void getAllUser() {
    }

    @Test
    public void checkUser() {
    }

    @Test
    public void loginCheck() {
    }
}