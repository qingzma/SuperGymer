package com.learn.qingzhi.supergymer;

//import android.app.AlertDialog;
//import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
                                intent.setClass(UserHistory.this, EquipmentActivity.class);
                                UserHistory.this.startActivity(intent);
                                UserHistory.this.finish();
                                break;
                            case R.id.menu_scan:
                                Toast.makeText(getApplicationContext(),"menu_scan",Toast.LENGTH_SHORT).show();
                                Intent intent1 =new Intent();
                                intent1.setClass(UserHistory.this,Scanner.class);
                                UserHistory.this.startActivity(intent1);
                                UserHistory.this.finish();
                                break;

                            case R.id.menu_user:
                                Toast.makeText(getApplicationContext(),"menu_user",Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;

                    }
                });



        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putString("UserId",Integer.toString(user.get_userId()));
        String userID=sharedPreferences.getString("UserInfo","NoneUser");
        if (userID.equals("NoneUser")){
            AlertDialog.Builder builder = new AlertDialog.Builder(UserHistory.this);
            builder.setMessage("Sign in?");
            builder.setTitle("Hint:");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent =new Intent();
                            intent.setClass(UserHistory.this, UserLogin.class);
                            UserHistory.this.startActivity(intent);
                            UserHistory.this.finish();

                        }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent =new Intent();
                            intent.setClass(UserHistory.this, EquipmentActivity.class);
                            UserHistory.this.startActivity(intent);
                            UserHistory.this.finish();
                        }
            });
            builder.create().show();

        }
        else{
            int userid=Integer.parseInt(sharedPreferences.getString("UserId","0"));
            userid=1;
            DBHandler db=new DBHandler(this);
            List<History> historyList=db.getAllHistory(userid);
            double totalCalories=0;
            double totalTime=0;

            DataPoint[] calories=new DataPoint[historyList.size()];
            DataPoint[] durations=new DataPoint[historyList.size()];
            String[] xlabels=new String[historyList.size()];
            for(int i=0;i<historyList.size();i++){
                calories[i]=new DataPoint(i,historyList.get(i).get_calories());
                durations[i]=new DataPoint(i,historyList.get(i).get_duration());
                xlabels[i]=historyList.get(i).get_date().toString().substring(4,10);
                totalCalories+=historyList.get(i).get_calories();
                totalTime+=historyList.get(i).get_duration();

                Log.d("history: ", xlabels[i]=historyList.get(i).get_date().toString().substring(4,10));
            }


            GraphView graph = (GraphView) findViewById(R.id.history_graph);
            //GraphView graph= new GraphView(this);
            LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(calories);
            LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(durations);
            series1.setColor(Color.RED);
            series2.setColor(Color.BLUE);
            series1.setTitle("calories");
            series2.setTitle("durations");


            graph.addSeries(series1);
            graph.addSeries(series2);




            StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
            staticLabelsFormatter.setHorizontalLabels(xlabels);
            graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);


            LinearLayout linearLayout=(LinearLayout) findViewById(R.id.history_comment);
            linearLayout.setPadding(20,20,20,20);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            TextView textView1=new TextView(this);
            textView1.setPadding(80,10,80,10);
            textView1.setTextColor(Color.RED);
            textView1.setText("Total calories: "+totalCalories+" kJ.");

            TextView textView2=new TextView(this);
            textView2.setPadding(80,10,80,10);
            textView2.setTextColor(Color.BLUE);
            textView2.setText("Total time: "+totalTime+" mins.");


            linearLayout.addView(textView1);
            linearLayout.addView(textView2);

            ImageView imageView=new ImageView(this);
            imageView.setImageResource(R.drawable.fight);
            linearLayout.addView(imageView);




        }
    }
}
