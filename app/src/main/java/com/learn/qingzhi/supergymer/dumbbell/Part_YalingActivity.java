package com.learn.qingzhi.supergymer.dumbbell;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.learn.qingzhi.supergymer.EquiementActivity;
import com.learn.qingzhi.supergymer.R;

import java.util.ArrayList;
import java.util.List;

import db.DBHandler;
import db.Equipment;

public class Part_YalingActivity extends AppCompatActivity {
    Button[] btnCategory ;

    //private Button myButton4=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.part);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_home:
                                Toast.makeText(getApplicationContext(), "menu_home", Toast.LENGTH_SHORT).show();


                                Intent intent =new Intent();
                                intent.setClass(Part_YalingActivity.this,EquiementActivity.class);
                                Part_YalingActivity.this.startActivity(intent);


                            case R.id.menu_scan:
                                Toast.makeText(getApplicationContext(),"menu_scan",Toast.LENGTH_SHORT).show();
                            case R.id.menu_user:
                                Toast.makeText(getApplicationContext(),"menu_user",Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
        DBHandler dbHandler=new DBHandler(this);
        dbHandler.initDb(this);
        Intent intent = getIntent();
        //Bundle extras=intent.getExtras();
        String equipment=intent.getStringExtra("equipment");
        List<Equipment> equipmentList=dbHandler.getEquipmentByName(equipment);

        btnCategory = new Button[equipmentList.size()];

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout3);
        layout.setOrientation(LinearLayout.VERTICAL);  //Can also be done in xml by android:orientation="vertical"

        for (int i = 0; i < equipmentList.size(); i++) {
            LinearLayout row = new LinearLayout(this);
            row.setLayoutParams(new AppBarLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));


            btnCategory[i] = new Button(this);
            btnCategory[i].setLayoutParams(new AppBarLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            btnCategory[i].setText(equipmentList.get(i).get_part());
            btnCategory[i].setId(i);
            btnCategory[i].setOnClickListener(new Part_YalingActivity.MyButtionListener());
            row.addView(btnCategory[i]);
            layout.addView(row);
        }
    }
    class MyButtionListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent =new Intent();

            intent.setClass(Part_YalingActivity.this,Arm_yalingActivity.class);
           // intent.putExtra("part",part );

            Part_YalingActivity.this.startActivity(intent);

        }
    }

}
