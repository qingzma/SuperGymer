package com.learn.qingzhi.supergymer;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import db.DBHandler;

/**
 * Created by wangxi on 06/03/2017.
 */

public class UserSignUp extends AppCompatActivity{
    private static final String TAG = "UserSignUpActivity";
    private static final int REQUEST_LOGIN = 0;

    @BindView(R.id.input_name) EditText _input_name;
    @BindView(R.id.input_password) EditText _input_password;
    @BindView(R.id.btn_next) Button _nextButton;
    @BindView(R.id.link_login) TextView _loginLink;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_sign_up);
        ButterKnife.bind(this);

        _nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                next();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), UserLogin.class);
                startActivityForResult(intent, REQUEST_LOGIN);
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_home:
                                Toast.makeText(getApplicationContext(), "menu_home", Toast.LENGTH_SHORT).show();
                            case R.id.menu_scan:
                                Toast.makeText(getApplicationContext(),"menu_scan",Toast.LENGTH_SHORT).show();
                            case R.id.menu_user:
                                Toast.makeText(getApplicationContext(),"menu_user",Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
    }

    public void next(){

        Log.d(TAG,"next");

        /*
        _nextButton.setEnabled(true);

        if(!validate()){
            nextFailed();
        }
        _nextButton.setEnabled(false);
        */

        /*
        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();
        */
        String name = _input_name.getText().toString();
        String password = _input_password.getText().toString();
        //System.out.println("name: " + name);
        if(validate()){
            nextSuccess(name,password);
        }else{
            nextFailed();
        }
        /*
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        nextSuccess(name,password);
                        // onSignupFailed();
                        //progressDialog.dismiss();
                    }
                }, 3000);*/
    }

    public void nextSuccess(String name,String password) {
        _nextButton.setEnabled(true);
        Intent intent = new Intent(UserSignUp.this,UserInfoSetup.class);
        intent.putExtra("name",name);
        intent.putExtra("password",password);
        startActivity(intent);
        setResult(RESULT_OK, null);
    }

    public void nextFailed() {
        //Toast.makeText(getBaseContext(), " failed", Toast.LENGTH_LONG).show();
    }
    public boolean validate() {
        boolean valid = true;
        DBHandler dbHandler = new DBHandler(this);
        String name = _input_name.getText().toString();
        String password = _input_password.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _input_name.setError("at least 3 characters");
            valid = false;
        }else if(dbHandler.getUser(name) != null){
            _input_name.setError("username exists");
            valid = false;
        }else {
            _input_name.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _input_password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _input_password.setError(null);
        }

        return valid;
    }
}
