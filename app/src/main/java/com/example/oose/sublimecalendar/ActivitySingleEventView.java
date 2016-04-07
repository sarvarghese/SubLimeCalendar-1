package com.example.oose.sublimecalendar;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.sql.Time;

public class ActivitySingleEventView extends AppCompatActivity implements View.OnClickListener {

    private TextView name,date,startTime,finishTime,location,emailList, eventType, note;
    private Button editButton, shareButton;
    private Bundle extrasBundle;
    private int selectedEventID;
    private String emList="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //link on getting bundle: http://stackoverflow.com/questions/3913592/start-an-activity-with-a-parameter
        extrasBundle = getIntent().getExtras();

        if( !(extrasBundle.isEmpty()) && (extrasBundle.containsKey("eventID")) ){
            //checks if bundle is empty and if it has the event id
            selectedEventID=extrasBundle.getInt("eventID");
        }
        else{
            //either bundle was empty or did not have parse id. should find a way to go back to previous activity
            //put finish()
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event_view);

        Event e= Event.findById(Event.class,selectedEventID);

        name =(TextView) findViewById(R.id.singleEventNameField);
        date =(TextView) findViewById(R.id.singleEventDateField);
        startTime =(TextView) findViewById(R.id.singleEventStartTimeField);
        finishTime =(TextView) findViewById(R.id.singleEventFinishTimeField);
        location =(TextView) findViewById(R.id.singleEventLocationField);
        emailList =(TextView) findViewById(R.id.singleEventEmailListField);
        eventType =(TextView) findViewById(R.id.singleEventEventTypeField);
        note =(TextView) findViewById(R.id.singleEventNoteField);

        name.setText(e.name);
        date.setText(e.date.toString());
        startTime.setText(e.startTime.toString());
        finishTime.setText(e.finishTime.toString());
        location.setText(e.location);
        emailList.setText(e.emailList);
        emList=e.emailList;
        eventType.setText(e.eventType);
        note.setText(e.eventNote);

        editButton = (Button) findViewById(R.id.singleEventEditButton);
        shareButton = (Button) findViewById(R.id.singleEventShareButton);

        editButton.setOnClickListener(this);
        shareButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent myIntent=null;
        switch (v.getId()){
            case R.id.singleEventEditButton:
                //link on starting new activity:http://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
                myIntent = new Intent(this, ActivityEditEvent.class);
                myIntent.putExtra("eventID", eventType.getId()); //Optional parameters
                this.startActivity(myIntent);
                break;

            case R.id.singleEventShareButton:
                if(emList.compareTo("")==0){
                    Toast.makeText(this,"No emails in email list", Toast.LENGTH_SHORT).show();
                    break;
                }
                //example on sending an email: https://github.com/CAPTAIN713/VitaCheck/blob/master/app/src/main/java/vitacheck/vitacheck/fragments/DoctorFragmentIndividualPage.java
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emList});
                try{
                    startActivity(Intent.createChooser(emailIntent,"Send mail..."));
                }
                catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(this,"Unable to send email", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    } //end of onClick method

}
