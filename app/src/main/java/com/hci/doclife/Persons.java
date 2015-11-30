package com.hci.doclife;

/**
 * Created by Manasi on 21-11-2015.
 */
public class Persons {
   String name;
    String age;
    int photoId;
    String details;
    String Report;
    int id;


    Persons(String name, String age, int photoId,String details) {
        this.name = name;
        this.age = age;
        this.photoId = photoId;
        this.details=details;

    }

    Persons(int id,String name,String Report ){
        this.id= id;
        this.name=name;
        this.Report=Report;
    }


}
