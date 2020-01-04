package com.example.eventz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import communication.CVolley;
import communication.Iresponser;
import storage.UserSettings;
import utility.CustomProgress;
import utility.CustomToast;
import utility.DateTimeUtils;

public class AddEvent extends AppCompatActivity implements View.OnClickListener, Iresponser {

    private EditText et_eventname;
    private EditText et_date;
    private EditText et_time;
    private EditText et_date2;
    private EditText et_namee;
    private CheckBox cb_dkyear;
    private CheckBox cb_setalarm;
    private Button bt_addevent;
    private int mYear, mMonth, mDay, mHour, mMinitue;
    private String selectedDate1;
    private String selectedDate2;
    private String selectedTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        et_eventname = findViewById(R.id.et_eventname);
        et_namee = findViewById(R.id.et_namee);
        cb_dkyear = findViewById(R.id.cb_dkyear);
        cb_setalarm = findViewById(R.id.cb_setAlarm);


        //bt_date=view.findViewById(R.id.bt_date);
        et_date = findViewById(R.id.et_date);
        et_date2 = findViewById(R.id.et_date2);
        // bt_time=view.findViewById(R.id.bt_time);
        et_time = findViewById(R.id.et_time);
        bt_addevent = findViewById(R.id.bt_addevent);
        cb_dkyear.setOnClickListener(this);
        cb_setalarm.setOnClickListener(this);
        et_date.setOnClickListener(this);
        et_date2.setOnClickListener(this);
        et_time.setOnClickListener(this);
        bt_addevent.setOnClickListener(this);
        CVolley.getInstance().setResponser(this);


    }

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void showTimePicker() {
        final Calendar c = Calendar.getInstance();
        int hr = c.get(Calendar.HOUR);
        int min = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                selectedTime = timePicker.getHour() + ":" + timePicker.getMinute();
                et_time.setText(selectedTime);
            }
        }, hr, min, true);
        timePickerDialog.show();
    }

    private void showDatePicker1() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        selectedDate1 = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        ;
                        et_date.setText(selectedDate1);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void showDatePicker2() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        selectedDate2 = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        et_date2.setText(selectedDate2);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }





    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_date:
                // Get Current Date
                if (cb_dkyear.isChecked()) {
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    selectedDate1 = dayOfMonth + "-" + (monthOfYear + 1);
                                    et_date.setText(selectedDate1);
                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();

                } else {
                    showDatePicker1();
                }
            case R.id.et_date2:
                if (cb_setalarm.isChecked()) {
                   /* String result = "";
                    result = et_date.getText().toString();
                    et_date2.setText(result);*/

                    showDatePicker2();
                    break;
                }
            case R.id.et_time:
                if (cb_setalarm.isChecked()) {
                    showTimePicker();
                    break;


                }
            case R.id.bt_addevent:
                String event_name = et_eventname.getText().toString();
                String pname = et_namee.getText().toString();
                String user_id= String.valueOf(UserSettings.getInstance().getID());
                //CustomToast.show(user_id);
                String url = "https://medminder.000webhostapp.com/EventReminder/addevent.php";
                Map<String, String> params = new HashMap<>();
                params.put("event_name", event_name);
                params.put("pname", pname);
                params.put("pdate", selectedDate1);
                params.put("reminder_date", selectedDate2);
                params.put("reminder_time", selectedTime);
                params.put("user_id",user_id);
                CustomProgress.showProgress(this);
                CVolley.getInstance().process(url, params);
                break;
        }


    }

    @Override
    public void responseReceived(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String success = jsonObject.getString("success");
            String msg = jsonObject.getString("msg");
            if (success.equals("1")) {
                CustomToast.show("Reminder successfully set");
                PendingIntent pintent = PendingIntent.getBroadcast(this, 0, new Intent("com.example.eventreminder.MY_ALARM"), 0);
                AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                String dateTime = selectedDate2 + " " + selectedTime;

                long milisec = DateTimeUtils.getMillisecFromDateTime(dateTime);
                manager.setRepeating(AlarmManager.RTC_WAKEUP, milisec, 10000, pintent);
                startActivity(new Intent(this, Home.class));
                //startActivity(new Intent(AddEvent.this,Home.class));
            } else {
                CustomToast.show("Failed to add reminder");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

