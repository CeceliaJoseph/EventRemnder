package utility;

import android.app.ProgressDialog;
import android.content.Context;

public class CustomProgress {
    public static ProgressDialog dialog=null;

    public static void showProgress(Context context)
    {
        dialog= new ProgressDialog(context);
        dialog.setMessage("Please Wait....");
        dialog.show();

    }
    public static void dismiss(){if(dialog!=null){
    dialog.dismiss();}
    }

}
