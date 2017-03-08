package com.learn.qingzhi.supergymer.Main_workflow;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.learn.qingzhi.supergymer.R;

import java.net.URL;
import java.util.List;

import db.DBHandler;
import db.Equipment;

import static android.support.v7.appcompat.R.id.text;

public class ExerciseDetailActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{ //extends AppCompatActivity {
    private VideoView video1=null;
    private TextView Text1=null;
    private ToggleButton togglebutton;
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    //private ToggleButton togglebutton;
    private Button button=null;
    private Chronometer time;
    Equipment equip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.arm_yaling);

        DBHandler dbHandler = new DBHandler(this);


        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        String equipment = intent.getStringExtra("equipment");
        equip = dbHandler.getEquipmentByName(equipment).get(id);
        //URL url=equip.get_video_url();

        List<Equipment> equipmentList = dbHandler.getEquipmentByName(equipment);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_home:
                                Toast.makeText(getApplicationContext(), "menu_home", Toast.LENGTH_SHORT).show();


                                Intent intent = new Intent();
                                intent.setClass(ExerciseDetailActivity.this, EquipmentActivity.class);
                                ExerciseDetailActivity.this.startActivity(intent);


                            case R.id.menu_scan:
                                Toast.makeText(getApplicationContext(), "menu_scan", Toast.LENGTH_SHORT).show();
                            case R.id.menu_user:
                                Toast.makeText(getApplicationContext(), "menu_user", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
        button = (Button) findViewById(R.id.button);
        button.setTag(1);
        button.setText("Begin");
        time = (Chronometer) findViewById(R.id.chronometer2);



        Text1 = (TextView) findViewById(R.id.textView);
        Text1.setText(equip.get_introduction());
        //video1 = (VideoView) findViewById(R.id.videoView);
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);


        //video1 = (VideoView) findViewById(R.id.videoView);
        time.setFormat("Exercise time:%s");
        time.setBase(SystemClock.elapsedRealtime());

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int status = (Integer) v.getTag();
                // time.setBase(SystemClock.elapsedRealtime());
                long timeElapsed = SystemClock.elapsedRealtime() - time.getBase();
                if (status == 1) {
                    //long timeElapsed = SystemClock.elapsedRealtime() - time.getBase();
                    time.setBase(SystemClock.elapsedRealtime());
                    time.start();
                    button.setText("End");
                    v.setTag(0); //pause
                } else {
                    button.setText("Begin");

                    time.stop();
                   // long timeElapsed = SystemClock.elapsedRealtime() - time.getBase();

                    //v.setTag(1); //pause
                    Log.d(null,"Was: "+timeElapsed);
                }
               // Log.d(null,"Was: "+timeElapsed);
            }

        });


    }








    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.cueVideo(equip.get_video_url()); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), youTubeInitializationResult.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }
}





