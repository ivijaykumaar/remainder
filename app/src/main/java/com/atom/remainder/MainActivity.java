package com.atom.remainder;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView Day;
    MaterialCalendarView materialCalendarView;
    private boolean isClicked = false;
    Button viewButton,newEvent_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_main);

        Button more = (Button)findViewById(R.id.more_button);
        newEvent_button = (Button)findViewById(R.id.new_event_button);
        viewButton = (Button)findViewById(R.id.viewButton);
        Day = (TextView)findViewById(R.id.text_day);
        materialCalendarView = (MaterialCalendarView)findViewById(R.id.calendarView);

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,MoreActionAct.class);
                startActivity(intent);
            }
        });

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

//                Toast.makeText(MainActivity.this,date, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this,NewEventAct.class);
                startActivity(intent);

            }
        });
    }

    public void setDateHome(View view) {

        RelativeLayout  setDateHome = (RelativeLayout)findViewById(R.id.setDate_layout);
        setDateHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment picker = new DatePickerFragment();
                picker.show(getFragmentManager(), "datePicker");

            }
        });
    }

    public void NewEventAct(View view) {

        newEvent_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,NewEventAct.class);
                startActivity(intent);
            }
        });

    }

    public void ViewInCalendar(View view) {
        if (isClicked) {
            materialCalendarView.state().edit()
                    .setFirstDayOfWeek(Calendar.MONDAY)
                    .setMinimumDate(CalendarDay.from(1900, 1, 1))
                    .setMaximumDate(CalendarDay.from(2100, 12, 31))
                    .setCalendarDisplayMode(CalendarMode.MONTHS)
                    .commit();
            viewButton.setText("Week");
            isClicked = false;
        }else {
            materialCalendarView.state().edit()
                    .setFirstDayOfWeek(Calendar.MONDAY)
                    .setMinimumDate(CalendarDay.from(1900, 1, 1))
                    .setMaximumDate(CalendarDay.from(2100, 12, 31))
                    .setCalendarDisplayMode(CalendarMode.WEEKS)
                    .commit();
            viewButton.setText("Month");
            isClicked = true;
        }

    }
    class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        final TextView  Day = (TextView)findViewById(R.id.text_day);
        final TextView Month = (TextView)findViewById(R.id.text_month);
        final TextView Year = (TextView)findViewById(R.id.text_year);
        final TextView dateTxt = (TextView)findViewById(R.id.date_text);

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

            SimpleDateFormat sdfMonth = new SimpleDateFormat("MMM");
            String formattedMonth = sdfMonth.format(c.getTime());
            Month.setText(formattedMonth);

            SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
            String formattedYear = sdfYear.format(c.getTime());
            Year.setText(formattedYear);

            SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
            String formattedDay = sdfDay.format(c.getTime());
            Day.setText(formattedDay);

            dateTxt.setText(formattedDay);

        }
    }

}
