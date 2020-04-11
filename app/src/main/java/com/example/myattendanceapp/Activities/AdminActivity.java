package com.example.myattendanceapp.Activities;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.myattendanceapp.Adapter.Attendance_Adapter_All;
import com.example.myattendanceapp.Data.AttendanceDatabaseHelper;
import com.example.myattendanceapp.Data.Attendance_List;
import com.example.myattendanceapp.Data.LoginDatabaseHelper;

import com.example.myattendanceapp.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;

public class AdminActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener {

    public ArrayList<Attendance_List> attendance_list;
    RecyclerView rvAttendance;
    protected AttendanceDatabaseHelper adb;
    protected Button buttonSelectDate;
    protected DatePickerDialog datePickerDialog;
    Attendance_Adapter_All attendanceAdapterAll;
    TextView textViewmessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        getSupportActionBar().setTitle("Welcome Professor");
        adb = new AttendanceDatabaseHelper(this);

       textViewmessage = findViewById(R.id.textViewSelectDateLabel);
        Toast.makeText(this, "Check This Week's Attendance", Toast.LENGTH_LONG).show();

        rvAttendance = (RecyclerView) findViewById(R.id.rvAttendance);
        attendance_list = new ArrayList<Attendance_List>();
        Attendance_List list = new Attendance_List();
        list.setEmail("");
        list.setName("LIST EMPTY SELECT DATE PLEASE");
        list.setStatus("");
        attendance_list.add(list);
        attendanceAdapterAll  = new Attendance_Adapter_All(attendance_list);

        rvAttendance.setAdapter(attendanceAdapterAll);
        rvAttendance.setLayoutManager(new LinearLayoutManager(this));
        buttonSelectDate = findViewById(R.id.buttonDatePicker);

        buttonSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             showDatePickerDialog();

            }
        });
         }

    private void showDatePickerDialog() {
        Calendar c = Calendar.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            datePickerDialog = new DatePickerDialog(AdminActivity.this,
                     this,
                    c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            c.add(Calendar.DATE, -6);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            datePickerDialog.show();

        }

    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        String date = month+1+"/"+dayOfMonth+"/"+year;
        textViewmessage.setText("Attendance For: "+date);

           this.attendance_list =  adb.getAllAttendance(date);
           if(attendance_list.isEmpty())
           {
               Toast.makeText(this, "Sorry NO Attendance Registered for : "+date, Toast.LENGTH_LONG).show();



               attendanceAdapterAll.updateList(attendance_list);
           }
           else {

            textViewmessage.setText("Attendance For: "+date);

              attendanceAdapterAll.updateList(attendance_list);


               Toast.makeText(this, "Here are students who were present today", Toast.LENGTH_SHORT).show();
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
