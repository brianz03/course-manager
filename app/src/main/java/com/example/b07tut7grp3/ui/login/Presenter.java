package com.example.b07tut7grp3.ui.login;

import android.view.Display;

import com.example.b07tut7grp3.MainActivity;

public class Presenter {
    private Model model;
    private MainActivity view;

    public Presenter(Model model, MainActivity view){
        this.model = model;
        this.view = view;
    }

    public void checkUsername(){
        String username = view.getUsername();
        if(username.equals("")){
            view.displayMessage("user email cannot be empty");
        }else if(model.isFound(username)){
            view.displayMessage("user email found");
        }else{
            view.displayMessage("user email not found");
        }
    }

    public void checkPassword(){
        String password = view.getPassword();
        if(password.length()<9){
            view.displayMessage("short password, bad password");
        }else{
            view.displayMessage("long password, safe password");
        }
    }

}
