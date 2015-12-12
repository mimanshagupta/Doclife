package com.hci.doclife;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DutySchedule extends ActionBarActivity implements DatePicker.OnDateChangedListener{

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
    Button setDateButton;
    TextView setDateTextView;
    TableLayout tableLayout;
    List<DutyScheduleList> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duty_schedule);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MyAdapter(TITLES, ICONS, NAME, EMAIL, PROFILE, this);

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

        final GestureDetector mGestureDetector = new GestureDetector(DutySchedule.this, new GestureDetector.SimpleOnGestureListener() {

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

                //launch activity for selected menu item from nav drawer
                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    Drawer.closeDrawers();
                    //Toast.makeText(DoctorMode.this, "The Item Clicked is: " + recyclerView.getChildPosition(child), Toast.LENGTH_SHORT).show();
                    if (recyclerView.getChildPosition(child) == 1) {
                        Intent i = new Intent(DutySchedule.this, DoctorMode.class);
                        startActivity(i);
                    }
                    if (recyclerView.getChildPosition(child) == 4) {
                        Intent i = new Intent(DutySchedule.this, OTSchedule.class);
                        startActivity(i);
                    }
                    if (recyclerView.getChildPosition(child) == 2) {
                        Intent i = new Intent(DutySchedule.this,LabReportCentre.class);
                        startActivity(i);
                    }
                    if (recyclerView.getChildPosition(child) == 3) {
                        Intent i = new Intent(DutySchedule.this,SearchDispensary.class);
                        startActivity(i);
                    }
                    if (recyclerView.getChildPosition(child) == 6) {
                        Intent i = new Intent(DutySchedule.this,EmergencyCenter.class);
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

        //get day selected by user and highlight field in table accordingly

        setDateButton = (Button) findViewById(R.id.setDateButton);
        setDateTextView = (TextView) findViewById(R.id.setDateTextView);
        final Calendar calendar = Calendar.getInstance();
        setDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DutySchedule.this);
                DatePicker picker = new DatePicker(DutySchedule.this);
                picker.setCalendarViewShown(false);
                builder.setTitle("Select Date");
                builder.setView(picker);
                builder.setNegativeButton("Cancel", null);
                builder.setPositiveButton("Set", null);

                picker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), DutySchedule.this);

                builder.show();
            }
        });
        int mon = calendar.get(Calendar.MONTH);
        mon++;
        setDateTextView.setText(calendar.get(Calendar.DAY_OF_MONTH) + " / " + mon + " / " + calendar.get(Calendar.YEAR));
        tableLayout = (TableLayout) findViewById(R.id.DutyScheduleTable);
        Date today = Calendar.getInstance().getTime();
        list = new ArrayList<>();

        list.add(new DutyScheduleList("Dr. Mimansha Gupta", "Monday"));

        list.add(new DutyScheduleList("Dr. Manasi Jayaraman", "Tuesday"));

        list.add(new DutyScheduleList("Dr. Kate Green", "Wednesday"));

        list.add(new DutyScheduleList("Dr. Mimansha Gupta", "Thursday"));

        list.add(new DutyScheduleList("Dr. Manasi Jayaraman", "Friday"));

        list.add(new DutyScheduleList("Dr. Kate Green", "Saturday"));

        list.add(new DutyScheduleList("Dr. Mimansha Gupta", "Sunday"));


        BuildTable(7, 2);

    }

    private void BuildTable(int rows, int cols) {

        //dynamically builds table with list items

        TableRow tableHead = new TableRow(this);
        tableHead.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView labelOT = new TextView(this);
        labelOT.setText("OT");
        labelOT.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        labelOT.setBackgroundResource(R.drawable.cell_header);
        labelOT.setPadding(25,25,25,25);
        tableHead.addView(labelOT);

        TextView labelDepartment = new TextView(this);
        labelDepartment.setText("Duty Specialist");
        labelDepartment.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        labelDepartment.setBackgroundResource(R.drawable.cell_header);
        labelDepartment.setPadding(25,25,25,25);
        tableHead.addView(labelDepartment);

        tableLayout.addView(tableHead, new TableLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        for(int i = 0; i < rows; i++) {

            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tableRow.setFocusable(true);
            tableRow.setFocusableInTouchMode(true);
            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


            TextView department = new TextView(this);
            String departmentString = list.get(i).getDepartment();
            department.setText(departmentString);
            department.setPadding(25,25,25,25);
            department.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            department.setBackgroundResource(R.drawable.cell_shape);
            tableRow.addView(department);

            TextView day = new TextView(this);
            String dayString = list.get(i).getDate();
            day.setText(dayString);
            day.setPadding(25, 25, 25, 25);
            day.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            day.setBackgroundResource(R.drawable.cell_shape);
            tableRow.addView(day);

            tableLayout.addView(tableRow, new TableLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tableLayout.canScrollVertically(1);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_duty_schedule, menu);
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

    //OnDateChanged highlights the selected date in the table

    @SuppressLint("ResourceAsColor")
    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        int m = monthOfYear;
        m++;
        setDateTextView.setText(dayOfMonth + " / " + m + " / " + year);

        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
        Date date = new Date(year, monthOfYear, dayOfMonth-1);
        String dayOfWeek = simpledateformat.format(date);
        //Log.d("mushu", dayOfWeek);
        //setDateTextView.setText(dayOfWeek);

        for(int i=0;i<8;i++)
        {
            TableRow row = (TableRow)tableLayout.getChildAt(i);
            TextView rowData = (TextView)row.getChildAt(1); // get child index on particular row
            String text = rowData.getText().toString();
            for(int j = 0; j < 2; j++) {
                rowData = (TextView)row.getChildAt(j);
                if(dayOfWeek.equals(text)){
                    rowData.setBackgroundResource(R.color.ColorPrimary);
                    rowData.setTextColor(Color.WHITE);
                }
                else {
                    rowData.setBackgroundResource(R.color.tablewhite);
                    rowData.setTextColor(Color.BLACK);
                }
                //Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
