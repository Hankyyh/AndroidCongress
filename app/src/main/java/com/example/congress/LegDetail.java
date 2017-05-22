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
import static com.example.congress.R.id.chamber;
import static com.example.congress.R.id.fax;
import static com.example.congress.R.id.firstline;
//import static com.example.congress.R.id.imageView;
import static com.example.congress.R.id.lastline;
import static com.example.congress.R.id.name;
import static com.example.congress.R.id.party;
import static com.example.congress.R.id.star;
import static com.example.congress.R.id.state;
//import static com.example.congress.R.id.textView;
import static com.squareup.picasso.Picasso.with;

/**
 * Created by Hank on 11/29/2016.
 */

public class LegDetail extends Activity {
    private LegItem leg_f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leg_detail);
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
                        ((Myglobal) LegDetail.this.getApplication()).addLeg(leg_f);
                        break;
                    case R.drawable.ic_staryellow:
                    default:
                        star.setImageResource(R.drawable.ic_starwhite);
                        star.setTag(R.drawable.ic_starwhite);
                        star.setColorFilter(Color.parseColor("#000000"));
                        ((Myglobal) LegDetail.this.getApplication()).deleteLeg(leg_f);
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
        String rrr = "http://hank.sgpmuxr6xj.us-west-1.elasticbeanstalk.com/temp.php?view=" + item;
        // Making a request to url and getting response
        String jsonStr_leg1 = sh.makeServiceCall(rrr);


//        Log.e(TAG, "Response from url: ");

//            if (jsonStr != null) {
        try {
//                    JSONObject jsonObj = new JSONObject(jsonStr);
//
//                    // Getting JSON Array node
//                    JSONArray contacts = jsonObj.getJSONArray("contacts");            Json

            JSONObject contacts = new JSONObject(jsonStr_leg1);
            String ccc = contacts.getString("bio");
            JSONArray a = new JSONArray(ccc);



            JSONObject c = a.getJSONObject(0);


            // looping through All Contacts


            final String bioguide_id = c.getString("bioguide_id");
            final String first_name = c.getString("first_name");
            final String last_name = c.getString("last_name");
            final String title = c.getString("title");
            final String name = title + ". " + last_name + ", " + first_name;


            String district = c.getString("district");
            final String email = c.getString("oc_email");
            String party_name = c.getString("party");



            final String phone = c.getString("phone");
            final String office = c.getString("office");
            final String state = c.getString("state");
            final String fax = c.getString("fax");

            if (district == "null") {
                district = "0";
            }
            final String firstline = last_name + ", " + first_name;
            final String lastline = "(" + party_name + ")" + state + "-District " + district;

            final String chamber;
            if (c.getString("chamber").equals("senate")) {
                chamber = "Senate";
            }
            else {
                chamber = "House";
            }
            final String party;
            final String P;
            if (c.getString("party").equals("R")) {
                party = "Republican";
                P = "r";
            }
            else {
                party = "Democratic";
                P = "d";
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
            SimpleDateFormat change = new SimpleDateFormat("MMM dd,yyyy");
            String term_start = c.getString("term_start");
            String term_end = c.getString("term_end");
            String bd = c.getString("birthday");
                Date date1 = formatter.parse(term_start);
                Date date2 = formatter.parse(term_end);
                Date date3 = formatter.parse(bd);
            final String ts  = change.format(date1);
            final String td = change.format(date2);
            final String birthday = change.format(date3);





            arg1 = firstline;
            arg2 = lastline;
            arg3 = bioguide_id;
            arg4 = state;
            arg5 = last_name;
            leg_f = new LegItem(arg1,arg2,arg3,arg4,arg5);


            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    TextView Name = (TextView) findViewById(R.id.name);
                    Name.setText(name);
                    TextView Email = (TextView) findViewById(R.id.email);
                    Email.setText(email);
                    TextView Chamber = (TextView) findViewById(R.id.chamber);
                    Chamber.setText(chamber);
                    TextView Contact = (TextView) findViewById(R.id.contact);
                    Contact.setText(phone);
                    TextView Office = (TextView) findViewById(R.id.office);
                    Office.setText(office);
                    TextView State = (TextView) findViewById(R.id.state);
                    State.setText(state);
                    TextView Fax = (TextView) findViewById(R.id.fax);
                    Fax.setText(fax);
                    TextView Party = (TextView) findViewById(R.id.party);
                    Party.setText(party);
                    TextView Start_Term = (TextView) findViewById(R.id.start);
                    Start_Term.setText(ts);
                    TextView End_Term = (TextView) findViewById(R.id.end);
                    End_Term.setText(td);
                    TextView Birthday = (TextView) findViewById(R.id.birthday);
                    Birthday.setText(birthday);

                    String bio_url = "https://theunitedstates.io/images/congress/225x275/" + bioguide_id + ".jpg";
                    ImageView img_bio = (ImageView) findViewById(R.id.img_bio);
                    Picasso.with(LegDetail.this).load(bio_url).resize(200,230).into(img_bio);

                    String bio_url = "https://theunitedstates.io/images/congress/225x275/" + bioguide_id + ".jpg";
                    ImageView img_bio = (ImageView) findViewById(R.id.img_bio);
                    Picasso.with(LegDetail.this).load(bio_url).resize(200,230).into(img_bio);
                    String party_url = "http://cs-server.usc.edu:45678/hw/hw8/images/"+ P +".png";
                    ImageView img_party = (ImageView) findViewById(R.id.img_party);
                    Picasso.with(LegDetail.this).load(party_url).resize(80,80).into(img_party);
                    ImageView star = (ImageView) findViewById(R.id.star);
                    boolean flag = ((Myglobal) LegDetail.this.getApplication()).detectLeg(new LegItem(arg1,arg2,arg3,arg4,arg5));
                    if (flag== true) {
                        star.setImageResource(R.drawable.ic_staryellow);
                        star.setTag(R.drawable.ic_staryellow);
                        star.setColorFilter(Color.parseColor("#FFD700"));
                    }
                }
            });



        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
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

