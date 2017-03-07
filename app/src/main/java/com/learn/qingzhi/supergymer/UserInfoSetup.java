package com.learn.qingzhi.supergymer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangxi on 07/03/2017.
 */

public class UserInfoSetup extends AppCompatActivity {
    /*
    private static final String TAG = "UserInfoSetup";

    @BindView(R.id.input_weight) EditText _input_weight;
    @BindView(R.id.input_height) EditText _input_height;
    @BindView(R.id.input_gender) EditText _input_gender;
    @BindView(R.id.btn_sign_up) Button _SignUpButton;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_setup);
        ButterKnife.bind(this);

        _SignUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        //setup the information of drop down list
        String[] SPINNERLIST = {"Male","Female"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.input_gender);
        materialDesignSpinner.setAdapter(arrayAdapter);
    }

    public void signUp(){
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String password = intent.getStringExtra("password");

    }
*/
}
