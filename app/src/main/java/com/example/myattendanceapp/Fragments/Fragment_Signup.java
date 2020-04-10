package com.example.myattendanceapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myattendanceapp.Activities.LoginActivity;
import com.example.myattendanceapp.Data.LoginDatabaseHelper;
import com.example.myattendanceapp.Fragments.fragmentInterfaces.Fragment_Callback;
import com.example.myattendanceapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Signup extends Fragment {


    protected Button buttonlogin;
    protected Button buttonsignup;

    protected EditText editTextname;
    protected EditText editTextemail;
    protected EditText editTextpassword;
    protected String loginemail,loginpassword;

    protected LoginDatabaseHelper db;
    Fragment_Callback fragment_callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        buttonlogin = view.findViewById(R.id.signup_button_login);
        buttonsignup  = view.findViewById(R.id.signup_button_signup);
        editTextname = view.findViewById(R.id.signup_editText_name);
        editTextemail = view.findViewById(R.id.signup_editText_username);

        editTextpassword = view.findViewById(R.id.signup_editText_password);

        db = new LoginDatabaseHelper(getActivity());
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editTextname.getText().toString().trim();
                String email = editTextemail.getText().toString().trim().toLowerCase();
                String pwd = editTextpassword.getText().toString().trim();

                if(email.contains("@uncc.edu")){
                    boolean val = db.addUser(name,email,pwd);
                    if(val){
                        Toast.makeText(getActivity(),"You have registered",Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(getActivity(), LoginActivity.class);
                        startActivity(moveToLogin);
                    }
                    else{
                        Toast.makeText(getActivity(),"Email must be Unique Error",Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(getActivity(),"Email is not of @uncc.edu",Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
}
