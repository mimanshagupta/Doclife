package com.hci.doclife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchDispensary extends AppCompatActivity {
    // List view
    private ListView lv;
   // custom adapter
    DispensaryAdapter adapter;

    //Values array
    List <Medicines> medicines;


    // Search EditText
    EditText inputSearch;


    // ArrayList for Listview
    ArrayList<HashMap<String, String>> productList;

    String TITLES[] = {"Patient Center","Lab Report Center","Search Dispensary","OT Schedule", "Duty Schedule", "Emergency Center"};
    int ICONS[] = {R.drawable.ic_patient,R.drawable.ic_lab, R.drawable.ic_searchdispensary, R.drawable.ic_otschedule, R.drawable.ic_clipboard, R.drawable.ic_danger};

    String NAME = "Dr. Mimansha Gupta";
    String EMAIL = "doctor@hospital.com";
    int PROFILE = R.mipmap.ic_launcher;

    private Toolbar toolbar;
    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout
    List<Persons> persons;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dispensary);

        //Add medicine objects to medicines list
        medicines= new ArrayList<>();
        medicines.add(new Medicines("Paracetomol", "150 packs"));
        medicines.add(new Medicines("Insulin","150 injections"));
        medicines.add(new Medicines("Lovanix","140 packs"));
        medicines.add(new Medicines("Morphine","120 packs"));

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MyAdapter(TITLES,ICONS,NAME,EMAIL,PROFILE,this);

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

        final GestureDetector mGestureDetector = new GestureDetector(SearchDispensary.this, new GestureDetector.SimpleOnGestureListener() {

            @Override public boolean onSingleTapUp(MotionEvent e) {
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
                    if (recyclerView.getChildPosition(child) == 4) {
                        Intent i = new Intent(SearchDispensary.this, OTSchedule.class);
                        startActivity(i);
                    }
                    if (recyclerView.getChildPosition(child) == 2) {
                        Intent i = new Intent(SearchDispensary.this,LabReportCentre.class);
                        startActivity(i);
                    }
                    if (recyclerView.getChildPosition(child) == 3) {
                        Intent i = new Intent(SearchDispensary.this,SearchDispensary.class);
                        startActivity(i);
                    }
                    if (recyclerView.getChildPosition(child) == 1) {
                        Intent i = new Intent(SearchDispensary.this,DoctorMode.class);
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
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.openDrawer,R.string.closeDrawer){

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

        //listview is set to custom adapter searchdispensaryadapter
        lv = (ListView) findViewById(R.id.list_view);
        //Edittext for filtering purposes(searching purposes)
        inputSearch = (EditText) findViewById(R.id.inputSearch);

         adapter= new DispensaryAdapter(this,medicines);
         lv.setAdapter(adapter);

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text

                adapter.getFilter().filter(cs);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

    }

}
