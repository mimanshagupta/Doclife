package com.hci.doclife;

import java.util.Date;

/**
 * Basic structure for OT Schedule table items
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
