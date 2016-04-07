package com.example.oose.sublimecalendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

public class ActivityEditEvent extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private EditText nameTB,dateTB,locationTB,emailListTB,startTimeTB,finishTimeTB,noteTB;
    private Button saveButton;
    private TextView startTime,finishTime,date;
    private String name,selectedDate="",location,emailList,eventType,selectedStartTime="",selectedFinishTime="",note;
    private Date d;
    private Time st,ft;
    private Bundle extrasBundle;
    private int selectedEventID;

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
        setContentView(R.layout.activity_edit_event);
        nameTB=(EditText) findViewById(R.id.editEventNameTB);
        locationTB=(EditText) findViewById(R.id.editEventLocationTB);
        emailListTB=(EditText) findViewById(R.id.editEventEmailListTB);
        noteTB=(EditText) findViewById(R.id.editEventNoteTB);
        /*code on date and time picker: http://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext */
        date=(TextView) findViewById(R.id.editEventDateTB);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(ActivityEditEvent.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                    /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        selectedDate=("" + selectedmonth + "-" + selectedday + "-" + selectedyear);
                        d=new Date(selectedyear,selectedmonth,selectedday);
                        date.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });
        //startTimeTB=(EditText) findViewById(R.id.editEventStartTimeTB);
        //finishTimeTB=(EditText) findViewById(R.id.editEventFinishTimeTB);
        startTime=(TextView) findViewById(R.id.editEventStartTimeTB);
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ActivityEditEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        selectedStartTime=(selectedHour + ":" + selectedMinute);
                        st=new Time(selectedHour,selectedMinute,0);
                        startTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        finishTime =(TextView) findViewById(R.id.editEventFinishTimeTB);
        finishTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ActivityEditEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        selectedFinishTime=(selectedHour + ":" + selectedMinute);
                        ft=new Time(selectedHour,selectedMinute,0);
                        finishTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        //dateTB=(EditText) findViewById(R.id.editEventDateTB);

        /*sites on spinners: http://developer.android.com/guide/topics/ui/controls/spinner.html
        http://www.tutorialspoint.com/android/android_spinner_control.htm  */
        Spinner spinner = (Spinner) findViewById(R.id.editEventTypeSpinner);
        spinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.eventTypes,android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        Event e= Event.findById(Event.class,selectedEventID);

        saveButton = (Button) findViewById(R.id.editEventSaveButton);
        saveButton.setOnClickListener(this);
        nameTB.setText(e.name);
        dateTB.setText(e.date.toString());
        startTimeTB.setText(e.startTime.toString());
        finishTimeTB.setText(e.finishTime.toString());
        locationTB.setText(e.location);
        emailListTB.setText(e.emailList);
        //eventTypeTB.setText(e.eventType);
        noteTB.setText(e.eventNote);

    }


    @Override
        /*link on how to handle mutiple button clicks
         http://stackoverflow.com/questions/21827046/handle-multiple-button-click-in-view-onclicklistener-in-android*/
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.editEventSaveButton: //if save button was pressed
                //pull the inputed user data from the edit fields, no need to pull the times and dates
                //because they are auto grabed when selected
                name=nameTB.getText().toString();
                location=locationTB.getText().toString();
                emailList=emailListTB.getText().toString();
                note=noteTB.getText().toString();

                //error check; make sure user inputed data into the name, date and time fields
                if(name.compareTo("")==0){
                    Toast.makeText(getApplicationContext(), "Please fill out the name field", Toast.LENGTH_SHORT).show();
                    break;}
                if(selectedDate.compareTo("")==0){
                    Toast.makeText(getApplicationContext(), "Please select the date", Toast.LENGTH_SHORT).show();
                    break;}
                if(selectedStartTime.compareTo("")==0){
                    Toast.makeText(getApplicationContext(), "Please state the start time", Toast.LENGTH_SHORT).show();
                    break;}
                if(selectedFinishTime.compareTo("")==0){
                    Toast.makeText(getApplicationContext(), "Please state the finish time", Toast.LENGTH_SHORT).show();
                    break;}

                try {
                    Event e = new Event(name, d, st, ft, location, emailList, eventType, note);
                    e.save();
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Unable to save event", Toast.LENGTH_SHORT).show();
                    super.onBackPressed(); //same effect as pressing the back button
                    break;
                }

                Toast.makeText(getApplicationContext(), "Saved Changes", Toast.LENGTH_SHORT).show();
                super.onBackPressed(); //same effect as pressing the back button
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // An item was selected. You can retrieve the selected item using
        String item = parent.getItemAtPosition(position).toString();
        eventType=item;
        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback. Required method for spinner.
    }

}
