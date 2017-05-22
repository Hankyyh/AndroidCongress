package com.example.congress;

//import static com.example.congress.R.id.email;
//import static com.example.congress.R.id.mobile;

/**
 * Created by Hank on 11/25/2016.
 */

public class LegItem {
    public int icon;
    public String img;
    public String first_name;
    public String last_name;
    public String party;
    public String state;
    public String district;
    public String firstline;
    public String lastline;
    public String bioguide_id;

    public LegItem(){
        super();
    }

    public LegItem( String firstline, String lastline,String bioguide_id ,String state,String last_name) {
        super();
        this.img = img;
        this.firstline = firstline;
        this.lastline = lastline;
        this.bioguide_id = bioguide_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.last_name= last_name;
        this.party = party;
        this.state = state;
//        this.last_name = last_name;
        this.district = district;
    }
    public String getState() {
        return state;
    }
    public String getLastname() {
        return last_name;
    }
}
