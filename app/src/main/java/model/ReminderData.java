package model;

public class ReminderData {
    private int rid;
    private String event_name;
    private String pname;
    private String pdate;
    private String reminder_date;
    private String reminder_time;

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getevent_name() {
        return event_name;
    }

    public void setevent_name(String event_name) {
        this.event_name = event_name;
    }
    public String getPname(){return pname;}

    public void setPname(String pname){
        this.pname=pname;
    }
    public String getPdate(){return pdate;}

    public void setPdate(String pdate){
        this.pdate=pdate;
    }


    public String getReminder_date() {
        return reminder_date;
    }

    public void setReminder_date(String reminder_date) {
        this.reminder_date = reminder_date;
    }

    public String getReminder_time() {
        return reminder_time;
    }

    public void setReminder_time(String reminder_time) {
        this.reminder_time = reminder_time;
    }

}
