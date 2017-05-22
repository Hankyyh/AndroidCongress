package com.example.congress;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.congress.R.id.firstline;
import static com.example.congress.R.id.lastline;
import static com.example.congress.R.id.party;
import static com.example.congress.R.id.state;

/**
 * Created by Hank on 12/1/2016.
 */

public class BillItem {

    public String bill_id;
    public String title;
    public String introduce;



    public BillItem(){
        super();
    }

    public BillItem( String bill_id, String title,String introduce) throws ParseException {
        super();
        this.bill_id = bill_id;
        this.title = title;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat change = new SimpleDateFormat("MMM dd,yyyy");
        Date date = formatter.parse(introduce);
        this.introduce = change.format(date);
    }
    public String getDate() {
        return introduce;
    }

}
