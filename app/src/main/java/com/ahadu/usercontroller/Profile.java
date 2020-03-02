package com.ahadu.usercontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class Profile extends AppCompatActivity {
    TextView tv_user_name,tv_email_from_sql,tv_phone_sql;
    DatabaseHelperClass databaseHelperClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        databaseHelperClass= new DatabaseHelperClass(this);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_email_from_sql = findViewById(R.id.email_from_sql);
        tv_phone_sql = findViewById(R.id.phone_no_sql);
        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        displayToProfile(username);



    }
    public void displayToProfile(String u) {
        Cursor cursor = databaseHelperClass.getProfileData(u);

        while(cursor.moveToNext()){
            tv_user_name.setText(cursor.getString(0));
            tv_phone_sql.setText(cursor.getString(1));
            tv_email_from_sql.setText(cursor.getString(2));
        }

    }
}
