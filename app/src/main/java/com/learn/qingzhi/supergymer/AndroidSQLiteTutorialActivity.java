package com.learn.qingzhi.supergymer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.List;

import db.Contact;
import db.DBHandler;
import db.DatabaseHandler;
import db.User;

public class AndroidSQLiteTutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_sqlite_tutorial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /*
        DatabaseHandler db = new DatabaseHandler(this);

        /**
         * CRUD Operations
         * */
        // Inserting Contacts
/*        Log.d("Insert: ", "Inserting ..");
        db.addContact(new Contact("Ravi", "9100000000"));
        db.addContact(new Contact("Srinivas", "9199999999"));
        db.addContact(new Contact("Tommy", "9522222222"));
        db.addContact(new Contact("Karthik", "9533333333"));

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();

        for (Contact cn : contacts) {
            String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }
        */

        DBHandler db = new DBHandler(this);

        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        /*
        Log.d("Insert: ", "Inserting ..");
        db.addUser(new User("Tom1", 70, 180, 1,"skjfkdsf"));
        db.addUser(new User("John1", 60,190,1,"a34sdfsd"));
        db.addUser(new User("Lily0", 50,165,0,"sdfas234fdgas"));
        db.addUser(new User("Sue0", 65,170,0,"ahfi893r"));
        */

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<User> contacts = db.getAllUsers();

        for (User cn : contacts) {
            String log = cn.toString();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }

        //get users by name
        contacts = db.getUser("Sue0");

        for (User cn : contacts) {
            String log = cn.toString();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }

 /*
        //insert one user
        Log.d("User","insert new user:");
        db.addUser(new User("Lee1", 90,190,1,"a2738432jsdflkjsf"));
        Log.d("Reading: ", "Reading all contacts..");
        contacts = db.getAllUsers();

        for (User cn : contacts) {
            String log = cn.toString();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }
*/

/*
        //delete one user
        Log.d("User","delete one user:");
        User user=db.getUser(1);
        db.deleteUser(user);
        Log.d("Reading: ", "Reading all contacts..");
        contacts = db.getAllUsers();

        for (User cn : contacts) {
            String log = "Id: " + cn.get_userId() + ", Name: " + cn.get_name() +
                    " ,weight: " + cn.get_weight()+", Height: "+cn.get_weight()+
                    ", gender: "+cn.get_gender();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }
*/

    }

}
