package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.eventz.R;
import com.example.eventz.ReminderList;

import java.util.ArrayList;

import model.ReminderData;


public class ReminderAdapter extends BaseAdapter  {
    private ArrayList<ReminderData> list;
    private ReminderList activity;
    private LayoutInflater inflater;

    public ReminderAdapter(ReminderList activity) {
        this.activity = activity;
        inflater = activity.getLayoutInflater();
    }

    public void setList(ArrayList<ReminderData> list) {
        this.list = list;
    }


    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }

        return 0;
}



    @Override
    public Object getItem(int position) {

        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view=inflater.inflate(R.layout.row1,null);
        ReminderData data = list.get(position);
        TextView tv_eventname = view.findViewById(R.id.tv_eventname);
        TextView tv_pname = view.findViewById(R.id.tv_pname);
        TextView tv_pdate=view.findViewById(R.id.tv_pdate);




        tv_eventname.setText(data.getevent_name());
        tv_pname.setText(data.getPname());
        tv_pdate.setText(data.getPdate());

        return view;
    }


}
