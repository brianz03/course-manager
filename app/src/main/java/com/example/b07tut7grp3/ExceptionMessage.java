package com.example.b07tut7grp3;
import java.lang.Exception;
public class ExceptionMessage extends Exception{
    private String message;

    /*
    This is an error message
    Will create a popup window with some error message
     */
    public ExceptionMessage(String message){
        this.message = "Error: " + message;
        System.out.println(message);
        //TODO: Create error popup window
    }
}
