package com.example.myattendanceapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.myattendanceapp.Data.LoginDatabaseHelper;
import com.example.myattendanceapp.R;

public class MainActivity extends AppCompatActivity {

    protected LoginDatabaseHelper db;
    protected String email;
    protected CalendarView calendarView;
    protected Button buttonMarkPresent, buttonMarkAbsent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        db = new LoginDatabaseHelper(this);

        calendarView = findViewById(R.id.main_calenderView);
        buttonMarkAbsent = findViewById(R.id.buttonMarkAbsent);
        buttonMarkPresent = findViewById(R.id.buttonMarkPresent);

        calendarView.setMinDate(calendarView.getDate());
        calendarView.setMaxDate(calendarView.getDate());

        buttonMarkPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonMarkAbsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        String name = db.getUser(email);
        getSupportActionBar().setTitle("Welcome, "+name);

        Toast.makeText(this, "Register Your Today's Attendance", Toast.LENGTH_LONG).show();
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
