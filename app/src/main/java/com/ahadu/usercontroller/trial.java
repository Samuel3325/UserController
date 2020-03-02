package com.ahadu.usercontroller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class trial extends AppCompatActivity {
    TextInputEditText fn,un,ph,em,pa,cp;
    TextView have_account;
    CheckBox chk;
    Button btn;
    Spinner gender;
    String x;
    DatabaseHelperClass databaseHelperClass = new DatabaseHelperClass(this);
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial);
        fn = findViewById(R.id.full_name);
        un = findViewById(R.id.user_name);
        gender = findViewById(R.id.customIconSpinner);
        ph = findViewById(R.id.phone);
        em = findViewById(R.id.email);
        pa = findViewById(R.id.password);
        cp = findViewById(R.id.confirm_pass);
        chk = findViewById(R.id.chk_privacy);
        have_account = findViewById(R.id.have_account);
        btn = findViewById(R.id.register_btn);
        db = new DatabaseHelper(this);


        final String[] gen = {"Male","Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,gen);

        gender.setAdapter(adapter);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                             @Override
                                             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                 switch (position) {
                                                     case 0:
                                                         x = gender.getSelectedItem().toString();
                                                     case 1:
                                                         x = gender.getSelectedItem().toString();
                                                 }
                                             }

                                             @Override
                                             public void onNothingSelected(AdapterView<?> parent) {

                                             }
                                         });

             btn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     insert(fn.getText().toString(),un.getText().toString(),x,em.getText().toString(),ph.getText().toString(),pa.getText().toString());
                 }
             });

            have_account.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),Login.class);
                    startActivity(intent);
                }
            });
    }
    private boolean validateEmail(){
        String emailInput = em.getText().toString().trim();
        if(emailInput.isEmpty()){
            em.setError("failed, It can't be empty");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            em.setError("invalid e-mail address");
            return false;
        }else{
            em.setError(null);
            return true;
        }
    }
    private boolean validateText(TextInputEditText coming){
         String emailInput = coming.getText().toString().trim();
        if(emailInput.isEmpty()){
            coming.setError("failed, It can't be empty");
            return false;
        }else {
            coming.setError(null);
            return true;
        }

    }

    public void insert( String f, String u,String em,String g,String ph, String pwd){
        if(validateText(fn)&&validateEmail()&&validateText(un)&&validateText(pa)&&validateText(cp)) {
            if(pa.getText().toString().equals(cp.getText().toString())) {
                boolean result = databaseHelperClass.insertData(f, u, em, g, ph, pwd);
                if (result) {
                    Toast.makeText(this, "Succesfully inserted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Error occured", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(),"Password didn't match",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
