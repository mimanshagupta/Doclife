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
import android.widget.Toast;

import java.util.List;

/**
 * Created by Manasi on 21-11-2015.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{
    Context ctx;
   static int id;



    public static class PersonViewHolder extends RecyclerView.ViewHolder  {
        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;


        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }


    }

    List<Persons> persons;

    RVAdapter(List<Persons> persons, Context ctx){
        this.persons = persons;
        this.ctx=ctx;
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
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
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, final int i) {

        personViewHolder.personName.setText(persons.get(i).name);
        personViewHolder.personAge.setText(persons.get(i).age);
        personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);
        personViewHolder.cv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Persons p= displayinfo(i);
                Toast.makeText(v.getContext(), "" + id, Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.dialogue_item, null);
                builder.setView(view);
                AlertDialog flooralert = builder.create();
                flooralert.show();

                TextView name = (TextView) view.findViewById(R.id.name);
                TextView details=(TextView)view.findViewById(R.id.patienthistory);
                details.setText(persons.get(i).details);
                name.setText("Patient Name:"+p.name);
            }
        });



    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public Persons displayinfo( int i){
        Persons p= this.getItem(i);
        return p;
    }


    public Persons getItem(int position) {
        return persons.get(position);
    }


}