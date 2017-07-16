package com.bmwu.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by michael on 7/16/17.
 */
public class Profile {

    @Autowired
    @Qualifier("student1")
    private Student student;

    public Profile() {
        System.out.println("Profile");
    }

    public void printAge() {
        System.out.println("age:" + this.student.getAge());
    }

    public void printName() {
        System.out.println("name: " + this.student.getName());
    }
}
