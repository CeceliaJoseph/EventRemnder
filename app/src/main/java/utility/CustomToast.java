package utility;

import android.widget.Toast;

import com.example.eventz.MyApplication;


public class CustomToast {
    public static void show(String msg)
    {
        Toast.makeText(MyApplication.context,msg,Toast.LENGTH_LONG).show();
    }
}
