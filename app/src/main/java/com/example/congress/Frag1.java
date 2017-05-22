package com.example.congress;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static android.icu.text.RelativeDateTimeFormatter.Direction.THIS;
import static com.example.congress.R.id.tab1;
import static com.example.congress.R.id.tabHost;
//import static com.example.congress.R.id.textView;
//import static com.example.congress.R.id.mobile;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * Created by Hank on 11/22/2016.
 */

public class Frag1 extends Fragment {
    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView leg1;
    private ListView leg2;
    private ListView leg3;
    // URL to get contacts JSON
//    private static String url = "http://api.androidhive.info/contacts/";
    private static String url_leg1 = "http://hank.sgpmuxr6xj.us-west-1.elasticbeanstalk.com/temp.php?text1=1";
    private static String url_leg2 = "http://hank.sgpmuxr6xj.us-west-1.elasticbeanstalk.com/temp.php?text1=2";
    private static String url_leg3 = "http://hank.sgpmuxr6xj.us-west-1.elasticbeanstalk.com/temp.php?text1=3";

    ArrayList<LegItem> leglist_leg1;
    ArrayList<LegItem> leglist_leg2;
    ArrayList<LegItem> leglist_leg3;
    public Frag1() {

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
        View view=inflater.inflate(R.layout.legislators_main, container, false);

        TabHost host = (TabHost)view.findViewById(tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(tab1);
        spec.setIndicator("BY STATE");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("HOUSE");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator("SENATE");
        host.addTab(spec);

        TabWidget widget = host.getTabWidget();
        for(int i = 0; i < widget.getChildCount(); i++) {
            View v = widget.getChildAt(i);
            // Look for the title view to ensure this is an indicator and not a divider.
            TextView tv = (TextView)v.findViewById(android.R.id.title);
            if(tv == null) {
                continue;
            }
            v.setBackgroundColor(Color.WHITE);
        }


        leglist_leg1 = new ArrayList<>();
        leglist_leg2 = new ArrayList<>();
        leglist_leg3 = new ArrayList<>();
        leg1 = (ListView)view.findViewById(R.id.list_leg1);
        leg2 = (ListView)view.findViewById(R.id.list_leg2);
        leg3 = (ListView)view.findViewById(R.id.list_leg3);
        new GetContacts().execute();
        leg1.setOnItemClickListener(new ListClickHandler(leglist_leg1));
        leg2.setOnItemClickListener(new ListClickHandler(leglist_leg2));
        leg3.setOnItemClickListener(new ListClickHandler(leglist_leg3));
        return view;
    }
    private Map<String, Integer> mapIndex;
    private void getIndexList(ArrayList<LegItem> legitem) {
        mapIndex = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < legitem.size(); i++) {
            String s = legitem.get(i).state;
            String index = s.substring(0, 1);
            if (mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }
    }
    private Map<String, Integer> mapIndex2;
    private void getIndexList2(ArrayList<LegItem> legitem) {
        mapIndex = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < legitem.size(); i++) {
            String s = legitem.get(i).last_name;
            String index = s.substring(0, 1);
            if (mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }
    }
    private Map<String, Integer> mapIndex3;
    private void getIndexList3(ArrayList<LegItem> legitem) {
        mapIndex = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < legitem.size(); i++) {
            String s = legitem.get(i).last_name;
            String index = s.substring(0, 1);
            if (mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }
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
            String jsonStr_leg1 = sh.makeServiceCall(url_leg1);

            String jsonStr_leg2 = sh.makeServiceCall(url_leg2);
            String jsonStr_leg3 = sh.makeServiceCall(url_leg3);

            Log.e(TAG, "Response from url: ");

//            if (jsonStr != null) {
                try {
                    JSONArray contacts = new JSONArray(jsonStr_leg1);
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String bioguide_id = c.getString("bioguide_id");
                        String first_name = c.getString("first_name");
                        String last_name = c.getString("last_name");
                        String party = c.getString("party");
                        String state = c.getString("state_name");
                        String state_bre = c.getString("state");
                        String district = c.getString("district");
                        if (district == "null") {
                            district = "0";
                        }
                        String firstline = last_name + ", " + first_name;
                        String lastline = "(" + party + ")" + state + "-District " + district;
                        LegItem leg_data = new LegItem(firstline,lastline,bioguide_id,state_bre,last_name);
                        leglist_leg1.add(leg_data);
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
//                    JSONObject jsonObj = new JSONObject(jsonStr);
//
//                    // Getting JSON Array node
//                    JSONArray contacts = jsonObj.getJSONArray("contacts");
                JSONArray contacts = new JSONArray(jsonStr_leg2);

                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    String bioguide_id = c.getString("bioguide_id");
                    String first_name = c.getString("first_name");
                    String last_name = c.getString("last_name");
                    String party = c.getString("party");
                    String state = c.getString("state_name");
                    String state_bre = c.getString("state");
                    String district = c.getString("district");
                    if (district == "null") {
                        district = "0";
                    }
                    String firstline = last_name + ", " + first_name;
                    String lastline = "(" + party + ")" + state + "-District " + district;
//                        String address = c.getString("address");
//                        String gender = c.getString("gender");

                    // Phone node is JSON Object
//                        JSONObject phone = c.getJSONObject("phone");
//                        String mobile = c.getString("govtrack_id");
//                        String home = phone.getString("home");
//                        String office = phone.getString("office");

                    // tmp hash map for single contact
//                        HashMap<String, String> contact = new HashMap<>();

                    // adding each child node to HashMap key => value
//                        contact.put("id", id);
//                        contact.put("name", name);
//                        contact.put("email", email);
//                        contact.put("mobile", mobile);

                    // adding contact to contact list
//                        contactList.add(contact);
                    LegItem leg_data = new LegItem(firstline,lastline,bioguide_id,state_bre,last_name);
                    leglist_leg2.add(leg_data);

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
//                    JSONObject jsonObj = new JSONObject(jsonStr);
//
//                    // Getting JSON Array node
//                    JSONArray contacts = jsonObj.getJSONArray("contacts");
                JSONArray contacts = new JSONArray(jsonStr_leg3);

                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    String bioguide_id = c.getString("bioguide_id");
                    String first_name = c.getString("first_name");
                    String last_name = c.getString("last_name");
                    String party = c.getString("party");
                    String state = c.getString("state_name");
                    String state_bre = c.getString("state");
                    String district = c.getString("district");
                    if (district == "null") {
                        district = "0";
                    }
                    String firstline = last_name + ", " + first_name;
                    String lastline = "(" + party + ")" + state + "-District " + district;
//                        String address = c.getString("address");
//                        String gender = c.getString("gender");

                    // Phone node is JSON Object
//                        JSONObject phone = c.getJSONObject("phone");
//                        String mobile = c.getString("govtrack_id");
//                        String home = phone.getString("home");
//                        String office = phone.getString("office");

                    // tmp hash map for single contact
//                        HashMap<String, String> contact = new HashMap<>();

                    // adding each child node to HashMap key => value
//                        contact.put("id", id);
//                        contact.put("name", name);
//                        contact.put("email", email);
//                        contact.put("mobile", mobile);

                    // adding contact to contact list
//                        contactList.add(contact);
                    LegItem leg_data = new LegItem(firstline,lastline,bioguide_id,state_bre,last_name);
                    leglist_leg3.add(leg_data);

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

            Collections.sort(leglist_leg1, new LegComparator());
            Collections.sort(leglist_leg2, new LegComparator2());
            Collections.sort(leglist_leg3, new LegComparator2());
            ListAdapter adapter_leg1 = new LegAdapter(getActivity(),R.layout.list_item,leglist_leg1);
            leg1.setAdapter(adapter_leg1);
            ListAdapter adapter_leg2 = new LegAdapter(getActivity(),R.layout.list_item,leglist_leg2);
            leg2.setAdapter(adapter_leg2);
            ListAdapter adapter_leg3 = new LegAdapter(getActivity(),R.layout.list_item,leglist_leg3);
            leg3.setAdapter(adapter_leg3);

            getIndexList(leglist_leg1);
            LinearLayout indexLayout = (LinearLayout) getView().findViewById(R.id.side_index);
            TextView textView1;
            List<String> indexList = new ArrayList<>(mapIndex.keySet());
            for (String index : indexList) {
                textView1 = (TextView) getActivity().getLayoutInflater().inflate(
                        R.layout.side_index_item, null);
                textView1.setText(index);
                textView1.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view) {
                        TextView selectedIndex = (TextView) view;
                        leg1.setSelection(mapIndex.get(selectedIndex.getText()));
                    }

                });
                indexLayout.addView(textView1);
            }
            getIndexList2(leglist_leg2);
            LinearLayout indexLayout2 = (LinearLayout) getView().findViewById(R.id.side_index2);
            TextView textView2;
            List<String> indexList2 = new ArrayList<>(mapIndex.keySet());
            for (String index : indexList2) {
                textView2 = (TextView) getActivity().getLayoutInflater().inflate(
                        R.layout.side_index_item, null);
                textView2.setText(index);
                textView2.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view) {
                        TextView selectedIndex = (TextView) view;
                        leg2.setSelection(mapIndex.get(selectedIndex.getText()));
                    }
                });
                indexLayout2.addView(textView2);
            }
            getIndexList3(leglist_leg3);
            LinearLayout indexLayout3 = (LinearLayout) getView().findViewById(R.id.side_index3);
            TextView textView3;
            List<String> indexList3 = new ArrayList<>(mapIndex.keySet());
            for (String index : indexList3) {
                textView3 = (TextView) getActivity().getLayoutInflater().inflate(
                        R.layout.side_index_item, null);
                textView3.setText(index);
                textView3.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view) {
                        TextView selectedIndex = (TextView) view;
                        leg3.setSelection(mapIndex.get(selectedIndex.getText()));
                    }

                });
                indexLayout3.addView(textView3);
            }
        }
    }




    public class LegComparator implements Comparator<LegItem> {

        @Override
        public int compare(LegItem o1, LegItem o2) {
            int value1 = o1.getState().compareTo(o2.getState());
            if (value1 == 0) {
                return o1.getLastname().compareTo(o2.getLastname());
            } else {
                return value1;
            }
        }
    }
    public class LegComparator2 implements Comparator<LegItem> {

        @Override
        public int compare(LegItem o1, LegItem o2) {
            return o1.getLastname().compareTo(o2.getLastname());
        }
    }



    public class ListClickHandler implements AdapterView.OnItemClickListener {
        private ArrayList<LegItem> curList;
        public ListClickHandler(ArrayList<LegItem> cur) {
            curList = cur;
        }
        @Override

        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
            // TODO Auto-generated method stub

            String a = curList.get(position).bioguide_id;
//            TextView listText = (TextView) view.findViewById(R.id.listText);
//            String text = listText.getText().toString();
//
//            // create intent to start another activity
//            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//            // add the selected text item to our intent.
//            intent.putExtra("selected-item", text);
//            startActivity(intent);

            Intent intent = new Intent(getActivity(), LegDetail.class);
            intent.putExtra("selected-item", a);
            startActivity(intent);
        }

    }
}


