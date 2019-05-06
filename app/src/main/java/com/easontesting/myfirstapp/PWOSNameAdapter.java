package com.easontesting.myfirstapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PWOSNameAdapter extends ArrayAdapter<PWOSName> {
    private final String tag = "NameAdapter";
    private List<PWOSName> names;
    private Context context;
    public PWOSNameAdapter(Context context, int resource, List<PWOSName> names) {
        super(context, resource, names);
        this.context = context;
        this.names = names;
        Log.w(tag, "easontesting "+tag+" NameAdapter 1");
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.w(tag, "easontesting "+tag+" getView 1");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //create a custome view using names.xml
        View listViewItem = inflater.inflate(R.layout.pwosnames, null, true);
        //get name from names
        PWOSName name = names.get(position);
        //assign data to view
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        ImageView imageViewStatus = (ImageView) listViewItem.findViewById(R.id.imageViewStatus);
        textViewName.setText(name.getName());
        if (name.getStatus() == 0)
            imageViewStatus.setBackgroundResource(R.drawable.stopwatch);
        else
            imageViewStatus.setBackgroundResource(R.drawable.success);
        //return the custom view
        return listViewItem;
    }
}