package com.example.eventz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import communication.CVolley;
import communication.Iresponser;
import model.ProfileData;
import storage.UserSettings;
import utility.CustomProgress;
import utility.CustomToast;

public class Profile extends AppCompatActivity {

    private TextView tv_profile_name,tv_profile_mobile,tv_profile_email;
    private static String URL_REG="https://medminder.000webhostapp.com/EventReminder/getProfileData.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_profile_name=findViewById(R.id.tv_profile_name);
        tv_profile_email=findViewById(R.id.tv_profile_email);
        tv_profile_mobile=findViewById(R.id.tv_profile_mobile);





    }

    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();

        StringRequest request=new StringRequest(Request.Method.POST, URL_REG, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);

                        String name=obj.getString("full_name");
                        String email=obj.getString("email");
                        String mobile=obj.getString("mobile_no");

                        ProfileData data=new ProfileData();
                        data.setName(name);
                        data.setMobile(mobile);
                        data.setEmail(email);

                        tv_profile_name.setText(data.getName());
                        tv_profile_mobile.setText(data.getMobile());
                        tv_profile_email.setText(data.getEmail());

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(Profile.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                String id=String.valueOf(UserSettings.getInstance().getID());
                params.put("id", id);
                return params;

            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
}