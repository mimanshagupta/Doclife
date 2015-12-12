package com.hci.doclife;

/**
 * Basic structure of Duty Schedule table columns
 */
public class DutyScheduleList {
    String doctor;
    String date;

    DutyScheduleList(String department, String date){
        this.doctor = department;
        this.date = date;
    }


    public String getDepartment() {
        return doctor;
    }

    public String getDate() {
        return date;
    }
}
