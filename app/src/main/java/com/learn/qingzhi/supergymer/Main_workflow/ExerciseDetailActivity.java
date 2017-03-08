package com.learn.qingzhi.supergymer.Main_workflow;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.learn.qingzhi.supergymer.R;

import db.DBHandler;

public class ExerciseDetailActivity extends AppCompatActivity {
    private VideoView video1=null;
    private TextView Text1=null;
    private Button Button1=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arm_yaling);
        DBHandler dbHandler=new DBHandler(this);
        dbHandler.initDb(this);

        Intent intent=getIntent();
        //String equipment=intent.getStringExtra("equipment");

        //List<Equipment> equipmentList=dbHandler.getEquipmentByName(equipment);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_home:
                                Toast.makeText(getApplicationContext(), "menu_home", Toast.LENGTH_SHORT).show();


                                Intent intent =new Intent();
                                intent.setClass(ExerciseDetailActivity.this,EquipmentActivity.class);
                                ExerciseDetailActivity.this.startActivity(intent);


                            case R.id.menu_scan:
                                Toast.makeText(getApplicationContext(),"menu_scan",Toast.LENGTH_SHORT).show();
                            case R.id.menu_user:
                                Toast.makeText(getApplicationContext(),"menu_user",Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
        Button1=(Button)findViewById(R.id.button);
        Text1=(TextView)findViewById(R.id.textView);
      //  Text1.setText(equipmentList.get(equipment).get_introduction());
        video1=(VideoView)findViewById(R.id.videoView);
    }
}
