package com.example.myreminds;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    DBHandler dbHandler;

    //declare shopping lists cursor adapter
    Reminders remindersAdapter;

    //declare a listView
    ListView reminderListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize the dbHandler
        dbHandler = new DBHandler(this,null);

        //initialize the listview
        reminderListView = (ListView) findViewById(R.id.reminderListView);

        //initialize shopping lists cursor adapter
        remindersAdapter = new Reminders(this, dbHandler.getReminders(), 0);

        //set shopping lists cursor adapter on listview
        reminderListView.setAdapter(remindersAdapter);

        //set onItemClickListener to shopper ListView
        reminderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //launch the view list activity and sending the id of the
                //shopping list
                intent = new Intent(MainActivity.this, ViewList.class);
                intent.putExtra("_id", id);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fabAddReminder);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddReminder(view);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        //getting the ID of the item that was selected
        switch (item.getItemId()) {
            case R.id.action_home:
                // initializing an intent for the main activity, starting it,
                // and returning true
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            // initializing an intent for the create list activity, starting it,
            // and returning true
            case R.id.action_add_reminder:
                intent = new Intent(this, AddReminder.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    public void openAddReminder(View view) {

        intent = new Intent(this, AddReminder.class);
        startActivity(intent);
    }

}