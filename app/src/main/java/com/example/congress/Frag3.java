package com.example.congress;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.congress.R.id.firstline;
import static com.example.congress.R.id.lastline;
import static com.example.congress.R.id.list_leg1;
import static com.example.congress.R.id.party;
import static com.example.congress.R.id.state;
import static com.example.congress.R.id.tab1;
import static com.example.congress.R.id.tabHost;

/**
 * Created by Hank on 12/1/2016.
 */

public class Frag3 extends Fragment {
    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView comm1;
    private ListView comm2;
    private ListView comm3;
    // URL to get contacts JSON
//    private static String url = "http://api.androidhive.info/contacts/";
    private static String url_comm1 = "http://hank.sgpmuxr6xj.us-west-1.elasticbeanstalk.com/temp.php?text3=1";
    private static String url_comm2 = "http://hank.sgpmuxr6xj.us-west-1.elasticbeanstalk.com/temp.php?text3=2";
    private static String url_comm3 = "http://hank.sgpmuxr6xj.us-west-1.elasticbeanstalk.com/temp.php?text3=3";

    ArrayList<CommItem> commlist_comm1;
    ArrayList<CommItem> commlist_comm2;
    ArrayList<CommItem> commlist_comm3;
    public Frag3() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.comm_main, container, false);

        TabHost host = (TabHost) view.findViewById(tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(tab1);
        spec.setIndicator("HOUSE");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("SENATE");
        host.addTab(spec);
        //Tab 3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator("JOINT");
        host.addTab(spec);

        TabWidget widget = host.getTabWidget();
        for (int i = 0; i < widget.getChildCount(); i++) {
            View v = widget.getChildAt(i);
            // Look for the title view to ensure this is an indicator and not a divider.
            TextView tv = (TextView) v.findViewById(android.R.id.title);
            if (tv == null) {
                continue;
            }
            v.setBackgroundColor(Color.WHITE);
        }


        commlist_comm1 = new ArrayList<>();
        commlist_comm2 = new ArrayList<>();
        commlist_comm3 = new ArrayList<>();

        comm1 = (ListView) view.findViewById(R.id.list_comm1);
        comm2 = (ListView) view.findViewById(R.id.list_comm2);
        comm3 = (ListView) view.findViewById(R.id.list_comm3);

        new Frag3.GetContacts().execute();
        comm1.setOnItemClickListener(new Frag3.ListClickHandler(commlist_comm1));
        comm2.setOnItemClickListener(new Frag3.ListClickHandler(commlist_comm2));
        comm3.setOnItemClickListener(new Frag3.ListClickHandler(commlist_comm3));
        return view;
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//             Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait.......");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr_comm1 = sh.makeServiceCall(url_comm1);
            String jsonStr_comm2 = sh.makeServiceCall(url_comm2);
            String jsonStr_comm3 = sh.makeServiceCall(url_comm3);
            Log.e(TAG, "Response from url: ");

//            if (jsonStr != null) {
            try {
                JSONArray contacts = new JSONArray(jsonStr_comm1);
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    String comm_id = c.getString("committee_id").toUpperCase();
                    String name = c.getString("name");
                    String chamber = c.getString("chamber");
                    if (chamber.equals("house")) {
                        chamber = "House";
                    }
                    else if (chamber.equals("senate")) {
                        chamber = "Senate";
                    }
                    else {
                        chamber = "Joint";
                    }
                    CommItem comm_data = new CommItem(comm_id,name,chamber);
                    commlist_comm1.add(comm_data);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
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
            Log.e(TAG, "Response from url: ");

//            if (jsonStr != null) {
            try {
                JSONArray contacts = new JSONArray(jsonStr_comm2);
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    String comm_id = c.getString("committee_id").toUpperCase();
                    String name = c.getString("name");
                    String chamber = c.getString("chamber");
                    if (chamber.equals("house")) {
                        chamber = "House";
                    }
                    else if (chamber.equals("senate")) {
                        chamber = "Senate";
                    }
                    else {
                        chamber = "Joint";
                    }
                    CommItem comm_data = new CommItem(comm_id,name,chamber);
                    commlist_comm2.add(comm_data);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.e(TAG, "Response from url: ");

//            if (jsonStr != null) {
            try {
                JSONArray contacts = new JSONArray(jsonStr_comm3);
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    String comm_id = c.getString("committee_id");
                    String name = c.getString("name");
                    String chamber = c.getString("chamber");
                    if (chamber.equals("house")) {
                        chamber = "House";
                    }
                    else if (chamber.equals("senate")) {
                        chamber = "Senate";
                    }
                    else {
                        chamber = "Joint";
                    }
                    CommItem comm_data = new CommItem(comm_id,name,chamber);
                    commlist_comm3.add(comm_data);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            Collections.sort(commlist_comm1, new Frag3.CommComparator());
            Collections.sort(commlist_comm2, new Frag3.CommComparator());
            Collections.sort(commlist_comm3, new Frag3.CommComparator());
            ListAdapter adapter_comm1 = new CommAdapter(getActivity(), R.layout.comm_item, commlist_comm1);
            comm1.setAdapter(adapter_comm1);
            ListAdapter adapter_comm2 = new CommAdapter(getActivity(), R.layout.comm_item, commlist_comm2);
            comm2.setAdapter(adapter_comm2);
            ListAdapter adapter_comm3 = new CommAdapter(getActivity(), R.layout.comm_item, commlist_comm3);
            comm3.setAdapter(adapter_comm3);


        }
    }
    public class CommComparator implements Comparator<CommItem> {
        @Override
        public int compare(CommItem o1, CommItem o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
    public class ListClickHandler implements AdapterView.OnItemClickListener {
        private ArrayList<CommItem> curList;
        public ListClickHandler(ArrayList<CommItem> cur) {
            curList = cur;
        }
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
            // TODO Auto-generated method stub


            String a = curList.get(position).comm_id;
//            TextView listText = (TextView) view.findViewById(R.id.listText);
//            String text = listText.getText().toString();
//
//            // create intent to start another activity
//            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//            // add the selected text item to our intent.
//            intent.putExtra("selected-item", text);
//            startActivity(intent);

            Intent intent = new Intent(getActivity(), CommDetail.class);
            intent.putExtra("selected-item", a);
            startActivity(intent);
        }

    }
}
