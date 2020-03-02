package com.ahadu.usercontroller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    UserListAdapter userListAdapter;
    List<userModel> mData;
    FloatingActionButton fabSwitcher;
    FloatingActionButton fabLogOut;
    boolean isDark = false;
    ConstraintLayout rootLayout;
    SharedPreferences toLoss;

    DatabaseHelperClass databaseHelperClass = new DatabaseHelperClass(this);
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);


        fabSwitcher = findViewById(R.id.fab_switcher);
        fabLogOut = findViewById(R.id.fab_logout);
        rootLayout = findViewById(R.id.root_layout);
        toLoss = getSharedPreferences("login_preference",MODE_PRIVATE);

        recyclerView = findViewById(R.id.user_rv);
        mData = new ArrayList<>();

        isDark = getThemeStatePref();
        if(isDark){
            rootLayout.setBackgroundColor(getResources().getColor(R.color.black));
        }else{
            rootLayout.setBackgroundColor(getResources().getColor(R.color.white));
        }

//        mData.add(new userModel("Kobe Bryant"," consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","12/07/2019",R.mipmap.pro1));
//        mData.add(new userModel("Lebron James"," consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","12/07/2019",R.mipmap.pro1));
//        mData.add(new userModel("Mikel Jordan"," consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","12/07/2019",R.mipmap.pro1));
//        mData.add(new userModel("Jack Rolf"," consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","12/07/2019",R.mipmap.pro1));
//        mData.add(new userModel("Daniel Kevin"," consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","12/07/2019",R.mipmap.pro1));
//        mData.add(new userModel("Jhon Wick"," consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","12/07/2019",R.mipmap.pro1));
//        mData.add(new userModel("Constantin Smith"," consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","12/07/2019",R.mipmap.pro1));
//        mData.add(new userModel("James Cameroon"," consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","12/07/2019",R.mipmap.pro1));
//        mData.add(new userModel("Samuel El-jackson"," consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","12/07/2019",R.mipmap.pro1));
//        mData.add(new userModel("Dr Strange"," consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","12/07/2019",R.mipmap.pro1));
//        mData.add(new userModel("Tanus Brave"," consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","12/07/2019",R.mipmap.pro1));
//        mData.add(new userModel("Captain America"," consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","12/07/2019",R.mipmap.pro1));
//        mData.add(new userModel("Mikel Jordan"," consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","12/07/2019",R.mipmap.pro1));
//        mData.add(new userModel("Jack Rolf"," consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","12/07/2019",R.mipmap.pro1));
//        mData.add(new userModel("Daniel Kevin"," consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","12/07/2019",R.mipmap.pro1));
//        mData.add(new userModel("Jhon Wick"," consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","12/07/2019",R.mipmap.pro1));
//        mData.add(new userModel("Constantin Smith"," consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","12/07/2019",R.mipmap.pro1));
        Cursor cursor = databaseHelperClass.getAllData();
        if(cursor.getCount() == 0){
            //show message
            showMessage("Error","Nothing is found");
            return;
        }
        String[] from = new String[2];
        while (cursor.moveToNext()){
            from[0] = cursor.getString(1);
            from[1] = cursor.getString(6);
            mData.add(new userModel(from[0]," consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",from[1],R.mipmap.pro1));

        }

        userListAdapter = new UserListAdapter(this,mData);
        recyclerView.setAdapter(userListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fabSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDark = !isDark;
                if(isDark){
                    rootLayout.setBackgroundColor(getResources().getColor(R.color.black));
                }else{
                    rootLayout.setBackgroundColor(getResources().getColor(R.color.white));
                }
                    userListAdapter = new UserListAdapter(getApplicationContext(),mData,isDark);
                recyclerView.setAdapter(userListAdapter);
                saveThemeStatePref(isDark);
            }
        });
        fabLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toExit();
            }
        });
    }

    private void saveThemeStatePref(boolean isDark) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("mypref",MODE_PRIVATE);
        SharedPreferences.Editor editor =pref.edit();
        editor.putBoolean("isDark",isDark);
        editor.commit();
    }
    private boolean getThemeStatePref(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("mypref",MODE_PRIVATE);
        boolean isDark = pref.getBoolean("isDark",false);
        return isDark;
    }
    public void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void toExit(){
        Intent intent = new Intent(getApplicationContext(),Login.class);
        toLoss.edit().putBoolean("isLogged",false).apply();
        startActivity(intent);

    }
}
