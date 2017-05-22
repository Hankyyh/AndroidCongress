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

public class Frag2 extends Fragment {
    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView bill1;
    private ListView bill2;

    // URL to get contacts JSON
//    private static String url = "http://api.androidhive.info/contacts/";
    private static String url_bill1 = "http://hank.sgpmuxr6xj.us-west-1.elasticbeanstalk.com/temp.php?text2=1";
    private static String url_bill2 = "http://hank.sgpmuxr6xj.us-west-1.elasticbeanstalk.com/temp.php?text2=2";


    ArrayList<BillItem> billlist_bill1;
    ArrayList<BillItem> billlist_bill2;

    public Frag2() {

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
        View view = inflater.inflate(R.layout.bill_main, container, false);

        TabHost host = (TabHost) view.findViewById(tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(tab1);
        spec.setIndicator("ACTIVE BILLS");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("NEW BILLS");
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


        billlist_bill1 = new ArrayList<>();
        billlist_bill2 = new ArrayList<>();

        bill1 = (ListView) view.findViewById(R.id.list_bill1);
        bill2 = (ListView) view.findViewById(R.id.list_bill2);

        new Frag2.GetContacts().execute();
        bill1.setOnItemClickListener(new Frag2.ListClickHandler(billlist_bill1));
        bill2.setOnItemClickListener(new Frag2.ListClickHandler(billlist_bill2));

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
            String jsonStr_bill1 = sh.makeServiceCall(url_bill1);
            String jsonStr_bill2 = sh.makeServiceCall(url_bill2);

            Log.e(TAG, "Response from url: ");

//            if (jsonStr != null) {
            try {
                JSONArray contacts = new JSONArray(jsonStr_bill1);
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    String bill_id = c.getString("bill_id").toUpperCase();
                    String title = c.getString("official_title");
                    String introduce = c.getString("introduced_on");

                    BillItem bill_data = new BillItem(bill_id,title,introduce);
                    billlist_bill1.add(bill_data);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
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
                JSONArray contacts = new JSONArray(jsonStr_bill2);
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    String bill_id = c.getString("bill_id").toUpperCase();
                    String title = c.getString("official_title");
                    String introduce = c.getString("introduced_on");

                    BillItem bill_data = new BillItem(bill_id,title,introduce);
                    billlist_bill2.add(bill_data);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
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

            Collections.sort(billlist_bill1, new Frag2.BillComparator());
            Collections.sort(billlist_bill2, new Frag2.BillComparator());

            ListAdapter adapter_bill1 = new BillAdapter(getActivity(), R.layout.bill_item, billlist_bill1);
            bill1.setAdapter(adapter_bill1);
            ListAdapter adapter_bill2 = new BillAdapter(getActivity(), R.layout.bill_item, billlist_bill2);
            bill2.setAdapter(adapter_bill2);



        }
    }
    public class BillComparator implements Comparator<BillItem> {
        @Override
        public int compare(BillItem o1, BillItem o2) {
            return o2.getDate().compareTo(o1.getDate());
        }
    }
    public class ListClickHandler implements AdapterView.OnItemClickListener {
        private ArrayList<BillItem> curList;
        public ListClickHandler(ArrayList<BillItem> cur) {
            curList = cur;
        }
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
            // TODO Auto-generated method stub


            String a = curList.get(position).bill_id;
//            TextView listText = (TextView) view.findViewById(R.id.listText);
//            String text = listText.getText().toString();
//
//            // create intent to start another activity
//            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//            // add the selected text item to our intent.
//            intent.putExtra("selected-item", text);
//            startActivity(intent);

            Intent intent = new Intent(getActivity(), BillDetail.class);
            intent.putExtra("selected-item", a);
            startActivity(intent);
        }

    }
}
