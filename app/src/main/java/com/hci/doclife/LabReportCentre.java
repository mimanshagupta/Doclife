package com.hci.doclife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LabReportCentre extends AppCompatActivity {
    private Toolbar toolbar;
    String TITLES[] = {"Patient Center", "Lab Report Center", "Search Dispensary", "OT Schedule", "Duty Schedule", "Emergency Center"};
    int ICONS[] = {R.drawable.ic_patient, R.drawable.ic_lab, R.drawable.ic_searchdispensary, R.drawable.ic_otschedule, R.drawable.ic_clipboard, R.drawable.ic_danger};

    String NAME = "Dr. Mimansha Gupta";
    String EMAIL = "doctor@hospital.com";
    int PROFILE = R.mipmap.ic_launcher;

    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;
    TableLayout table;
    List<Persons> patients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_report_centre);

        table=(TableLayout)findViewById(R.id.labreportcentre); //Getting the table from the xml layout
        patients= new ArrayList<>();
        patients.add(new Persons(1232,"Emma Wison","MRI"));
        patients.add(new Persons(1232,"Lavery Maiss","ECG"));
        patients.add(new Persons(1234,"Lillie Watts","Lung Scan"));


        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MyAdapter(TITLES, ICONS, NAME, EMAIL, PROFILE, this);

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

        final GestureDetector mGestureDetector = new GestureDetector(LabReportCentre.this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }

            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());


                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    Drawer.closeDrawers();
                    //On click of particular value in the drawer, the corresponding class is called. It is generated according to the ID of value selcted)
                    if (recyclerView.getChildPosition(child) == 1) {
                        Intent i = new Intent(LabReportCentre.this, DoctorMode.class);
                        startActivity(i);
                    }
                    if (recyclerView.getChildPosition(child) == 3) {
                        Intent i = new Intent(LabReportCentre.this, SearchDispensary.class);
                        startActivity(i);
                    }
                    return true;

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }
        });

        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager


        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }


        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();
        buildtable(2,2);

    }

    //This method is used to build the table that contains the Patient Report information such as PatientID,name and report details
    private void buildtable(int rows, int cols) {

       //Creates the table header row with the column names as PatientID,Name,Report
        TableRow tableHead = new TableRow(this);
        tableHead.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView labelID = new TextView(this);
        labelID.setText("PatientID");
        labelID.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        labelID.setBackgroundResource(R.drawable.cell_header);
        labelID.setPadding(25, 25, 25, 25);
        tableHead.addView(labelID);

        TextView labelname = new TextView(this);
        labelname.setText("Patient Name");
        labelname.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        labelname.setBackgroundResource(R.drawable.cell_header);
        labelname.setPadding(25, 25, 25, 25);
        tableHead.addView(labelname);

        TextView labelreport = new TextView(this);
        labelreport.setText("Report Name");
        labelreport.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        labelreport.setBackgroundResource(R.drawable.cell_header);
        labelreport.setPadding(25, 25, 25, 25);
        tableHead.addView(labelreport);

        table.addView(tableHead, new TableLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        for(int i = 0; i < rows; i++) {
            //Populates the row with report details from database

            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView idnumber = new TextView(this);
            String idnumberString = ""+patients.get(i).id;
            idnumber.setText(idnumberString);
            idnumber.setPadding(25, 25, 25, 25);
            idnumber.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            idnumber.setBackgroundResource(R.drawable.cell_shape);
            tableRow.addView(idnumber);

            TextView name = new TextView(this);
            String nameString = patients.get(i).name;
            name.setText(nameString);
            name.setPadding(25, 25, 25, 25);
            name.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            name.setBackgroundResource(R.drawable.cell_shape);
            tableRow.addView(name);

            TextView report = new TextView(this);
            String reportString = patients.get(i).Report;
            report.setText(reportString);
            report.setPadding(25, 25, 25, 25);
            report.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            report.setBackgroundResource(R.drawable.cell_shape);
            tableRow.addView(report);

            table.addView(tableRow, new TableLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            table.canScrollVertically(1);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_otschedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
