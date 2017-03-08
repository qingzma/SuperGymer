package com.learn.qingzhi.supergymer.Main_workflow;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arm_yaling);

        DBHandler dbHandler = new DBHandler(this);


        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        String equipment = intent.getStringExtra("equipment");
        Equipment equip = dbHandler.getEquipmentByName(equipment).get(id);
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


        Text1 = (TextView) findViewById(R.id.textView);
        Text1.setText(equip.get_introduction());
        //video1 = (VideoView) findViewById(R.id.videoView);
        youTubeView=(YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(Config.YOUTUBE_API_KEY,this);
        togglebutton = (ToggleButton) findViewById(R.id.toggleButton);
        togglebutton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                if (togglebutton.isChecked()) {
                    Toast.makeText(ExerciseDetailActivity.this, "begin", Toast.LENGTH_SHORT).show();
                }

                else {
                    Toast.makeText(ExerciseDetailActivity.this, "end", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
            if (!b) {
                youTubePlayer.cueVideo("fhWaJi1Hsfo"); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
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





