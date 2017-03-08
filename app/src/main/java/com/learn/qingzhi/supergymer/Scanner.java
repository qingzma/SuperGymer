package com.learn.qingzhi.supergymer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.learn.qingzhi.supergymer.Main_workflow.EquipmentActivity;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

/**
 * Created by wangxi on 08/03/2017.
 */

public class Scanner extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanner);
        PulsatorLayout pulsator = (PulsatorLayout) findViewById(R.id.pulsator);
        pulsator.start();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_home:
                                Intent home = new Intent(Scanner.this,EquipmentActivity.class);
                                startActivity(home);
                                break;
                            case R.id.menu_scan:
                                //Toast.makeText(getApplicationContext(),"menu_scan",Toast.LENGTH_SHORT).show();
                                Intent scanner = new Intent(Scanner.this,Scanner.class);
                                startActivity(scanner);
                                break;
                            case R.id.menu_user:
                                Intent intent_user = new Intent(Scanner.this,UserHistory.class);
                                startActivity(intent_user);
                                break;
                        }
                        return true;
                    }
                });
    }
}
