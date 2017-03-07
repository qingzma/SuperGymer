package com.learn.qingzhi.supergymer;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.learn.qingzhi.supergymer.dumbbell.Part_YalingActivity;

import java.util.List;

import db.DBHandler;


public class EquiementActivity extends AppCompatActivity {
    //DBHandler mDBHandler=new DBHandler(this);
    Button[] btnCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBHandler dbHandler=new DBHandler(this);
        dbHandler.initDb(this);

        List<String> btnNames=dbHandler.getEquipmentNames();

        btnCategory = new Button[btnNames.size()];
        setContentView(R.layout.activity_equiement);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout2);
        layout.setOrientation(LinearLayout.VERTICAL);  //Can also be done in xml by android:orientation="vertical"

        for (int i = 0; i < btnNames.size(); i++) {
            LinearLayout row = new LinearLayout(this);
            row.setLayoutParams(new AppBarLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));


            btnCategory[i] = new Button(this);
            btnCategory[i].setLayoutParams(new AppBarLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            btnCategory[i].setText("Button " + i);
            btnCategory[i].setId(i);
            btnCategory[i].setOnClickListener(new EquiementActivity.MyButtionListener());
                row.addView(btnCategory[i]);
                layout.addView(row);


        }

    }
    class MyButtionListener implements View.OnClickListener{

        public void onClick(View v){

                Intent intent =new Intent();
                intent.setClass(EquiementActivity.this,Part_YalingActivity.class);
                EquiementActivity.this.startActivity(intent);

        }
    }

 }









