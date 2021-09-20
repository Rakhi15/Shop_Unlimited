package com.oakspro.shopunlimited;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    EditText nameEd, emailEd, mobileEd, passwordEd;
    Button signupBtn;
    RadioGroup radioGroup;
    RadioButton gender_radio;
    private String api_link="";
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameEd=findViewById(R.id.name_ed);
        emailEd=findViewById(R.id.email_ed);
        mobileEd=findViewById(R.id.phone_ed);
        passwordEd=findViewById(R.id.password_ed);
        signupBtn=findViewById(R.id.signup_btn);
        radioGroup=findViewById(R.id.gender_radiogp);

        progressDialog=new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String user_name=nameEd.getText().toString();
                String user_email=emailEd.getText().toString();
                String user_mobile=mobileEd.getText().toString();
                String user_pass=passwordEd.getText().toString();

                Integer gender_id=radioGroup.getCheckedRadioButtonId();
                gender_radio=findViewById(gender_id);

                String user_sex=gender_radio.getText().toString();

                if (!TextUtils.isEmpty(user_name) && !TextUtils.isEmpty(user_email) && ! TextUtils.isEmpty(user_mobile) && !TextUtils.isEmpty(user_pass) && !TextUtils.isEmpty(user_sex)){

                    if (user_mobile.length()==10){

                        uploadData(user_name, user_email, user_mobile, user_pass, user_sex);

                    }else{
                        Toast.makeText(SignupActivity.this, "Enter valid 10 digit mobile number", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SignupActivity.this, "Please Enter All Feilds", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }

    private void uploadData(String user_name, String user_email, String user_mobile, String user_pass, String user_sex) {
        progressDialog.show();
        //server logic of upload StringRequest

    }


}