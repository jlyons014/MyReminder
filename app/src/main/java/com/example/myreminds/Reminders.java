package com.example.myreminds;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Reminders extends CursorAdapter {

    public Reminders(Context context, Cursor cursor, int flags){
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.li_reminder, viewGroup, false);
    }


    public String isReminderExpired(Cursor cursor){

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String todaysDate = (formatter.format(calendar.getTime()));
        String reminderDate = (cursor.getString(cursor.getColumnIndex("date")));

        if(todaysDate.compareTo(reminderDate) <= 0) {
            return "no";
        } else
            return "yes";
    }

    public boolean sendNotification(Cursor cursor){
        if (isReminderExpired(cursor).equalsIgnoreCase("yes")){
            return true;
        }
        else
            return false;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ((TextView) view.findViewById(R.id.titleTextView)).
                setText(cursor.getString(cursor.getColumnIndex("title")));
        ((TextView) view.findViewById(R.id.dateTextView)).
                setText(cursor.getString(cursor.getColumnIndex("date")));
        ((TextView) view.findViewById(R.id.hasTextView)).
                setText("Expired? " + isReminderExpired(cursor));
    }

    }

