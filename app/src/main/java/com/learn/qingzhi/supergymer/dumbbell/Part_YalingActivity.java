package com.learn.qingzhi.supergymer.dumbbell;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.learn.qingzhi.supergymer.R;

public class Part_YalingActivity extends AppCompatActivity {
    Button[] btnCategory = new Button[2];
    //private Button myButton4=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.part);
        Intent intent = getIntent();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout3);
        layout.setOrientation(LinearLayout.VERTICAL);  //Can also be done in xml by android:orientation="vertical"
        //Button[] btnCategory = new Button[5];
        for (int i = 0; i < 2; i++) {
            LinearLayout row = new LinearLayout(this);
            row.setLayoutParams(new AppBarLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));


            btnCategory[i] = new Button(this);
            btnCategory[i].setLayoutParams(new AppBarLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            btnCategory[i].setText("Button " + i);
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
            Part_YalingActivity.this.startActivity(intent);
        }
    }

}
