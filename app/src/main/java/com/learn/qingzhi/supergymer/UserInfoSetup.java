package com.learn.qingzhi.supergymer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.learn.qingzhi.supergymer.Main_workflow.EquipmentActivity;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import db.DBHandler;
import db.User;

/**
 * Created by wangxi on 07/03/2017.
 */

public class UserInfoSetup extends AppCompatActivity {

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
        if(validate()){
            SignupSuccess();
        }else{
            SignupFailed();
        }


    }

    public void SignupSuccess(){
        /*
        final ProgressDialog progressDialog = new ProgressDialog(UserInfoSetup.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();
        */
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String password = intent.getStringExtra("password");
        DBHandler dbHandler = new DBHandler(this);
        float weight = Float.parseFloat(_input_weight.getText().toString());
        float height = Float.parseFloat(_input_height.getText().toString());
        String gender = _input_gender.getText().toString();
        int genderFlag = 0;// 1 indicate male, 0 indicate female
        if(gender.equals("male")){
            genderFlag = 1;
        }
        User user = new User(name,weight,height,genderFlag,password);
        dbHandler.addUser(user);
        List<User> users = dbHandler.getUser(name);
        User user1 = users.get(0);

        //put user info into sharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        //Get SharedPreferences.Editor object，save object to sharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserId",Integer.toString(user1.get_userId()));
        //put key value pair
        editor.commit();

        Intent intent_new = new Intent(UserInfoSetup.this, EquipmentActivity.class);
        startActivity(intent_new);
    }


    public void SignupFailed(){
        Toast.makeText(getBaseContext(), "Sign Up failed", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;
        //DBHandler dbHandler = new DBHandler(this);

        float weight;
        float height;
        if(_input_weight.getText().toString() == null){
            weight = 0;
        }else{
            weight = Float.parseFloat(_input_weight.getText().toString());
        }
        if(_input_height.getText().toString() == null){
            height = 0;
        }else{
            height =  Float.parseFloat(_input_height.getText().toString());
        }

        //int height = Integer.parseInt(_input_height.getText().toString());
        String gender = _input_gender.getText().toString();

        if (Math.abs(weight - 0)<1e-6) {
            _input_weight.setError("weight cannot be empty");
            valid = false;
        }else {
            _input_weight.setError(null);
        }

        if (Math.abs(height - 0) < 1e-6) {
            _input_height.setError("height cannot be empty");
            valid = false;
        } else {
            _input_height.setError(null);
        }

        if(gender.isEmpty()){
            _input_gender.setError("please select your gender");
            valid = false;
        }else{
            _input_gender.setError(null);
        }
        return valid;
    }

}
