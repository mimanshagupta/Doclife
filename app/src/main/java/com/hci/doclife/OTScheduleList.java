package com.hci.doclife;

import java.util.Date;

/**
 * Created by Mimansha on 24/11/2015.
 */
public class OTScheduleList {
    String otName;
    String department;
    String date;

    OTScheduleList(String otName, String department, String date){
        this.otName = otName;
        this.department = department;
        this.date = date;
    }

    public String getOtName() {
        return otName;
    }

    public String getDepartment() {
        return department;
    }

    public String getDate() {
        return date;
    }
}
