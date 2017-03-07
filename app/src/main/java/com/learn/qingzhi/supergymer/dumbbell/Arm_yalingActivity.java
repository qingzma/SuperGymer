package com.learn.qingzhi.supergymer.dumbbell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import com.learn.qingzhi.supergymer.R;

public class Arm_yalingActivity extends AppCompatActivity {
    private VideoView video1=null;
    private TextView Text1=null;
    private Button Button1=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arm_yaling);
        Intent intent=getIntent();
        Button1=(Button)findViewById(R.id.button);
        Text1=(TextView)findViewById(R.id.textView);
        video1=(VideoView)findViewById(R.id.videoView);
    }
}
