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

public class AdminMode extends AppCompatActivity {

    private Toolbar toolbar;
    String TITLES[] = {"Bed Status", "Search Dispensary", "OT Schedule"};
    int ICONS[] = {R.drawable.ic_patient,R.drawable.ic_searchdispensary, R.drawable.ic_otschedule};

    String NAME = "Ms. Manasi Jayaraman";
    String EMAIL = "admin@hospital.com";
    int PROFILE = R.mipmap.ic_launcher;

    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;
    TableLayout table;
    List<BedStatus> beds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_admin_mode);
        beds= new ArrayList<>();

        table=(TableLayout)findViewById(R.id.labreportcentre); //Getting the table from the xml layout
        beds.add(new BedStatus("children", 20, 15));
        beds.add(new BedStatus("General",100,49));
        beds.add(new BedStatus("Cancer",30,12));
        beds.add(new BedStatus("ICU",5,1));
        beds.add(new BedStatus("NICU",20,10));



        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MyAdapter(TITLES, ICONS, NAME, EMAIL, PROFILE, this);

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

        final GestureDetector mGestureDetector = new GestureDetector(AdminMode.this, new GestureDetector.SimpleOnGestureListener() {

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
                    //On click of particular value in the drawer, the corresponding class is called. It is generated according to the ID of value selcted
                    if (recyclerView.getChildPosition(child) == 1) {
                        Intent i = new Intent(AdminMode.this, AdminMode.class);
                        startActivity(i);
                    }
                    if (recyclerView.getChildPosition(child) == 2) {
                        Intent i = new Intent(AdminMode.this, SearchDispensaryAdmin.class);
                        i.putExtra("Titles", TITLES);
                        i.putExtra("Icons", ICONS);
                        startActivity(i);
                    }
                    if (recyclerView.getChildPosition(child) == 3) {
                        Intent i = new Intent(AdminMode.this, OTSchedule.class);
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
        buildtable(4,4);

    }

    //This function builds the table and fills the rows and cols with the ward name,total beds and occupied beds
    private void buildtable(int rows, int cols) {
        //Creates the heading row that displays the heading of the columns
        TableRow tableHead = new TableRow(this);
        tableHead.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView labelward = new TextView(this);
        labelward.setText("Ward Name");
        labelward.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        labelward.setBackgroundResource(R.drawable.cell_header);
        labelward.setPadding(25, 25, 25, 25);
        tableHead.addView(labelward);

        TextView labelbeds = new TextView(this);
        labelbeds.setText("Total beds");
        labelbeds.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        labelbeds.setBackgroundResource(R.drawable.cell_header);
        labelbeds.setPadding(25, 25, 25, 25);
        tableHead.addView(labelbeds);

        TextView labeloccbeds = new TextView(this);
        labeloccbeds.setText("Occupied beds");
        labeloccbeds.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        labeloccbeds.setBackgroundResource(R.drawable.cell_header);
        labeloccbeds.setPadding(25, 25, 25, 25);
        tableHead.addView(labeloccbeds);

        table.addView(tableHead, new TableLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        //Populate the other rows of the table with the data from the database
        for(int i = 0; i < rows; i++) {

            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView wardnumber = new TextView(this);
            String otnumberString = ""+beds.get(i).ward;
            wardnumber.setText(otnumberString);
            wardnumber.setPadding(25, 25, 25, 25);
            wardnumber.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            wardnumber.setBackgroundResource(R.drawable.cell_shape);
            tableRow.addView(wardnumber);

            TextView totalbednumber = new TextView(this);
            String departmentString =""+ (beds.get(i).total);
            totalbednumber.setText(departmentString);
            totalbednumber.setPadding(25, 25, 25, 25);
            totalbednumber.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            totalbednumber.setBackgroundResource(R.drawable.cell_shape);
            tableRow.addView(totalbednumber);

            TextView occupiedbeds = new TextView(this);
            String dayString = ""+beds.get(i).occupied;
            occupiedbeds.setText(dayString);
            occupiedbeds.setPadding(25, 25, 25, 25);
            occupiedbeds.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            occupiedbeds.setBackgroundResource(R.drawable.cell_shape);
            tableRow.addView(occupiedbeds);

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
