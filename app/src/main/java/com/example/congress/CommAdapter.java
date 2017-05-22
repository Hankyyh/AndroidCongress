package com.example.congress;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Hank on 12/1/2016.
 */

public class CommAdapter extends ArrayAdapter<CommItem> {
    Context context;
    int layoutResourceId;
    ArrayList<CommItem> data;
    public CommAdapter(Context context, int layoutResourceId, ArrayList<CommItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CommAdapter.CommHolder holder = null;
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new CommAdapter.CommHolder();

            holder.comm_id = (TextView)row.findViewById(R.id.comm_id);
            holder.name = (TextView)row.findViewById(R.id.name);
            holder.chamber = (TextView)row.findViewById(R.id.chamber);

            row.setTag(holder);
        }
        else
        {
            holder = (CommAdapter.CommHolder)row.getTag();
        }

        CommItem commitem = data.get(position);
        holder.comm_id.setText(commitem.comm_id);
        holder.name.setText(commitem.name);
        holder.chamber.setText(commitem.chamber);


        return row;
    }

    static class CommHolder
    {

        TextView comm_id;
        TextView name;
        TextView chamber;

    }
}
