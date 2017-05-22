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
import java.util.List;

import static android.R.attr.data;
import static android.R.attr.detailSocialSummary;
import static android.R.attr.resource;


/**
 * Created by Hank on 11/25/2016.
 */

public class LegAdapter extends ArrayAdapter<LegItem> {
    Context context;
    int layoutResourceId;
    ArrayList<LegItem> data;
    public LegAdapter(Context context, int layoutResourceId, ArrayList<LegItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LegHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new LegHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.firstline = (TextView)row.findViewById(R.id.firstline);
            holder.lastline = (TextView)row.findViewById(R.id.lastline);

            row.setTag(holder);
        }
        else
        {
            holder = (LegHolder)row.getTag();
        }

        LegItem legitem = data.get(position);
        holder.firstline.setText(legitem.firstline);
        holder.lastline.setText(legitem.lastline);

        ImageView targetImageView = (ImageView) row.findViewById(R.id.imgIcon);
        String internetUrl = "https://theunitedstates.io/images/congress/225x275/" + legitem.bioguide_id + ".jpg";

        Picasso
                .with(context)
                .load(internetUrl)
                .resize(100,108)
                .into(targetImageView);

//        holder.imgIcon.setImageResource(legitem.icon);

        return row;
    }

    static class LegHolder
    {
        ImageView imgIcon;
        TextView firstline;
        TextView lastline;


    }
}
