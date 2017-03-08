package com.learn.qingzhi.supergymer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import db.DBHandler;
import db.User;

/**
 * Created by wangxi on 07/03/2017.
 */

public class UserLogin extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 1;

    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        ButterKnife.bind(this);
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), UserSignUp.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
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

    public void login(){
        Log.d(TAG,"Login");
        if(!validate()){
            onLoginFailed();
            return;
        }


        _loginButton.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(UserLogin.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String password = _passwordText.getText().toString();
        //System.out.println("name:" + name);
        //System.out.println("password: " + password);
        DBHandler dbHandler = new DBHandler(this);
        List<User> users = dbHandler.getUser(name);

        if(users.isEmpty()){
            _nameText.setError("username not exist");
            return;
        }
        //implement the authentication here !!!
        User user = users.get(0);
        Log.d("user_id:", Integer.toString(user.get_userId()));

        if(user.get_password().equals(password)){
            SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
            //Get SharedPreferences.Editor object，save object to sharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("UserId",Integer.toString(user.get_userId()));
            //put key value pair
            editor.commit();
            Intent intent = new Intent(UserLogin.this,EquiementActivity.class);
            startActivity(intent);

        }else{
            return;
        }
        //System.out.print("user id : " + user.get_userId() );
        //Toast.makeText(getBaseContext(), user.get_userId(), Toast.LENGTH_LONG).show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 2000);
        /*
        if(authenticating()){
            String name = _nameText.getText().toString();
            //DBHandler dbHandler = new DBHandler(this);
            //List<User> users = dbHandler.getUser(name);
            //User user = users.get(0);
            System.out.print("userpassword: " + "password");
            /*
            //put user Info into sharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);

            //Get SharedPreferences.Editor object，save object to sharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("UserId",Integer.toString(user.get_userId()));
            //put key value pair
            editor.commit();
            Intent intent = new Intent(UserLogin.this,EquiementActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(getBaseContext(), "username not exists or Wrong Password", Toast.LENGTH_LONG).show();
        }*/
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess(){
        _loginButton.setEnabled(true);

    }
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    public boolean authenticating(){
        String name = _nameText.getText().toString();
        String password = _passwordText.getText().toString();

        //get user account information
        DBHandler dbHandler = new DBHandler(this);
        List<User> users = dbHandler.getUser(name);
        System.out.print("authenticating !!!!!!!!!");
        if(users.isEmpty()){
            return false;
        }
        User user = users.get(0);
        if(user.get_password().equals(password)){
            return true;
        }else{
            return false;
        }
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty()) {
            _nameText.setError("enter a valid name");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
