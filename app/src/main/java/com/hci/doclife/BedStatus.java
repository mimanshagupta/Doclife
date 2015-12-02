package com.hci.doclife;

/** This class creates a custom item bed status that has three member variables
 * ward,total and occupied.
 * Created by Manasi on 30-11-2015.
 */
public class BedStatus {
    String ward;
    int total;
    int occupied;


   //Constructor to instantiate values of the class
    public BedStatus(String ward, int total, int occupied)
    {
        this.ward=ward;
        this.total=total;
        this.occupied=occupied;

    }
}
