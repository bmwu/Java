package com.bmwu.spring;

/**
 * Created by michael on 3/28/17.
 */
public class HelloWorld {

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public void getMessage() {
        System.out.println("Your message: " + this.message);
    }
}
