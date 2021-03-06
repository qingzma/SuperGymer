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

import static com.learn.qingzhi.supergymer.R.drawable.btdevice_dumbbell;
/**
 * Created by Langsha Liu on 08/03/2017.
 */

public class EquipmentActivity extends AppCompatActivity {

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
            btnCategory[i].setLayoutParams(new AppBarLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            btnCategory[i].setText(btnNames.get(i));



            btnCategory[i].setOnClickListener(new EquipmentActivity.MyButtionListener(btnNames.get(i)));
                row.addView(btnCategory[i]);
                layout.addView(row);


        }

        btnCategory[0].setCompoundDrawablesWithIntrinsicBounds(R.drawable.dumbbell, 0,0 , 0);
        btnCategory[1].setCompoundDrawablesWithIntrinsicBounds(R.drawable.yoga_mat, 0, 0, 0);
        btnCategory[2].setCompoundDrawablesWithIntrinsicBounds(R.drawable.treadmill, 0, 0, 0);



        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_home:
                               // Toast.makeText(getApplicationContext(), "menu_home", Toast.LENGTH_SHORT).show();

                                break;
                            case R.id.menu_scan:
                                //Toast.makeText(getApplicationContext(),"menu_scan",Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent();
                                intent.setClass(EquipmentActivity.this,Scanner.class);
                                EquipmentActivity.this.startActivity(intent);
                                EquipmentActivity.this.finish();
                                break;

                            case R.id.menu_user:
                              //  Toast.makeText(getApplicationContext(),"menu_user",Toast.LENGTH_SHORT).show();
                                Intent intent1 =new Intent();
                                intent1.setClass(EquipmentActivity.this,UserHistory.class);
                                EquipmentActivity.this.startActivity(intent1);
                                EquipmentActivity.this.finish();
                                break;
                        }
                        return true;
                    }
                });

    }
    class MyButtionListener implements View.OnClickListener{
        String equipment = null;
        public MyButtionListener(String equipment){

            this.equipment = equipment;
        }
        public void onClick(View v){

                Intent intent =new Intent();
                intent.setClass(EquipmentActivity.this,PartActivity.class);


                    intent.putExtra("equipment",equipment );

            EquipmentActivity.this.startActivity(intent);

        }
    }

 }









