package com.hci.doclife;

/** This class creates a custom item persons that has six member variables
 * name,age,photoId,details,report
 * Created by Manasi on 21-11-2015.
 */
public class Persons {
   String name;
    String age;
    int photoId;
    String details;
    String Report;
    int id;


    //Constructor for Doctor menu which displays the cards showing the patient name,age and disease
    Persons(String name, String age, int photoId,String details) {
        this.name = name;
        this.age = age;
        this.photoId = photoId;
        this.details=details;

    }

    //Constructor for the lab report centre that fetched other detaisl such as PatientID and report
    Persons(int id,String name,String Report ){
        this.id= id;
        this.name=name;
        this.Report=Report;
    }


}
