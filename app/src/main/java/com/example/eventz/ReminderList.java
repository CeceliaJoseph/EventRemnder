package com.example.eventz;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.ReminderAdapter;
import communication.CVolley;
import communication.Iresponser;
import model.ReminderData;
import storage.UserSettings;
import utility.CustomProgress;

public class ReminderList extends Fragment implements Iresponser, AdapterView.OnItemClickListener {
    private ReminderAdapter adapter=null;
    private ListView lv_reminders;
    private View view;
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        view=inflater.inflate(R.layout.activity_reminder_list,null);
        lv_reminders=view.findViewById(R.id.lv_reminders);
        adapter=new ReminderAdapter(this);
        lv_reminders.setAdapter(adapter);
        lv_reminders.setOnItemClickListener(this);
        CVolley.getInstance().setResponser(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String url = "https://medminder.000webhostapp.com/EventReminder/getReminders.php";
        String id= String.valueOf(UserSettings.getInstance().getID());
        Map<String, String> params = new HashMap<>();
        params.put("id",id);
        CustomProgress.showProgress(getActivity());
        CVolley.getInstance().process(url,params);
    }

    @Override
    public void responseReceived(String response) {
        try {
            JSONObject object = new JSONObject(response);
            JSONArray array = object.getJSONArray("data");
            ArrayList<ReminderData> list = new ArrayList<>();
             for (int i = 0; i < array.length(); i++) {

                JSONObject obj = array.getJSONObject(i);
                int rid = obj.getInt("event_id");
                String event_name = obj.getString("event_name");
                String pname=obj.getString("pname");
                String pdate=obj.getString("pdate");
                String reminder_date = obj.getString("reminder_date");
                String reminder_time = obj.getString("reminder_time");
                ReminderData data = new ReminderData();
                data.setRid(rid);
                data.setevent_name(event_name);
                data.setPname(pname);
                data.setPdate(pdate);
                data.setReminder_date(reminder_date);
                data.setReminder_time(reminder_time);
                list.add(data);

            }
            adapter.setList(list);
            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        Intent intent = new Intent(getActivity(), UpdateEvents.class);
        ReminderData data = (ReminderData) adapter.getItem(position);
        intent.putExtra("id",data.getRid());
        intent.putExtra("event_name", data.getevent_name());
        intent.putExtra("pname", data.getPname());
        intent.putExtra("pdate", data.getPdate());
        intent.putExtra("reminder_date", data.getReminder_date());
        intent.putExtra("reminder_time", data.getReminder_time());
        startActivity(intent);



    }
}

