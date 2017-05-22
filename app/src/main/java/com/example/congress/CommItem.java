package com.example.congress;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.congress.R.id.bill_id;
import static com.example.congress.R.id.firstline;
import static com.example.congress.R.id.lastline;
import static com.example.congress.R.id.party;
import static com.example.congress.R.id.state;

/**
 * Created by Hank on 12/1/2016.
 */

public class CommItem {

    public String comm_id;
    public String name;
    public String chamber;



    public CommItem(){
        super();
    }

    public CommItem( String comm_id, String name,String chamber) {
        super();
        this.comm_id = comm_id;
        this.name = name;
        this.chamber = chamber;

    }
    public String getName() {
        return name;
    }

}
