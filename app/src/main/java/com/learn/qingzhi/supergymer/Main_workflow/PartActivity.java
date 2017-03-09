package com.learn.qingzhi.supergymer.Main_workflow;

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

import com.learn.qingzhi.supergymer.R;
import com.learn.qingzhi.supergymer.Scanner;
import com.learn.qingzhi.supergymer.UserHistory;

import java.util.List;

import db.DBHandler;
import db.Equipment;
/**
 * Created by Langsha Liu on 08/03/2017.
 */

public class PartActivity extends AppCompatActivity {
    Button[] btnCategory ;



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
                                //Toast.makeText(getApplicationContext(), "menu_home", Toast.LENGTH_SHORT).show();


                                Intent intent =new Intent();
                                intent.setClass(PartActivity.this,EquipmentActivity.class);
                                PartActivity.this.startActivity(intent);
                                PartActivity.this.finish();
                                break;

                            case R.id.menu_scan:
                                //Toast.makeText(getApplicationContext(),"menu_scan",Toast.LENGTH_SHORT).show();
                                Intent intent2 =new Intent();
                                intent2.setClass(PartActivity.this,Scanner.class);
                                PartActivity.this.startActivity(intent2);
                                PartActivity.this.finish();
                                break;

                            case R.id.menu_user:
                               // Toast.makeText(getApplicationContext(),"menu_user",Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent();
                                intent1.setClass(PartActivity.this, UserHistory.class);
                                PartActivity.this.startActivity(intent1);
                                PartActivity.this.finish();
                                break;
                        }
                        return true;
                    }
                });
        DBHandler dbHandler=new DBHandler(this);
        dbHandler.initDb(this);
        Intent intent = getIntent();

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
            btnCategory[i].setOnClickListener(new PartActivity.MyButtionListener(equipmentList.get(i).get_equipment_name()));
            row.addView(btnCategory[i]);
            layout.addView(row);
        }
        //String a="Dumbbell";
        if(equipment.equals("Dumbbell")){
            btnCategory[0].setCompoundDrawablesWithIntrinsicBounds(R.drawable.arm1, 0,0 , 0);
            btnCategory[1].setCompoundDrawablesWithIntrinsicBounds(R.drawable.chest1, 0, 0, 0);
            btnCategory[2].setCompoundDrawablesWithIntrinsicBounds(R.drawable.abs1, 0, 0, 0);
        }else if(equipment.equals("Yoga mat")){
            btnCategory[0].setCompoundDrawablesWithIntrinsicBounds(R.drawable.abs1, 0,0 , 0);
            btnCategory[1].setCompoundDrawablesWithIntrinsicBounds(R.drawable.leg1, 0, 0, 0);
        }else if(equipment.equals("Treadmill")){
            btnCategory[0].setCompoundDrawablesWithIntrinsicBounds(R.drawable.reduce_fat, 0,0 , 0);
        }
    }
    class MyButtionListener implements View.OnClickListener{
        String equipment = null;
        public MyButtionListener(String equipment){

            this.equipment = equipment;
        }
        @Override
        public void onClick(View v) {
            Intent intent =new Intent();
            v.getId();
            intent.setClass(PartActivity.this,ExerciseDetailActivity.class);

            intent.putExtra("id",v.getId());
            intent.putExtra("equipment",equipment);

            PartActivity.this.startActivity(intent);

        }
    }

}
