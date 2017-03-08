package db;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by qingzhi on 07/03/2017.
 */

public class MyDateFormat {
    public static SimpleDateFormat Date2String = new SimpleDateFormat("yyyyMMdd");
    public static Date toDate(String str){
        Date date=new Date();
        //Log.d("date: ",str.substring(0,4) );
        //Log.d("date: ",str.substring(4,6) );
        //Log.d("date: ",str.substring(6,8) );
        date.setYear(Integer.parseInt(str.substring(0,4))-1900);
        date.setMonth(Integer.parseInt(str.substring(4,6))-1);
        date.setDate(Integer.parseInt(str.substring(6,8)));

        return date;
    }
}
