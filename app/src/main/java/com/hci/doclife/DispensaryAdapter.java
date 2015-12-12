package com.hci.doclife;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/** This class creates a custom list adapter that returns the listitem as two text views
 * containing the medicine name and quantity
 */
public class DispensaryAdapter extends ArrayAdapter<Medicines> implements Filterable {
    private List <Medicines>medicines;
    private Context context;


    public DispensaryAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    //Custom constructor that instantiates the context and list that is used to feed the listview
    public DispensaryAdapter(Context context, List<Medicines> items) {
        super(context, -1, items);
        this.context=context;
        this.medicines=items;
    }


   //This method returns the row view that returns the view of a row in the custom list
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //get the custom layout form the dispensary_list_xml(Contains two textviews)
        View rowView = inflater.inflate(R.layout.dispensary_list_item, parent, false);
        TextView name = (TextView) rowView.findViewById(R.id.product_name);
        TextView quantity = (TextView) rowView.findViewById(R.id.product_quantity);
        name.setText(medicines.get(position).Name); //set the name
        quantity.setText(medicines.get(position).Quantity);
        return rowView;

    }

    //This method filters the list displayed and displays only those values that match the letter entered in the edit text
    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            ArrayList<Medicines> tempList=new ArrayList<Medicines>();
            //constraint is the result from text  to filter against.
            //objects is your data set you will filter from
            if(constraint != null && medicines!=null) {
                int length=medicines.size();
                int i=0;
                while(i<length){
                    Medicines item=medicines.get(i);
                    //adding result set output array
                     if(item.Name.toLowerCase().startsWith(constraint.toString())) {
                         tempList.add(item);
                     }
                    i++;
                }
                //following two lines is very important
                //as publish result can only take FilterResults objects
                filterResults.values = tempList;
                filterResults.count = tempList.size();
            }
            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence contraint, FilterResults results) {
            //Publishing the filtered results in the listview
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                clear();
                addAll(((List<Medicines>) results.values));
                notifyDataSetChanged();
            }
        }
    };

}


