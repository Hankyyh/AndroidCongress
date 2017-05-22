package com.example.congress;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.example.congress.R.id.birthday;
import static com.example.congress.R.id.chamber;
import static com.example.congress.R.id.email;
import static com.example.congress.R.id.fax;
import static com.example.congress.R.id.firstline;
//import static com.example.congress.R.id.imageView;
import static com.example.congress.R.id.lastline;
import static com.example.congress.R.id.name;
import static com.example.congress.R.id.office;
import static com.example.congress.R.id.party;
import static com.example.congress.R.id.sponsor;
import static com.example.congress.R.id.star;
import static com.example.congress.R.id.state;
import static com.example.congress.R.id.status;
//import static com.example.congress.R.id.textView;
import static com.squareup.picasso.Picasso.with;

/**
 * Created by Hank on 11/29/2016.
 */

public class CommDetail extends Activity {
    private CommItem comm_f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comm_detail);
        new GetContacts().execute();

        final ImageView button = (ImageView) findViewById(R.id.but);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            };
        });
        final ImageView star = (ImageView) findViewById(R.id.star);

        star.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                assert(R.id.star == star.getId());
                Integer integer = (Integer) star.getTag();
                integer = integer == null ? 0 : integer;
                switch(integer) {
                    case R.drawable.ic_starwhite:
                        star.setImageResource(R.drawable.ic_staryellow);
                        star.setTag(R.drawable.ic_staryellow);
                        star.setColorFilter(Color.parseColor("#FFD700"));
                        ((Myglobal) CommDetail.this.getApplication()).addComm(comm_f);
                        break;
                    case R.drawable.ic_staryellow:
                    default:
                        star.setImageResource(R.drawable.ic_starwhite);
                        star.setTag(R.drawable.ic_starwhite);
                        star.setColorFilter(Color.parseColor("#000000"));
                        ((Myglobal) CommDetail.this.getApplication()).deleteComm(comm_f);
                        break;
                }
            };
        });

    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.second, menu);
//        return true;
//    }
    public class GetContacts extends AsyncTask<Void, Void, Void> {

        private String arg1,arg2,arg3,arg4,arg5;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//             Showing progress dialog


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Intent intent = getIntent();


            String item = intent.getStringExtra("selected-item");

            HttpHandler sh = new HttpHandler();
            String rrr = "http://hank.sgpmuxr6xj.us-west-1.elasticbeanstalk.com/temp.php?view3=" + item;
            // Making a request to url and getting response
            String jsonStr_comm = sh.makeServiceCall(rrr);


//        Log.e(TAG, "Response from url: ");

//            if (jsonStr != null) {
            try {
//                    JSONObject jsonObj = new JSONObject(jsonStr);
//
//                    // Getting JSON Array node
//                    JSONArray contacts = jsonObj.getJSONArray("contacts");            Json

//                JSONObject contacts = new JSONObject(jsonStr_comm);
//                String ccc = contacts.toString();
//                JSONObject c = a.getJSONObject(0); a = new JSONArray(ccc);
//
//                JSONObject c = a.getJSONObject(0);
                JSONArray contacts = new JSONArray(jsonStr_comm);
                JSONObject c = contacts.getJSONObject(0);


                final String comm_id = c.getString("committee_id");
                final String name = c.getString("name");
                final String parent = c.getString("parent_committee_id");
                String phone1;
                if (c.has("phone")) {
                    phone1 = c.getString("phone");
                }
                else {
                    phone1 = "N.A";
                }
                final String contact = phone1;
                String office1;
                if (c.has("office") ) {
                    office1 = c.getString("office");
                }
                else {
                    office1 = "N.A";
                }
                final String office = office1;

                final String chamber;
                final String C;
                if (c.getString("chamber").equals("senate")) {
                    chamber = "Senate";
                    C="s.svg";
                }
                else if (c.getString("chamber").equals("house")) {
                    chamber = "House";
                    C="h.png";
                }
                else {
                    chamber = "Joint";
                    C="s.svg";
                }



                arg1 = comm_id;
                arg2 = name;
                arg3 = chamber;

                comm_f = new CommItem(arg1,arg2,arg3);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        TextView CommID = (TextView) findViewById(R.id.commid);
                        CommID.setText(comm_id);
                        TextView Name = (TextView) findViewById(R.id.name);
                        Name.setText(name);
                        TextView Chamber = (TextView) findViewById(R.id.chamber);
                        Chamber.setText(chamber);
                        TextView Parent = (TextView) findViewById(R.id.parent);
                        Parent.setText(parent);
                        TextView Contact = (TextView) findViewById(R.id.contact);
                        Contact.setText(contact);
                        TextView Office = (TextView) findViewById(R.id.office);
                        Office.setText(office);

                        String party_url = "http://cs-server.usc.edu:45678/hw/hw8/images/"+ C ;
                        ImageView img_party = (ImageView) findViewById(R.id.img_party);
                        Picasso.with(CommDetail.this).load(party_url).resize(80,80).into(img_party);
                        ImageView star = (ImageView) findViewById(R.id.star);
                        boolean flag = false;
                        flag = ((Myglobal) CommDetail.this.getApplication()).detectComm(new CommItem(arg1,arg2,arg3));
                        if (flag== true) {
                            star.setImageResource(R.drawable.ic_staryellow);
                            star.setTag(R.drawable.ic_staryellow);
                            star.setColorFilter(Color.parseColor("#FFD700"));
                        }
                    }
                });



            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
//            } else {
//                Log.e(TAG, "Couldn't get json from server.");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(),
//                                "Couldn't get json from server. Check LogCat for possible errors!",
//                                Toast.LENGTH_LONG)
//                                .show();
//                    }
//                });
//
//            }
        }
    }

}

