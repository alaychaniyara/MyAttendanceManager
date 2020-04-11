package com.example.myattendanceapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.myattendanceapp.Data.AttendanceDatabaseHelper;
import com.example.myattendanceapp.Data.LoginDatabaseHelper;
import com.example.myattendanceapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    protected LoginDatabaseHelper db;
    protected AttendanceDatabaseHelper adb;
    protected String email,name,date;
    protected CalendarView calendarView;
    protected Button buttonMarkPresent, buttonMarkAbsent;
    String status;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        db = new LoginDatabaseHelper(this);
        adb = new AttendanceDatabaseHelper(this);
        calendarView = findViewById(R.id.main_calenderView);
        buttonMarkAbsent = findViewById(R.id.buttonMarkAbsent);
        buttonMarkPresent = findViewById(R.id.buttonMarkPresent);

        calendarView.setMinDate(calendarView.getDate());
        calendarView.setMaxDate(calendarView.getDate());
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
       date = sdf.format(new Date(calendarView.getDate()));
        Log.d("Date",date);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
               month++;
                if((month)<10)
                {
                    date = "0"+month+"/"+dayOfMonth+"/"+year;
                    Log.d("DDDD", date);
                    Toast.makeText(MainActivity.this, date, Toast.LENGTH_SHORT).show();

                }
                else
                {
                    date = month+"/"+dayOfMonth+"/"+year;}
                Toast.makeText(MainActivity.this, month+"/"+dayOfMonth+"/"+year, Toast.LENGTH_SHORT).show();
            }
        });
        buttonMarkPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "Present";
                updateAttendance(name,email, date,status);


            }
        });

        buttonMarkAbsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "Absent";
                updateAttendance(name,email, date,status);
            }
        });


       name  = db.getUser(email);
        getSupportActionBar().setTitle("Welcome, "+name);

        Toast.makeText(this, "Register Your Today's Attendance", Toast.LENGTH_LONG).show();
    }

    private  void updateAttendance(String name,String email, String date, String status){

        boolean val = adb.markAttendance(name,email,date,status);
        if(val){
            Toast.makeText(this,"Thank YOU! Your Attendance has been Registered",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"Sorry! You have already marked your Attendance for Today Come Back Tomorrow",Toast.LENGTH_LONG).show();
        }

    }
    private void logout() {

        Intent logout_intent = new Intent(this, LoginActivity.class);
        logout_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(logout_intent);
        this.finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
