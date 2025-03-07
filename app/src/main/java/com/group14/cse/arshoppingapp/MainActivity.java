package com.group14.cse.arshoppingapp;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText Username_editText;
    private FirebaseAuth auth;
    private EditText Password_editText;
    private Button login_button;
    private Button register_button;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(sp.getBoolean("flag",false))
        {  Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
        }
        auth = FirebaseAuth.getInstance();
        final EditText Name = findViewById(R.id.MailID_editText);
        final EditText Password = findViewById(R.id.Password_editText);
        Button Login = findViewById(R.id.login_button);
        Button Register = findViewById(R.id.rgRegister_Button);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = Name.getText().toString().trim();
                final String password = Password.getText().toString().trim();

                Task<AuthResult> authResultTask = auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            SharedPreferences.Editor ed=sp.edit();
                            ed.putBoolean("wflag", (true));
                            ed.commit();
                            Intent intent = new Intent(MainActivity.this, Home.class);
                            startActivity(intent);

                        } else {
                            Log.w("createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                    }
                });

            }

        });


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}

