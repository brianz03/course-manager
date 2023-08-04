package com.example.admintoolsUTSC;

import android.util.Patterns;

public class admin_presenter {


    private admin_model model;
    private Admin_Login view;
    private String userid;

    public admin_presenter(admin_model model, Admin_Login view) {
        this.model = model;
        this.view = view;
    }

    public void checkLogin() {
        String username = view.getEmail();
        String pw = view.getPw();
        if (username.isEmpty()) {
            view.displayError("Email is required");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            view.displayError("Please enter a valid email");
        } else if (pw.isEmpty()) {
            view.displayError("Password is required");
        } else if (pw.length() < 6){
            view.displayError("Passwored too short");
        }else {
            login(username, pw);
        }
    }

    public void login(String email, String password) {
        model.login(email, password, (String userID) -> {
            if (userID == null) view.displayError("Sorry, unable to login! Please try again");
            else view.goToStudentPage(userid);
        });
    }
}