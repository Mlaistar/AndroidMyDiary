package com.example.hi.mydiaryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

public class DiaryActivity extends AppCompatActivity {

    Button btnSave;
    EditText editText;
    FileInputStream fileInputStream;
    FileOutputStream fileOutputStream;
    String line="", stringCollector="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        btnSave = (Button)findViewById(R.id.btnSave);
        editText = (EditText) findViewById(R.id.editText);

        File path = getApplicationContext().getFilesDir();
        File file = new File(path, "myDiary.txt");
        BufferedReader br;
        // MODE_APPEND;

        try {

            fileInputStream = new FileInputStream(file);
            br = new BufferedReader(new InputStreamReader(fileInputStream));



            while((line = br.readLine()) != null) {
                stringCollector += line +"\n";
            }
            editText.setText(stringCollector + "\n");
            fileInputStream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File path = getApplicationContext().getFilesDir();
                try {

                    Time time = new Time("GMT+8");
                    time.setToNow();
                    // editText.append("\n" + time.year + "-" + (time.month +1)+ "-" + time.monthDay);
                    editText.append("\nEdited on: \n" + (new SimpleDateFormat("yyyy-MM-dd HH:mm").format(System.currentTimeMillis())).toString() + "\n\n");
                    // String text = (new SimpleDateFormat("yyyy-MM-dd HH:mm").format(System.currentTimeMillis())).toString() + "\n" + editText.getText().toString()+"\n";
                    //editText.setText((new SimpleDateFormat("yyyy-MM-dd HH:mm").format(System.currentTimeMillis())+"\n"+editText.getText()));
                    File file = new File(path, "myDiary.txt");
                    fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(editText.getText().toString().getBytes());
                    fileOutputStream.close();

                    Toast.makeText(DiaryActivity.this, "Successfully saved!", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
