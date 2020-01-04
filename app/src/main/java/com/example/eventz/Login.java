package com.example.eventz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import communication.CVolley;
import communication.Iresponser;
import storage.UserSettings;
import utility.CustomProgress;
import utility.CustomToast;

public class Login extends AppCompatActivity implements View.OnClickListener, Iresponser {

    private EditText et_name;
    private EditText et_pass;
    private Button bt_login;
    private TextView tv_signup;


    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_login);

        et_name = findViewById(R.id.et_name);
        et_pass = findViewById(R.id.et_pass);
        bt_login = findViewById(R.id.bt_login);
        tv_signup = findViewById(R.id.tv_signup);
        bt_login.setOnClickListener(this);
        tv_signup.setOnClickListener(this);
        CVolley.getInstance().setResponser(this);

    }


    @Override

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                String username = et_name.getText().toString();
                String pass = et_pass.getText().toString();

                String url = "https://medminder.000webhostapp.com/EventReminder/login.php";
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("pass", pass);
                CustomProgress.showProgress(this);
                CVolley.getInstance().process(url, params);

                break;


            case R.id.tv_signup:
                Intent intent1 = new Intent(this, Registration.class);
                this.startActivity(intent1);

        }
    }

    @Override

    public void responseReceived(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String success = jsonObject.getString("success");
            String msg = jsonObject.getString("msg");
            int id=jsonObject.getInt("data");
            if (success.equals("1")) {
                String username = et_name.getText().toString();
                String pass = et_pass.getText().toString();
                UserSettings.getInstance().setName(username);
                UserSettings.getInstance().setPassword(pass);
                UserSettings.getInstance().setID(id);
                CustomToast.show("Login Succesfull!");
                //String tesid=Integer.toString(id);
                //CustomToast.show(String.valueOf(UserSettings.getInstance().getID()));
                Intent intent = new Intent(this, Home.class);
                this.startActivity(intent);

            } else {
                CustomToast.show("Login Failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

