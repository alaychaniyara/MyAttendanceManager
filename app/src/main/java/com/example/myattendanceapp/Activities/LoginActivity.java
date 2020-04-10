package com.example.myattendanceapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.example.myattendanceapp.Data.LoginDatabaseHelper;
import com.example.myattendanceapp.Fragments.Fragment_Login;
import com.example.myattendanceapp.Fragments.Fragment_Signup;
import com.example.myattendanceapp.Fragments.fragmentInterfaces.Fragment_Callback;
import com.example.myattendanceapp.R;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity implements Fragment_Callback {

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
       //Adding Fragment ON Activity Create
        addFragmentToActivity();





    }


    public void addFragmentToActivity()
    {
       Fragment_Login fragment = new Fragment_Login();
       fragment.setFragment_callback(this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.container_fragment, fragment);
        fragmentTransaction.commit();
    }

    public void replaceFragmentToActivity()
    {
        fragment = new Fragment_Signup();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.container_fragment, fragment);
        fragmentTransaction.commit();
    }


    public String getCurrentLocale ()
    {
        Locale current = getResources().getConfiguration().locale;

        return current.toString();
    }
    public void setAppLocale(String localeCode){
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
            config.setLocale(new Locale(localeCode.toLowerCase()));
        } else {
            config.locale = new Locale(localeCode.toLowerCase());
        }
        resources.updateConfiguration(config, dm);

    }
    @Override
    public void fragment_Switch() {
           replaceFragmentToActivity();
    }
}
