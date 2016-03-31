package com.example.oose.sublimecalendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityAddEvent extends AppCompatActivity implements View.OnClickListener {


    private EditText nameTB,dateTB,locationTB,emailListTB,eventTypeTB,startTimeTB,finishTimeTB,durationTB;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        nameTB=(EditText) findViewById(R.id.addEventNameTB);
        locationTB=(EditText) findViewById(R.id.addEventLocationTB);
        emailListTB=(EditText) findViewById(R.id.addEventEmailListTB);
        //eventTypeTB=(EditText) findViewById(R.id.addEvent);
        startTimeTB=(EditText) findViewById(R.id.addEventStartTimeTB);
        finishTimeTB=(EditText) findViewById(R.id.addEventFinishTimeTB);
        //durationTB=(EditText) findViewById(R.id.addEvent);
        dateTB=(EditText) findViewById(R.id.addEventDateTB);

        saveButton = (Button) findViewById(R.id.addEventSaveButton);
        saveButton.setOnClickListener(this);

    }


    @Override
        /*link on how to handle mutiple button clicks
         http://stackoverflow.com/questions/21827046/handle-multiple-button-click-in-view-onclicklistener-in-android*/
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.addEventSaveButton: //if save button was pressed
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                super.onBackPressed(); //same effect as pressing the back button
                break;
        }

    }
}
