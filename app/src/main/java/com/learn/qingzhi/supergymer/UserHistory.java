package com.learn.qingzhi.supergymer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.learn.qingzhi.supergymer.Main_workflow.EquipmentActivity;

import java.util.List;

import db.DBHandler;
import db.Equipment;
import db.History;
import db.User;

/**
 * Created by wangxi on 08/03/2017.
 */

public class UserHistory extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_history);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_home:
                                Toast.makeText(getApplicationContext(), "menu_home", Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent();
                                intent.setClass(UserHistory.this, Equipment.class);
                                UserHistory.this.startActivity(intent);
                            case R.id.menu_scan:
                                Toast.makeText(getApplicationContext(),"menu_scan",Toast.LENGTH_SHORT).show();
                                Intent intent1 =new Intent();
                                intent1.setClass(UserHistory.this,Scanner.class);
                                UserHistory.this.startActivity(intent1);

                            case R.id.menu_user:
                                Toast.makeText(getApplicationContext(),"menu_user",Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });



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
