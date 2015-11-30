package com.hci.doclife;

/**
 * Created by Manasi on 30-11-2015.
 */
public class BedStatus {
    String ward;
    int total;
    int occupied;



    public BedStatus(String ward, int total, int occupied)
    {
        this.ward=ward;
        this.total=total;
        this.occupied=occupied;

    }
}
