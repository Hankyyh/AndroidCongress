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
import static com.example.congress.R.id.star;
import static com.example.congress.R.id.state;
//import static com.example.congress.R.id.textView;
import static com.squareup.picasso.Picasso.with;

/**
 * Created by Hank on 11/29/2016.
 */

public class BillDetail extends Activity {
    private BillItem bill_f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_detail);
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
                        ((Myglobal) BillDetail.this.getApplication()).addBill(bill_f);
                        break;
                    case R.drawable.ic_staryellow:
                    default:
                        star.setImageResource(R.drawable.ic_starwhite);
                        star.setTag(R.drawable.ic_starwhite);
                        star.setColorFilter(Color.parseColor("#000000"));
                        ((Myglobal) BillDetail.this.getApplication()).deleteBill(bill_f);
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
            String rrr = "http://hank.sgpmuxr6xj.us-west-1.elasticbeanstalk.com/temp.php?view2=" + item.toLowerCase();
            // Making a request to url and getting response
            String jsonStr_bill = sh.makeServiceCall(rrr);


//        Log.e(TAG, "Response from url: ");

//            if (jsonStr != null) {
            try {
//                    JSONObject jsonObj = new JSONObject(jsonStr);
//
//                    // Getting JSON Array node
//                    JSONArray contacts = jsonObj.getJSONArray("contacts");            Json

//                JSONObject contacts = new JSONObject(jsonStr_bill);
//                String ccc = contacts.toString();
//                JSONObject c = a.getJSONObject(0); a = new JSONArray(ccc);
//
//                JSONObject c = a.getJSONObject(0);
                JSONArray contacts = new JSONArray(jsonStr_bill);
                JSONObject c = contacts.getJSONObject(0);


                final String bill_id = c.getString("bill_id");
                final String title = c.getString("official_title");
                JSONObject sponsor = c.getJSONObject("sponsor");
                final String bill_type = c.getString("bill_type").toUpperCase();
                final String sponsor_name = sponsor.getString("title") + ". " +sponsor.getString("last_name") + ", " + sponsor.getString("first_name");



                JSONObject history = c.getJSONObject("history");
                final String status;
                if (history.getString("active") == "true") {
                    status = "Active";
                }
                else {
                    status = "New";
                }


                JSONObject urls = c.getJSONObject("urls");
                final String congress_url = urls.getString("congress");
                final String version;
                final String bill_url;
//                if (c.getString("last_version").equals("") == false) {
//                    JSONObject last_version = c.getJSONObject("last_version");
//                    version = last_version.getString("version_name");
//
//                    bill_url = last_version.getJSONObject("urls").getString("pdf");
//                }
//                else {
                    version = "Null";
                    bill_url = "Null";
//                }

                final String chamber;
                if (c.getString("chamber") == "senate") {
                    chamber = "Senate";
                }
                else {
                    chamber = "House";
                }

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
                SimpleDateFormat change = new SimpleDateFormat("MMM dd,yyyy");
                String introduce = c.getString("introduced_on");

                Date date = formatter.parse(introduce);

                final String intro_on  = change.format(date);






                arg1 = bill_id;
                arg2 = title;
                arg3 = introduce;

                bill_f = new BillItem(arg1,arg2,arg3);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        TextView BillID = (TextView) findViewById(R.id.billid);
                        BillID.setText(bill_id);
                        TextView Title = (TextView) findViewById(R.id.title);
                        Title.setText(title);
                        TextView Chamber = (TextView) findViewById(R.id.chamber);
                        Chamber.setText(chamber);
                        TextView BillType = (TextView) findViewById(R.id.billtype);
                        BillType.setText(bill_type);
                        TextView Sponsor = (TextView) findViewById(R.id.sponsor);
                        Sponsor.setText(sponsor_name);
                        TextView Status = (TextView) findViewById(R.id.status);
                        Status.setText(status);
                        TextView IntroOn = (TextView) findViewById(R.id.introduce);
                        IntroOn.setText(intro_on);
                        TextView ConURL = (TextView) findViewById(R.id.congressurl);
                        ConURL.setText(congress_url);
                        TextView Version= (TextView) findViewById(R.id.version);
                        Version.setText(version);
                        TextView BillURL = (TextView) findViewById(R.id.billurl);
                        BillURL.setText(bill_url);


                        ImageView star = (ImageView) findViewById(R.id.star);
                        boolean flag = false;
                        try {
                            flag = ((Myglobal) BillDetail.this.getApplication()).detectBill(new BillItem(arg1,arg2,arg3));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
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

