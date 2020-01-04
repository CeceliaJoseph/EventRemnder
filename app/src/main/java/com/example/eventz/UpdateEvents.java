package com.example.eventz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import utility.CustomProgress;
import utility.DateTimeUtils;

public class UpdateEvents extends AppCompatActivity implements View.OnClickListener {

    private EditText et_update_eventname;
    private EditText et_update_date;
    private EditText et_update_time;
    private EditText et_update_date2;
    private EditText et_update_namee;
    private Button bt_update_addevent,bt_update_delete;
    private int mYear, mMonth, mDay, mHour, mMinitue;
    private String selectedDate1;
    private String selectedDate2;
    private String selectedTime;
    private String url = "https://medminder.000webhostapp.com/EventReminder/update_event.php";
    private String url_delete = "https://medminder.000webhostapp.com/EventReminder/delete_event.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_events);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        et_update_eventname = findViewById(R.id.et_update_eventname);
        et_update_namee = findViewById(R.id.et_update_namee);
        et_update_date = findViewById(R.id.et_update_date);
        et_update_date2 = findViewById(R.id.et_update_date2);
        et_update_time = findViewById(R.id.et_update_time);
        bt_update_addevent = findViewById(R.id.bt_update_addevent);
        bt_update_delete=findViewById(R.id.bt_update_delete);
        et_update_date.setOnClickListener(this);
        et_update_date2.setOnClickListener(this);
        et_update_time.setOnClickListener(this);
        bt_update_addevent.setOnClickListener(this);
        bt_update_delete.setOnClickListener(this);
        // CVolley.getInstance().setResponser(this);

        Intent intent=getIntent();
        String id=String.valueOf(intent.getIntExtra("id",0));
        //CustomToast.show(id);
        String eventname=intent.getStringExtra("event_name");
        String pname=intent.getStringExtra("pname");
        String pdate=intent.getStringExtra("pdate");
        String reminder_date=intent.getStringExtra("reminder_date");
        String reminder_time=intent.getStringExtra("reminder_time");

        et_update_eventname.setText(eventname);
        et_update_namee.setText(pname);
        et_update_date.setText(pdate);
        et_update_date2.setText(reminder_date);
        et_update_time.setText(reminder_time);


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
                et_update_time.setText(selectedTime);
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
                        et_update_date.setText(selectedDate1);
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
                        et_update_date2.setText(selectedDate2);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }



    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.et_update_date:

                showDatePicker1();
                break;


            case R.id.et_update_date2:
                showDatePicker2();
                break;

            case R.id.et_update_time:
                showTimePicker();
                break;

            case R.id.bt_update_addevent:

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            CustomProgress.dismiss();
                            if (success.equals("1")) {


                                Toast.makeText(UpdateEvents.this, "Reminder Successfully Set", Toast.LENGTH_LONG).show();
                                PendingIntent pintent = PendingIntent.getBroadcast(UpdateEvents.this, 0, new Intent("com.example.eventreminder.MY_ALARM"), 0);
                                AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                                String dateTime = selectedDate2 + " " + selectedTime;

                                long milisec = DateTimeUtils.getMillisecFromDateTime(dateTime);
                                manager.setRepeating(AlarmManager.RTC_WAKEUP, milisec, 10000, pintent);

                                startActivity(new Intent(UpdateEvents.this,Home.class));
                                //getSupportFragmentManager().beginTransaction().replace(R.id.list_container, new ReminderList()).commit();

                            } else {

                                Toast.makeText(UpdateEvents.this, "Failed to set reminder..please try again", Toast.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(UpdateEvents.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Intent intent=getIntent();
                        String id=String.valueOf(intent.getIntExtra("id",0));
                        String event_name = et_update_eventname.getText().toString();
                        String pname = et_update_namee.getText().toString();

                        Map<String, String> params = new HashMap<>();
                        params.put("id",id);
                        params.put("event_name", event_name);
                        params.put("pname", pname);
                        params.put("pdate", selectedDate1);
                        params.put("reminder_date", selectedDate2);
                        params.put("reminder_time", selectedTime);

                        return params;
                    }
                };
                CustomProgress.showProgress(UpdateEvents.this);

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(request);


                break;

            case R.id.bt_update_delete:

                StringRequest request1 = new StringRequest(Request.Method.POST, url_delete, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            final  String success = jsonObject.getString("success");
                            CustomProgress.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(UpdateEvents.this);
                            builder.setMessage("Are you sure you want to delete ?")
                                    .setCancelable(true)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            if (success.equals("1")) {


                                                Toast.makeText(UpdateEvents.this, "Record Successfully Deleted", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(UpdateEvents.this, Home.class));
                                                // getSupportFragmentManager().beginTransaction().replace(R.id.list_container, new ReminderList()).commit();


                                            } else {

                                                Toast.makeText(UpdateEvents.this, "Failed to set Delete..please try again", Toast.LENGTH_LONG).show();

                                            }

                                        }

                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            dialogInterface.dismiss();

                                        }
                                    });

                            AlertDialog alertDialog=builder.create();
                            alertDialog.setTitle("Delete");
                            alertDialog.show();
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(UpdateEvents.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Intent intent=getIntent();
                        String id=String.valueOf(intent.getIntExtra("id",0));
                        Map<String, String> params = new HashMap<>();
                        params.put("id",id);
                        return params;
                    }
                };
                CustomProgress.showProgress(UpdateEvents.this);

                RequestQueue requestQueue1 = Volley.newRequestQueue(this);
                requestQueue1.add(request1);
                break;

        }

    }

}