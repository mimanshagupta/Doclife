package com.hci.doclife;

/**This class creates a custom item medicines that has two member variables
 * name,quantity. This is used to store the medicine item.
 * Created by Manasi on 02-12-2015.
 */
public class Medicines {
    String Quantity;
    String Name;

    //Constructor to instantiate values of the class
    public Medicines(String Name,String Quantity){
        this.Name=Name;
        this.Quantity=Quantity;
    }
}
