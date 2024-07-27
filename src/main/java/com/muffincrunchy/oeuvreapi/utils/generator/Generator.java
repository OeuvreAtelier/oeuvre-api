package com.muffincrunchy.oeuvreapi.utils.generator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Generator {

    public static String InvoiceGenerator() {
        String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date()).replace("/", "");
        String time = System.currentTimeMillis() + "";
        return  "OVR/" + date + "/JKT/" + time;
    }
}
