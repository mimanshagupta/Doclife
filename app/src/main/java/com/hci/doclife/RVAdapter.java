package com.hci.doclife;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/** This class is a custom adapter for the patient card views implemented in the doctor mode class
 * It instantiates the layout of the card with item.xml and additionally
 * implement an on click listener that displays a dialogue box when a card is clicked
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{
    Context ctx;
   static int id;

    // Creating ViewHolder inner class custom to the Person class
    public static class PersonViewHolder extends RecyclerView.ViewHolder  {
        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;

     //ViewHolder constructor with itemView as a paramter. This instantiates the card form the layout to the viewholder
        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }


    }

    List<Persons> persons;

    //Constructor for the custom adpater for cardview
    RVAdapter(List<Persons> persons, Context ctx){
        this.persons = persons;
        this.ctx=ctx;
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }


    //Below first we override the method onCreateViewHolder which is called when the ViewHolder is
    //Created, In this method we inflate the item.xml

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false); //getting the layout from item
//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Persons p= displayinfo(id);
//                Toast.makeText(v.getContext(),""+id,Toast.LENGTH_SHORT).show();
//                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
//                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View view = inflater.inflate(R.layout.dialogue_item, null);
//                builder.setView(view);
//                AlertDialog flooralert = builder.create();
//                flooralert.show();
//
//                TextView name = (TextView) view.findViewById(R.id.name);
//                name.setText(p.name);
//            }
//        });
        PersonViewHolder pvh = new PersonViewHolder(v);
        //returns a viewholder of type personviewholder
        return pvh;
    }

    //Next we override a method which is called when the item in a row is needed to be displayed, here the int position
    // Tells us item at which position is being constructed to be displayed and the holder id of the holder object tell us
    // which view type is being created 1 for item row
    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, final int i) {

        //Personviewholder that displays the person object and its member variables
        personViewHolder.personName.setText(persons.get(i).name);
        personViewHolder.personAge.setText(persons.get(i).age);
        personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);
        personViewHolder.cv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creates a dialogue box on click
                Persons p= displayinfo(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                //LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //View view = inflater.inflate(R.layout.dialogue_item, null);
                //builder.setView(view);
                builder.setTitle(p.name);
                builder.setMessage(persons.get(i).details);
                builder.setPositiveButton("Close", null);
                AlertDialog flooralert = builder.create();
                flooralert.show();
                //Displays the name and patient details retreived from person object
                //TextView name = (TextView) view.findViewById(R.id.name);
                //TextView details=(TextView)view.findViewById(R.id.patienthistory);
                //details.setText(persons.get(i).details);
                //name.setText("Patient Name:"+p.name);
            }
        });



    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    //Custom method to get the patient information of the card clicked
    public Persons displayinfo( int i){
        Persons p= this.getItem(i);
        return p;
    }

    //Returns the Persons object at a particular position
    public Persons getItem(int position) {
        return persons.get(position);
    }


}