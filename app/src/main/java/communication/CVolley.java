package communication;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eventz.MyApplication;


import java.util.Map;

import utility.CustomProgress;

public class CVolley {

    private Iresponser responser;

    private static CVolley _instance = null;
    private static RequestQueue queue;

    private CVolley() {

    }

    public static CVolley getInstance() {
        if (_instance == null) {
            _instance = new CVolley();
            queue = Volley.newRequestQueue(MyApplication.context);
        }
        return _instance;
    }

    public void setResponser(Iresponser responser) {
        this.responser = responser;
    }


    public void process(String url, final Map<String, String> params) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    responser.responseReceived(response);
                    CustomProgress.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //CustomToast.show(error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    return params;
                }
            };

            queue.add(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


