package com.example.congress;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.example.congress.R.id.tab1;
import static com.example.congress.R.id.tabHost;

/**
 * Created by Hank on 11/30/2016.
 */

public class Frag4 extends Fragment {
    private ListView leg1;
    private ListView bill1;
    private ListView comm1;
    ArrayList<LegItem> leglist_leg1;
    ArrayList<BillItem> bill_bill1;
    ArrayList<CommItem> comm_comm1;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.favourite_main, container, false);


        TabHost host = (TabHost) view.findViewById(tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(tab1);
        spec.setIndicator("LEGISLATOS");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("BILLS");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator("COMMITEES");
        host.addTab(spec);



        leglist_leg1 = ((Myglobal) getActivity().getApplication()).getLeg();
        bill_bill1 = ((Myglobal) getActivity().getApplication()).getBill();
        comm_comm1 = ((Myglobal) getActivity().getApplication()).getComm();
        leg1 = (ListView)view.findViewById(R.id.list_leg1);
        bill1 = (ListView)view.findViewById(R.id.list_bill1);
        comm1 = (ListView)view.findViewById(R.id.list_comm1);

        Collections.sort(leglist_leg1, new Frag4.LegComparator());
        Collections.sort(bill_bill1, new Frag4.BillComparator());
        Collections.sort(comm_comm1, new Frag4.CommComparator());
        ListAdapter adapter_leg1 = new LegAdapter(getActivity(),R.layout.list_item,leglist_leg1);
        leg1.setAdapter(null);
        leg1.setAdapter(adapter_leg1);

        ListAdapter adapter_bill1 = new BillAdapter(getActivity(),R.layout.bill_item,bill_bill1);
        bill1.setAdapter(null);
        bill1.setAdapter(adapter_bill1);
        ListAdapter adapter_comm1 = new CommAdapter(getActivity(),R.layout.comm_item,comm_comm1);
        comm1.setAdapter(null);
        comm1.setAdapter(adapter_leg1);



        getIndexList(leglist_leg1);
        LinearLayout indexLayout = (LinearLayout) view.findViewById(R.id.side_index);
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
        leg1.setOnItemClickListener(new Frag4.ListClickHandler1(leglist_leg1));
        bill1.setOnItemClickListener(new Frag4.ListClickHandler2(bill_bill1));
        comm1.setOnItemClickListener(new Frag4.ListClickHandler3(comm_comm1));
        return view;
    }
    private class LegComparator implements Comparator<LegItem> {

        @Override
        public int compare(LegItem o1, LegItem o2) {
            return o1.getLastname().compareTo(o2.getLastname());
        }
    }
    private class BillComparator implements Comparator<BillItem> {

        @Override
        public int compare(BillItem o1, BillItem o2) {
            return o2.getDate().compareTo(o1.getDate());
        }
    }
    private class CommComparator implements Comparator<CommItem> {

        @Override
        public int compare(CommItem o1, CommItem o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }



    private Map<String, Integer> mapIndex;
    private void getIndexList(ArrayList<LegItem> legitem) {
        mapIndex = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < legitem.size(); i++) {
            String s = legitem.get(i).last_name;
            String index = s.substring(0, 1);
            if (mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }
    }
    public class ListClickHandler1 implements AdapterView.OnItemClickListener {
        private ArrayList<LegItem> curList;
        public ListClickHandler1(ArrayList<LegItem> cur) {
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
    public class ListClickHandler2 implements AdapterView.OnItemClickListener {
        private ArrayList<BillItem> curList;
        public ListClickHandler2(ArrayList<BillItem> cur) {
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
    public class ListClickHandler3 implements AdapterView.OnItemClickListener {
        private ArrayList<CommItem> curList;
        public ListClickHandler3(ArrayList<CommItem> cur) {
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