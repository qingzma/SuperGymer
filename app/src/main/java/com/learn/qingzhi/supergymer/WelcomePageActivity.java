package com.learn.qingzhi.supergymer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import db.DBHandler;


/**
 * Created by wangxi on 06/03/2017.
 */

public class WelcomePageActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 4000;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        //Initialize the database
        DBHandler dbHandler = new DBHandler(this);
        dbHandler.initDb(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent homeIntent = new Intent(WelcomePageActivity.this,UserSignUp.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
