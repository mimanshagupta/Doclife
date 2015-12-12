package com.hci.doclife;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class EmergencyCenter extends ActionBarActivity implements View.OnClickListener{

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

    ActionBarDrawerToggle mDrawerToggle;

    ImageView emergencybutton, play, pause;
    ImageView recordsound;
    private MediaRecorder myAudioRecorder;
    private String outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recordedsound.3gpp";
    ImageView blue, red, white, yellow, orange, green, pink, brown, purple, black;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_center);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        //record sound dialog box and functionality is recorded here
        recordsound = (ImageView) findViewById(R.id.recordsound);

        recordsound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder record = new AlertDialog.Builder(EmergencyCenter.this);
                record.setTitle("Record Message");
                LayoutInflater factory = LayoutInflater.from(EmergencyCenter.this);
                final View view = factory.inflate(R.layout.record_dialog, null);
                play = (ImageView) view.findViewById(R.id.play);
                pause = (ImageView) view.findViewById(R.id.stop);

                myAudioRecorder = new MediaRecorder();
                myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                myAudioRecorder.setOutputFile(outputFile);

                play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        play.setImageResource(R.drawable.play_pressed);
                        try {
                            myAudioRecorder.prepare();
                            myAudioRecorder.start();
                        } catch (IllegalStateException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        play.setEnabled(false);
                        pause.setEnabled(true);

                        Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
                    }
                });

                pause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pause.setImageResource(R.drawable.stop_pressed);
                        try {
                            myAudioRecorder.stop();
                            myAudioRecorder.release();
                            myAudioRecorder = null;
                            pause.setEnabled(false);
                            play.setEnabled(true);
                        } catch (IllegalStateException e) {
                            //  it is called before start()
                            e.printStackTrace();
                        } catch (RuntimeException e) {
                            // no valid audio/video data has been received
                            e.printStackTrace();
                        }

                        Toast.makeText(getApplicationContext(), "Audio recorded successfully", Toast.LENGTH_LONG).show();

                    }
                });

                /*pause.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        pause.setImageResource(R.drawable.stop_pressed);
                        return false;
                    }
                });*/
                record.setView(view);
                record.setNeutralButton("Done", null);
                record.create().show();
            }
        });

        emergencybutton = (ImageView) findViewById(R.id.emergencybutton);
        emergencybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder colorpick = new AlertDialog.Builder(EmergencyCenter.this);
                colorpick.setTitle("Emergency Code");
                LayoutInflater factory = LayoutInflater.from(EmergencyCenter.this);
                final View view = factory.inflate(R.layout.emergency_color, null);
                blue = (ImageView) view.findViewById(R.id.blue);
                red = (ImageView) view.findViewById(R.id.red);
                white = (ImageView) view.findViewById(R.id.white);
                yellow = (ImageView) view.findViewById(R.id.yellow);
                orange = (ImageView) view.findViewById(R.id.orange);
                green = (ImageView) view.findViewById(R.id.green);
                pink = (ImageView) view.findViewById(R.id.pink);
                brown = (ImageView) view.findViewById(R.id.brown);
                purple = (ImageView) view.findViewById(R.id.purple);
                black = (ImageView) view.findViewById(R.id.black);
                blue.setOnClickListener(EmergencyCenter.this);
                red.setOnClickListener(EmergencyCenter.this);
                white.setOnClickListener(EmergencyCenter.this);
                yellow.setOnClickListener(EmergencyCenter.this);
                orange.setOnClickListener(EmergencyCenter.this);
                green.setOnClickListener(EmergencyCenter.this);
                pink.setOnClickListener(EmergencyCenter.this);
                brown.setOnClickListener(EmergencyCenter.this);
                purple.setOnClickListener(EmergencyCenter.this);
                black.setOnClickListener(EmergencyCenter.this);
                colorpick.setView(view);
                colorpick.setNeutralButton("Done", null);
                colorpick.create().show();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MyAdapter(TITLES,ICONS,NAME,EMAIL,PROFILE,this);

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

        final GestureDetector mGestureDetector = new GestureDetector(EmergencyCenter.this, new GestureDetector.SimpleOnGestureListener() {

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
                    //open selected menu item from nav drawer
                    if (recyclerView.getChildPosition(child) == 4) {
                        Intent i = new Intent(EmergencyCenter.this, OTSchedule.class);
                        startActivity(i);
                    }
                    if (recyclerView.getChildPosition(child) == 2) {
                        Intent i = new Intent(EmergencyCenter.this,LabReportCentre.class);
                        startActivity(i);
                    }
                    if (recyclerView.getChildPosition(child) == 3) {
                        Intent i = new Intent(EmergencyCenter.this,SearchDispensary.class);
                        startActivity(i);
                    }
                    if (recyclerView.getChildPosition(child) == 5) {
                        Intent i = new Intent(EmergencyCenter.this,DutySchedule.class);
                        startActivity(i);
                    }
                    if (recyclerView.getChildPosition(child) == 1) {
                        Intent i = new Intent(EmergencyCenter.this,DoctorMode.class);
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
                // code here will execute once the drawer is opened
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }



        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_emergency_center, menu);
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

    //new activity with flashing screen and color of emergerncy
    @Override
    public void onClick(View v) {
        if(v.equals(blue)) {
            Intent intent = new Intent(this, FlashActivity.class);
            intent.putExtra("Color", Color.BLUE);
            startActivity(intent);
        }
        if(v.equals(red)) {
            Intent intent = new Intent(this, FlashActivity.class);
            intent.putExtra("Color", Color.RED);
            startActivity(intent);
        }
        if(v.equals(white)) {
            Intent intent = new Intent(this, FlashActivity.class);
            intent.putExtra("Color", Color.WHITE);
            startActivity(intent);
        }
        if(v.equals(yellow)) {
            Intent intent = new Intent(this, FlashActivity.class);
            intent.putExtra("Color", Color.YELLOW);
            startActivity(intent);
        }
        if(v.equals(orange)) {
            Intent intent = new Intent(this, FlashActivity.class);
            intent.putExtra("Color", Color.rgb(255,165,0));
            startActivity(intent);
        }
        if(v.equals(green)) {
            Intent intent = new Intent(this, FlashActivity.class);
            intent.putExtra("Color", Color.GREEN);
            startActivity(intent);
        }
        if(v.equals(pink)) {
            Intent intent = new Intent(this, FlashActivity.class);
            intent.putExtra("Color", Color.rgb(255,20,147));
            startActivity(intent);
        }
        if(v.equals(brown)) {
            Intent intent = new Intent(this, FlashActivity.class);
            intent.putExtra("Color", Color.rgb(139,69,19));
            startActivity(intent);
        }
        if(v.equals(purple)) {
            Intent intent = new Intent(this, FlashActivity.class);
            intent.putExtra("Color", Color.rgb(128,0,128));
            startActivity(intent);
        }
        if(v.equals(black)) {
            Intent intent = new Intent(this, FlashActivity.class);
            intent.putExtra("Color", Color.BLACK);
            startActivity(intent);
        }
    }
}
