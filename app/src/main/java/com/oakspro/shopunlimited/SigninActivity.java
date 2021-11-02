package com.oakspro.shopunlimited;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SigninActivity extends AppCompatActivity {

    EditText mobileEd, passwordEd;
    String api_signin="https://oakspro.com/projects/project35/deepu/shopUnlimited/signin_api.php";
    Button signinBtn;
    ProgressDialog progressDialog;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        //set ids
        mobileEd=findViewById(R.id.phon_ed);
        passwordEd=findViewById(R.id.password_ed);
        signinBtn=findViewById(R.id.signin_btn);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        preferences=getSharedPreferences("MyLogin", 0);
        editor=preferences.edit();

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_mobile=mobileEd.getText().toString();
                String user_pass=passwordEd.getText().toString();

                if(!TextUtils.isEmpty(user_mobile) && !TextUtils.isEmpty(user_pass)){
                    if(user_mobile.length()==10){
                        loginAuth(user_mobile, user_pass);
                    }else{
                        Toast.makeText(SigninActivity.this, "Please Enter Valid 10 Digit Mobile Number", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(SigninActivity.this, "Please Enter Valid Credentials", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void loginAuth(String user_mobile, String user_pass) {

        progressDialog.show();

        StringRequest request=new StringRequest(Request.Method.POST, api_signin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String status=jsonObject.getString("status");
                    if (status.equals("Success")){

                        editor.putBoolean("isLogged", true);
                        editor.putString("name", jsonObject.getString("name"));
                        editor.putString("email", jsonObject.getString("email"));
                        editor.putString("mobile", jsonObject.getString("mobile"));
                        editor.putString("address", jsonObject.getString("address"));
                        editor.commit();

                        Toast.makeText(SigninActivity.this, "Welcome to Shop Unlimited", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(SigninActivity.this, ShopActivity.class);
                        startActivity(intent);
                        finish();
                    }else if(status.equals("Failed")){
                        Toast.makeText(SigninActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }else if(status.equals("Invalid")){
                        Toast.makeText(SigninActivity.this, "Invalid username or Password", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SigninActivity.this, "Please Check the internet", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data=new HashMap<>();
                data.put("mobile", user_mobile);
                data.put("password", user_pass);
                return data;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void open_signup(View view) {
        Intent intent=new Intent(SigninActivity.this, SignupActivity.class);
        startActivity(intent);
    }
}