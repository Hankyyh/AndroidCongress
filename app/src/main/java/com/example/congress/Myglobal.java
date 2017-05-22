package com.example.congress;

import android.app.Application;

import java.util.ArrayList;

import static com.example.congress.R.layout.bill_item;

/**
 * Created by Hank on 11/30/2016.
 */

public class Myglobal extends Application {
    private String someVariable;
    private ArrayList<LegItem> leg_favor = new ArrayList<LegItem>();
    private ArrayList<BillItem> bill_favor = new ArrayList<>();
    private ArrayList<CommItem> comm_favor = new ArrayList<>();
    public String getSomeVariable() {
        return someVariable;
    }

    public void setSomeVariable(String someVariable) {
        this.someVariable = someVariable;
    }
    public ArrayList<LegItem> getLeg() {
        return leg_favor;
    }
    public void addLeg(LegItem leg_item) {
        leg_favor.add(leg_item);
    }
    public void deleteLeg(LegItem leg_item) {
        for (int i = 0;i<leg_favor.size();i++) {
            if (leg_favor.get(i).bioguide_id.equals(leg_item.bioguide_id)) {
                leg_favor.remove(i);
                return;
            }
        }

    }
    public boolean detectLeg(LegItem leg_item) {
        for (int i = 0;i<leg_favor.size();i++) {
            if (leg_favor.get(i).bioguide_id.equals(leg_item.bioguide_id)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<BillItem> getBill() {
        return bill_favor;
    }
    public void addBill(BillItem bill_item) {
        bill_favor.add(bill_item);
    }
    public void deleteBill(BillItem bill_item) {
        for (int i = 0;i<bill_favor.size();i++) {
            if (bill_favor.get(i).bill_id.equals(bill_item.bill_id)) {
                bill_favor.remove(i);
                return;
            }
        }
    }
    public boolean detectBill(BillItem bill_item) {
        for (int i = 0;i<bill_favor.size();i++) {
            if (bill_favor.get(i).bill_id.equals(bill_item.bill_id)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<CommItem> getComm() {
        return comm_favor;
    }
    public void addComm(CommItem comm_item) {
        comm_favor.add(comm_item);
    }
    public void deleteComm(CommItem comm_item) {
        for (int i = 0;i<comm_favor.size();i++) {
            if (comm_favor.get(i).comm_id.equals(comm_item.comm_id)) {
                comm_favor.remove(i);
                return;
            }
        }
    }
    public boolean detectComm(CommItem comm_item) {
        for (int i = 0;i<comm_favor.size();i++) {
            if (comm_favor.get(i).comm_id.equals(comm_item.comm_id)) {
                return true;
            }
        }
        return false;
    }
}
