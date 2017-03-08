package com.learn.qingzhi.supergymer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;

import db.DBHandler;
import db.History;

/**
 * Created by wangxi on 08/03/2017.
 */

public class UserHistory extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_history);

        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int userid=Integer.parseInt(sharedPreferences.getString("UserId","0"));
        userid=1;
        DBHandler db=new DBHandler(this);
        List<History> historyList=db.getAllHistory(userid);

        DataPoint[] calories=new DataPoint[historyList.size()];
        DataPoint[] durations=new DataPoint[historyList.size()];
        String[] xlabels=new String[historyList.size()];
        for(int i=0;i<historyList.size();i++){
            calories[i]=new DataPoint(i,historyList.get(i).get_calories());
            durations[i]=new DataPoint(i,historyList.get(i).get_duration());
            xlabels[i]=historyList.get(i).get_date().toString().substring(4,8);
            Log.d("history: ", xlabels[i]);
        }

        GraphView graph = (GraphView) findViewById(R.id.history_graph);

        LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(calories);
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(durations);


        graph.addSeries(series1);
        graph.addSeries(series2);


        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(xlabels);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
    }
}
