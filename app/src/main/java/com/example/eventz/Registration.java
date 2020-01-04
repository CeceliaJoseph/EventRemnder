package com.example.eventz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import communication.CVolley;
import communication.Iresponser;
import utility.CustomProgress;
import utility.CustomToast;

public class Registration extends AppCompatActivity implements View.OnClickListener, Iresponser {

    private EditText et_name;
    private EditText et_pass;
    private EditText et_fname;
    private EditText et_email;
    private EditText et_phno;
    private EditText et_cpass;

    private Button bt_signup2;
    private TextView tv_signup2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        et_name=findViewById(R.id.et_name);
        et_pass=findViewById(R.id.et_pass);

        et_fname=findViewById(R.id.et_fname);
        et_email=findViewById(R.id.et_email);
        et_phno=findViewById(R.id.et_phno);
        et_cpass=findViewById(R.id.et_cpass);

        bt_signup2=findViewById(R.id.bt_signup2);
        tv_signup2=findViewById(R.id.tv_signup2);
        bt_signup2.setOnClickListener(this);
        tv_signup2.setOnClickListener(this);
        CVolley.getInstance().setResponser(this);
    }


    @Override
    public void onClick(View view) {switch (view.getId())
    {
        case R.id.bt_signup2:
            String full_name=et_fname.getText().toString();
            String username = et_name.getText().toString();
            String email=et_email.getText().toString();
            String mobile_no=et_phno.getText().toString();
            String  pass =et_pass.getText().toString();
            String confrimpass=et_cpass.getText().toString();
            if(!pass.equals(confrimpass))
            {
                Toast.makeText(this,"Password does not Match",Toast.LENGTH_SHORT
                ).show();
            }else
            {
                String url="http://192.168.43.13/EventReminder/reg.php";
                Map<String,String> params=new HashMap<>();
                params.put("full_name",full_name);
                params.put("username",username);
                params.put("email",email);
                params.put("mobile_no",mobile_no);
                params.put("pass",pass);
                CustomProgress.showProgress(this);
                CVolley.getInstance().process(url,params);

            }
            break;

        case R.id.tv_signup2:
            Intent intent=new Intent(this, Login.class);
            startActivity(intent);


    }


    }

    @Override
    public void responseReceived(String response) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            String success=jsonObject.getString("success");
            String msg=jsonObject.getString("msg");
            if(success.equals("1"))
            {
                CustomToast.show("Registration Succesfull! Please Login to Continue");
                startActivity(new Intent(Registration.this,Login.class));
                finish();
            }
            else {
                CustomToast.show("Registration Failed");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}

