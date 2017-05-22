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

public class BillAdapter extends ArrayAdapter<BillItem> {
    Context context;
    int layoutResourceId;
    ArrayList<BillItem> data;
    public BillAdapter(Context context, int layoutResourceId, ArrayList<BillItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        BillAdapter.BillHolder holder = null;
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new BillAdapter.BillHolder();

            holder.bill_id = (TextView)row.findViewById(R.id.bill_id);
            holder.title = (TextView)row.findViewById(R.id.bill_title);
            holder.bill_date = (TextView)row.findViewById(R.id.bill_date);

            row.setTag(holder);
        }
        else
        {
            holder = (BillAdapter.BillHolder)row.getTag();
        }

        BillItem billitem = data.get(position);
        holder.bill_id.setText(billitem.bill_id);
        holder.title.setText(billitem.title);
        holder.bill_date.setText(billitem.introduce);


        return row;
    }

    static class BillHolder
    {

        TextView bill_id;
        TextView title;
        TextView bill_date;

    }
}
