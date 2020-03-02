package com.ahadu.usercontroller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    Button login, sign_up;
    EditText et_username, et_password;
    String username, password;
    SharedPreferences sharedPreferences;

    DatabaseHelperClass databaseHelperClass = new DatabaseHelperClass(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login_btn);
        sign_up = findViewById(R.id.sign_up_btn);
        et_username = findViewById(R.id.et_user_name_login);
        et_password = findViewById(R.id.et_password);
        sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        username = et_username.getText().toString();
        password = et_password.getText().toString();
        if(sharedPreferences.getBoolean("isLogged",false)){
            toUserPage();
        }
        //Data those checked in the database
        //====================================

        //====================================
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), trial.class);
                startActivity(intent);
            }
        });




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateText(et_username) && validateText(et_password)) {
                    if(auth(et_username.getText().toString(),et_password.getText().toString())){
                        sharedPreferences.edit().putBoolean("isLogged",true).apply();
                        toUserPage();
                    }

                }
            }
        });
    }

    private boolean validateText(EditText var) {
        String passwordInput = var.getText().toString();
        if (passwordInput.isEmpty()) {
            var.setError("failed, can't be empty");
            return false;
        } else {
            var.setError(null);
            return true;
        }
    }




    public boolean auth(String username, String pwd){

        Cursor res = databaseHelperClass.getSelectedData(username);
        String uname= "", pass="";
        if(res.getCount() == 0){
            Toast.makeText(getApplicationContext(),"No such Data",Toast.LENGTH_SHORT).show();
            return false;
        }

        while (res.moveToNext()){
            uname = res.getString(0);
            pass = res.getString(1);

        }
        if(uname.equals(username) && pass.equals(pwd)){
            return true;
        }else {
            Toast.makeText(this, "Incorrect Password or UserName", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
    public void toUserPage(){
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}