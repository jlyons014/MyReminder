package com.example.myreminds;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ViewList extends AppCompatActivity {

    Intent intent;
    Cursor cursor;
    //fields used to get shopping list id passed from main activity
    long  id;
    Bundle bundle;

    DBHandler dbHandler;

    Reminders reminders;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get the id
        bundle = this.getIntent().getExtras();
        id = bundle.getLong("_id");

        dbHandler = new DBHandler(this, null);

        //call database method that returns shopping list name
        String reminderTitle = dbHandler.getReminderTitle((int) id);

        //set title of this activity to shopping list name
        this.setTitle(reminderTitle);

        /**boolean answer = reminders.sendNotification(cursor);

        if(answer = true){
            //initialize Notification
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

//set icon, title, and text
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle("MyReminders");
            builder.setContentText("Reminder has expired!");

//initialize intent for viewList activity
            intent = new Intent(this, ViewList.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

//have the notification manager send the notification
            notificationManager.notify(2142, builder.build());
        }
**/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_list, menu);
        return true;
    }

    /**
     * This method gets called when an item in the overflow menu is selected and
     * it controls what happens when the item is selected
     * @param item item selected in the overflow menu. it contains information about the item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        //getting the ID of the item that was selected
        switch (item.getItemId()) {
            case R.id.action_home :
                // initializing an intent for the main activity, starting it,
                // and returning true
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            // initializing an intent for the create list activity, starting it,
            // and returning true
            case R.id.action_add_reminder :
                intent = new Intent(this, AddReminder.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    public void openAddReminder(View view) {
    }
}
