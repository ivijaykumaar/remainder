package com.atom.remainder;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.atom.remainder.MoreActionAct.count;

/**
 * Created by user on 1/22/2017.
 */


public class NewEventAct extends AppCompatActivity {

    Switch oNoFFToggle;
    RelativeLayout fromLayout, toLayout;
    LinearLayout moreOptionsLay;
    private boolean isHide = false;
    Button clickOk,cancelBtn,fromTimeBtn;
    DataBaseHelper dataBaseHelper;
    EditText NameEdt,DescriptionEdt,LocationEdt;
    TextView fromDateTxt;
    public static boolean buttonChange = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_event);

        NameEdt = (EditText)findViewById(R.id.event_name_editText);
        fromDateTxt = (TextView)findViewById(R.id.text_FromDate);
        fromTimeBtn = (Button)findViewById(R.id.button_FromTime);
        DescriptionEdt = (EditText)findViewById(R.id.description_EditText);
        LocationEdt = (EditText)findViewById(R.id.location_EditText);
        clickOk = (Button) findViewById(R.id.ok_button);

        cancelBtn = (Button) findViewById(R.id.cancel_button);
        dataBaseHelper = new DataBaseHelper(this);
        fromLayout = (RelativeLayout) findViewById(R.id.from_layout);
        toLayout = (RelativeLayout) findViewById(R.id.to_layout);
        oNoFFToggle = (Switch) findViewById(R.id.allDay_swtich_button);
        moreOptionsLay = (LinearLayout) findViewById(R.id.layout_moreOptions);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                broadCastReceiver();
            }
        });


        if (buttonChange == true){
            clickOk.setText("Ok");
            clickOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (NameEdt.length() != 0){
                        AddData();
                        NameEdt.setText("");
                    }else {
                        Toast.makeText(NewEventAct.this,"Must put event name!!",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }else if (buttonChange == false){
            clickOk.setText("Update");
            clickOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final boolean insertData = dataBaseHelper.UpdateData(count,NameEdt.getText().toString(),fromDateTxt.getText().toString(),
                            fromTimeBtn.getText().toString(),
                            DescriptionEdt.getText().toString(),LocationEdt.getText().toString());

                    if (insertData){
                        Toast.makeText(NewEventAct.this,"Event Update Successfully",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(NewEventAct.this,"Event Discarded",Toast.LENGTH_SHORT).show();

                    }
                    buttonChange = true;
                }
            });
        }
        fromLayout.setVisibility(View.INVISIBLE);
        toLayout.setVisibility(View.INVISIBLE);

        SetFromTime();
        SetToTime();
        ToggleActivate();
        moreOPtionsClick();
        setRemainder();
        setStatus();
        setPrivacy();
        setRepeat();
        setTimezone();
    }

    public void AddData(){

        boolean insertData = dataBaseHelper.addData(NameEdt.getText().toString(),fromDateTxt.getText().toString(),fromTimeBtn.getText().toString(),
                DescriptionEdt.getText().toString(),LocationEdt.getText().toString());

        if (insertData){
            Toast.makeText(NewEventAct.this,"Event Saved Successfully",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(NewEventAct.this,"Event Discarded",Toast.LENGTH_SHORT).show();

        }
    }

    public void ToggleActivate() {

        oNoFFToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (oNoFFToggle.isChecked()) {
                    fromLayout.setVisibility(View.VISIBLE);
                    toLayout.setVisibility(View.VISIBLE);
                } else {
                    fromLayout.setVisibility(View.INVISIBLE);
                    toLayout.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

    public void moreOPtionsClick() {

        final Button moreOPtionsBtn = (Button) findViewById(R.id.button_moreOptions);

        moreOPtionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isHide) {
                    moreOptionsLay.setVisibility(View.VISIBLE);
                    isHide = false;
                } else {
                    moreOptionsLay.setVisibility(View.GONE);
                    isHide = true;
                }
            }
        });
    }

    public void SetFromTime() {

        fromTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int Myhour = calendar.get(Calendar.HOUR);
                int Mymin = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(NewEventAct.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int HOUR, int MINUTE) {

                        fromTimeBtn.setText(HOUR + " : " + MINUTE);
                    }
                }, Myhour, Mymin, false);
                timePickerDialog.show();

                setAlarm(calendar);
            }
        });
        fromDateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment picker = new NewEventAct.MyDatePickerFrom();
                picker.show(getFragmentManager(), "datePicker");
            }
        });
    }
    public void SetToTime() {
        final TextView toDate_text = (TextView) findViewById(R.id.text_toDate);
        final Button toTime_Button = (Button) findViewById(R.id.button_toTime);

        toTime_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                int Myhour = calendar.get(Calendar.HOUR);
                int Mymin = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(NewEventAct.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int HOUR, int MINUTE) {
                        toTime_Button.setText(HOUR + " : " + MINUTE);
                    }
                }, Myhour, Mymin,true);
                timePickerDialog.show();

            }
        });

        toDate_text.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DialogFragment picker = new NewEventAct.MyDatePickerTo();
                picker.show(getFragmentManager(), "datePicker");
            }

        });


    }

    class MyDatePickerFrom extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month , int day) {
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" MMM / dd / yyyy ");
            String tostring = simpleDateFormat.format(c.getTime());
            fromDateTxt.setText(tostring);

        }
    }
    class MyDatePickerTo extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        final TextView toDate_text = (TextView) findViewById(R.id.text_toDate);
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month , int day) {
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" MMM / dd / yyyy ");
            String tostring = simpleDateFormat.format(c.getTime());
            toDate_text.setText(tostring);


        }
    }
    public void setRemainder(){
        final String ok = null,cancel = null;
        RelativeLayout remainderLyt = (RelativeLayout)findViewById(R.id.remainder_layout);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final ArrayList<Integer> remainderList = new ArrayList<Integer>();
        remainderLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("");
                builder.show();
                builder.setMultiChoiceItems(R.array.remainderItems, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                        if(isChecked){
                            remainderList.add(i);
                        }else if(remainderList.contains(i)){
                            remainderList.remove(Integer.valueOf(i));

                        }
                    }
                });
                builder.setPositiveButton(ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
            }

        });
    }
    public void setStatus(){
        final String[] statusList ={"Busy","Available"};
        final Button statusBtn = (Button)findViewById(R.id.button_status);
        RelativeLayout statusLyt = (RelativeLayout)findViewById(R.id.status_layout);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        statusLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("");
                builder.show();
                builder.setMultiChoiceItems(statusList, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                        if(isChecked){
                            statusBtn.setText(statusList[i]);
                        }
                    }
                });
            }
        });
    }
    public void setPrivacy(){

        final String[] privacyList = {"Default","Friends","Family","Only Me",};
        final Button privacyBtn = (Button)findViewById(R.id.button_privacy);
        RelativeLayout privacyLyt = (RelativeLayout)findViewById(R.id.privacy_layout);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        privacyLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("");
                builder.show();
                builder.setMultiChoiceItems(privacyList, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                        if(isChecked){
                            privacyBtn.setText(privacyList[i]);

                        }
                    }
                });

            }

        });
    }
    public void setRepeat(){

        RelativeLayout repeatLyt = (RelativeLayout)findViewById(R.id.repeat_layout);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final ArrayList<Integer> repeatList = new ArrayList<Integer>();
        repeatLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("");
                builder.show();
                builder.setMultiChoiceItems(R.array.repeatItems, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                        if(isChecked){
                            repeatList.add(i);
                        }else if(repeatList.contains(i)){
                            repeatList.remove(Integer.valueOf(i));
                        }

                    }
                });

            }

        });
    }

    public void setTimezone(){

        RelativeLayout Timezone = (RelativeLayout)findViewById(R.id.timezone_layout);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final ArrayList<Integer> timeZoneList = new ArrayList<Integer>();
        Timezone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("");
                builder.show();
                builder.setMultiChoiceItems(R.array.timeZoneItems, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                        if(isChecked){
                            timeZoneList.add(i);
                        }else if(timeZoneList.contains(i)){
                            timeZoneList.remove(Integer.valueOf(i));
                        }

                    }
                });

            }

        });
    }

    public void setAlarm(Calendar cal){

        int value = Integer.parseInt((fromTimeBtn.getText().toString()));
        Intent intent = new Intent (this,Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(),234324243,intent,0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(alarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);
        Toast.makeText(this,"Alarmsetted",Toast.LENGTH_SHORT).show();

    }
}

