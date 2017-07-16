package com.example.hi.mydiaryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    ProgressBar progressBar;
    Handler handler;
    CheckBox checkBox;
    EditText txtUsername, txtPassword;
    SharedPreferences sp;
    private long mFirstBackKeyPressTime;
    private long mLastBackKeyPressTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        checkBox = (CheckBox)findViewById(R.id.checkBox);
        txtUsername = (EditText)findViewById(R.id.txtUsername);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        sp = getSharedPreferences("LoginFolder", MODE_PRIVATE);

        Boolean isRemember = sp.getBoolean("remember", true);

        progressBar.setVisibility(View.GONE);
        if(isRemember){
            checkBox.setChecked(true);
            txtUsername.setText(sp.getString("username", ""));
            txtPassword.setText(sp.getString("password", ""));
        }

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent();
                intent.setClassName(MainActivity.this, "com.example.user.mydiary.DiaryActivity");
                startActivity(intent);
            }
        };

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sp.edit();
                if ((!txtUsername.getText().toString().equals(sp.getString("username", "Mlaistar"))) || (!txtPassword.getText().toString().equals(sp.getString("password", "Password")))){
                    Toast.makeText(MainActivity.this, "Incorrect username or password.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(MainActivity.this, "Please wait...", Toast.LENGTH_SHORT).show();
                    if (checkBox.isChecked()) {
                        editor.putBoolean("remember", true);
                        editor.putString("password", txtPassword.getText().toString());
                        editor.putString("username", txtUsername.getText().toString());
                        editor.commit();
                    } else {
                        editor.clear();
                        editor.putBoolean("remember", false);
                        editor.commit();
                    }

                    progressBar.setVisibility(View.VISIBLE);
                    Thread newThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (true) {
                                try {
                                    Thread.sleep(5000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Message msg = new Message();
                                handler.sendMessage(msg);
                                break;
                            }
                        }
                    });
                    newThread.start();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(mFirstBackKeyPressTime == 1){
            mFirstBackKeyPressTime = System.currentTimeMillis();
            Toast.makeText(this, "Press Back one more time to quit", Toast.LENGTH_SHORT).show();
        }
        else {
            mLastBackKeyPressTime = System.currentTimeMillis();
            if (mLastBackKeyPressTime - mFirstBackKeyPressTime <= 3000) {
                finish();
                super.onBackPressed();
            } else {
                mFirstBackKeyPressTime = System.currentTimeMillis();
                Toast.makeText(this, "Press Back one more time to quit", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
