package storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.eventz.MyApplication;

public class UserSettings {

    private static UserSettings _instance = null;
    private static SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "ServerDemo";
    private static SharedPreferences.Editor editor;

    private static String KEY_USER_NAME = "UserName";
    private static String KEY_USER_PASSWORD = "Password";
    private static String KEY_USER_ID = "user_id";


    private UserSettings() {
        sharedPreferences = MyApplication.context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }



    public static UserSettings getInstance() {
        if (_instance == null) {
            _instance = new UserSettings();
        }
        return _instance;
    }

    public void remove(){

        editor.clear();
        editor.commit();
    }

    public void setID(int id){

        editor.putInt(KEY_USER_ID,id);
        editor.commit();
    }

    public int getID(){

        int id=sharedPreferences.getInt(KEY_USER_ID,0);
        return id;
    }

    public void setName(String name) {
        editor.putString(KEY_USER_NAME, name);
        editor.commit();
    }

    public void setPassword(String password) {
        editor.putString(KEY_USER_PASSWORD, password);
        editor.commit();
    }


    public String getName() {
        String name = sharedPreferences.getString(KEY_USER_NAME, null);
        return name;
    }

    public String getPassword() {
        String password = sharedPreferences.getString(KEY_USER_PASSWORD, null);
        return password;
    }
}
