package com.example.oose.sublimecalendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivitySingleEventView extends AppCompatActivity implements View.OnClickListener {

    private TextView name,date,startTime,finishTime,location,emailList, eventType, note;
    private Button editButton, shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event_view);

        name =(TextView) findViewById(R.id.singleEventNameField);
        date =(TextView) findViewById(R.id.singleEventDateField);
        startTime =(TextView) findViewById(R.id.singleEventStartTimeField);
        finishTime =(TextView) findViewById(R.id.singleEventFinishTimeField);
        location =(TextView) findViewById(R.id.singleEventLocationField);
        emailList =(TextView) findViewById(R.id.singleEventEmailListField);
        eventType =(TextView) findViewById(R.id.singleEventEventTypeField);
        note =(TextView) findViewById(R.id.singleEventNoteField);

        editButton = (Button) findViewById(R.id.singleEventEditButton);
        shareButton = (Button) findViewById(R.id.singleEventShareButton);

        editButton.setOnClickListener(this);
        shareButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.singleEventEditButton:
                break;
            case R.id.singleEventShareButton:
                break;
        }
    } //end of onClick method

}
