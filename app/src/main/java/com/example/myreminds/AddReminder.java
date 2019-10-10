package com.example.myreminds;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Calendar;

public class AddReminder extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Intent intent;
    EditText titleEditText;
    EditText dateEditText;
    EditText typeEditText;
    DBHandler dbHandler;
    Spinner typeSpinner;
    String type;

    Calendar calendar;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//initialize EditTexts
        titleEditText = findViewById(R.id.titleEditText);
        dateEditText = findViewById(R.id.dateEditText);
        typeSpinner = (Spinner) findViewById(R.id.typeSpinner);

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,R.array.type, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setOnItemSelectedListener(this);

        //initialize calendar
        calendar = Calendar.getInstance();

        //initialize a DatePickerDialog and register an OnDateSetListener to it
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                //set the Calendar year, month, and day to the year, month and day
                //set in the DatePickerDialog
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                //call method that updates the date with date set
                //in the DatePicker
                updateDueDate();

            }
        };

        //register an OnClickListener to the date EditText
        dateEditText.setOnClickListener(new View.OnClickListener(){
            /**
             * This method gets called when the date EditText is clicked
             * @param view because the date EditText that calls this method is
             *             technically considered a view, we must pass the method
             *             a View
             */
            @Override
            public void onClick(View view) {
                //display DatePickerDialog with current date selected
                new DatePickerDialog(AddReminder.this,
                        date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)        ).show();
            }
        });
        dbHandler = new DBHandler(this, null);

    }

    public void addReminder (MenuItem menuItem) {
//get data input in EditTexts and store it in strings
        String title = titleEditText.getText().toString();
        String date = dateEditText.getText().toString();



        //trim Strings and see if any are equal to an empty string
        if (title.trim().equals("") || date.trim().equals("") || (type.trim().equals(""))) {
            //required data hasn't been input, so display toast
            Toast.makeText(this, "Please enter a title, date, and type!", Toast.LENGTH_LONG).show();

        }
        else {
            //required data has been input, update the database and display a different toast
            dbHandler.addReminder(title, date, type);
            Toast.makeText(this, "Reminder added!", Toast.LENGTH_LONG).show();

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_reminder, menu);
        return true;
    }

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

    public void updateDueDate() {
        //this method gets called when the date is set in the DatePickerDialog

        //Create a SimpleDateFormat
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        //Apply the SimpleDateFormat to the date set in the DatePickerDialog and
        //set the formatted date in the date EditText
        dateEditText.setText(simpleDateFormat.format(calendar.getTime()));

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

        if (adapterView.equals(typeSpinner)){
            type = adapterView.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}