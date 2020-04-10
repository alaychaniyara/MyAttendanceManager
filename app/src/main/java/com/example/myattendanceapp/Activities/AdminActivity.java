package com.example.myattendanceapp.Activities;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myattendanceapp.Data.LoginDatabaseHelper;
import com.example.myattendanceapp.R;

import java.util.Calendar;

public class AdminActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener {

    protected LoginDatabaseHelper db;
    protected String email;
    protected Button buttonSelectDate;
    protected DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        getSupportActionBar().setTitle("Welcome Professor");


        Toast.makeText(this, "Check This Week's Attendance", Toast.LENGTH_LONG).show();

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
                    (DatePickerDialog.OnDateSetListener) this,
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
        Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
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
