package com.example.myattendanceapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myattendanceapp.Activities.AdminActivity;
import com.example.myattendanceapp.Activities.LoginActivity;
import com.example.myattendanceapp.Activities.MainActivity;
import com.example.myattendanceapp.Data.LoginDatabaseHelper;
import com.example.myattendanceapp.Fragments.fragmentInterfaces.Fragment_Callback;
import com.example.myattendanceapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class  Fragment_Login extends Fragment {

    protected Button buttonlogin;
    protected Button buttonsignup;
    protected TextView textViewEnglish;

    protected TextView textViewEspañol;

    protected EditText editTextusername;
    protected EditText editTextpassword;
    protected String loginemail,loginpassword;

    protected Fragment_Callback fragment_callback;

    protected LoginDatabaseHelper db;

    @Override
    public void onAttach(@NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userFile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        buttonlogin = view.findViewById(R.id.login_button_login);
        buttonsignup  = view.findViewById(R.id.login_button_signup);
        editTextusername = view.findViewById(R.id.login_editText_username);
        editTextpassword = view.findViewById(R.id.login_editText_password);

        textViewEnglish = view.findViewById(R.id.textViewEnglish);
        textViewEspañol = view.findViewById(R.id.textViewEspañol);


        db = new LoginDatabaseHelper(this.getContext());

        String locale = ((LoginActivity) getActivity()).getCurrentLocale();

        if(locale.equals("en"))
        {
            textViewEnglish.setTextColor(Color.GREEN);
        }
        else
        {
            textViewEspañol.setTextColor(Color.GREEN);
        }

        textViewEspañol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity) getActivity()).setAppLocale("es");

                getActivity().finish();
                startActivity(getActivity().getIntent());
                getActivity().overridePendingTransition(0, 0);
                textViewEspañol.setTextColor(Color.GREEN);
                textViewEnglish.setTextColor(Color.BLACK);
            }
        });

        textViewEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity) getActivity()).setAppLocale("en");
                getActivity().finish();
                startActivity(getActivity().getIntent());
                getActivity().overridePendingTransition(0, 0);
              //  ((LoginActivity) getActivity()).recreate();
                textViewEspañol.setTextColor(Color.BLACK);
                textViewEnglish.setTextColor(Color.GREEN);
            }
        });
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = editTextusername.getText().toString().trim();
                String pwd = editTextpassword.getText().toString().trim();
                if(user.contains("@uncc.edu") && !pwd.isEmpty()) {

                    Boolean res = db.checkUser(user, pwd);
                    if (res == true) {
                        Intent MainPage = new Intent(getActivity(), MainActivity.class);
                        MainPage.putExtra("email", user);
                        startActivity(MainPage);
                    }
                    else if (user.equals("professor@uncc.edu") && !pwd.isEmpty() && pwd.equals("admin"))
                    {
                        Intent AdminPage = new Intent(getActivity(), AdminActivity.class);
                        startActivity(AdminPage);
                    }

                    else {
                        Toast.makeText(getActivity(), "Login Error", Toast.LENGTH_SHORT).show();
                    }
                }

                else {
                    Toast.makeText(getActivity(),"Username is not of @uncc.edu or Password is empty",Toast.LENGTH_SHORT).show();

                }
            }
        });

        buttonsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fragment_callback!=null)
                {
                    fragment_callback.fragment_Switch();
                }
            }
        });


        return view;
    }

    public void setFragment_callback(Fragment_Callback fragment_callback) {
        this.fragment_callback = fragment_callback;
    }
}
