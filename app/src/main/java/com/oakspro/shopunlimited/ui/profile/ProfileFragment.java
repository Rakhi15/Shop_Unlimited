package com.oakspro.shopunlimited.ui.profile;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.oakspro.shopunlimited.R;
import com.oakspro.shopunlimited.SigninActivity;
import com.oakspro.shopunlimited.SignupActivity;
import com.oakspro.shopunlimited.databinding.FragmentNotificationsBinding;
import com.oakspro.shopunlimited.databinding.FragmentProfileBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {


    private FragmentProfileBinding binding;
    EditText userEd, mobileEd, emailEd, addressEd;
    Button updateBtn;
    String mobile_s="";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private Boolean isEdit=false;
    private String api_link="https://oakspro.com/projects/project35/deepu/shopUnlimited/profile_update_api.php";
    private ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        preferences=getActivity().getSharedPreferences("MyLogin", 0);
        editor=preferences.edit();

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        userEd=(EditText)root.findViewById(R.id.userN_ed);
       // userEd=root.findViewById(R.id.username_ed);
        mobileEd=root.findViewById(R.id.userM_ed);
        emailEd=root.findViewById(R.id.userE_ed);
        addressEd=root.findViewById(R.id.userA_ed);
        updateBtn=root.findViewById(R.id.btn22);



        userEd.setText(preferences.getString("name", "null"));
        mobileEd.setText(preferences.getString("mobile", "null"));
        mobile_s=preferences.getString("mobile", "null");
        emailEd.setText(preferences.getString("email", "null"));
        addressEd.setText(preferences.getString("address", "null"));

        userEd.setEnabled(false);
        mobileEd.setEnabled(false);
        emailEd.setEnabled(false);
        addressEd.setEnabled(false);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isEdit==false){
                    userEd.setEnabled(true);
                    mobileEd.setEnabled(true);
                    emailEd.setEnabled(true);
                    addressEd.setEnabled(true);

                    isEdit=true;
                    updateBtn.setText("Update");
                    updateBtn.setBackgroundResource(R.drawable.button2);
                }else{
                    userEd.setEnabled(false);
                    mobileEd.setEnabled(false);
                    emailEd.setEnabled(false);
                    addressEd.setEnabled(false);

                    isEdit=false;
                    updateBtn.setText("Edit");
                    updateBtn.setBackgroundResource(R.drawable.button_shape);

                    String user_n=userEd.getText().toString();
                    String email_n=emailEd.getText().toString();
                    String mobile_n=mobileEd.getText().toString();
                    String address_n=addressEd.getText().toString();

                    updateProfile(user_n, email_n, mobile_n, address_n, mobile_s);
                }

            }
        });

        return root;
    }

    private void updateProfile(String user_n, String email_n, String mobile_n, String address_n, String mobile_s) {

        progressDialog.show();
        //server logic of upload StringRequest

        StringRequest request_signup=new StringRequest(Request.Method.POST, api_link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    //later

                    String status=jsonObject.getString("status");
                    String message=jsonObject.getString("message");
                    progressDialog.dismiss();
                    showMessage(status, message);



                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Please Check Internet...", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data=new HashMap<>();
                data.put("name", user_n);
                data.put("email", email_n);
                data.put("mobile", mobile_n);
                data.put("password", address_n);
                data.put("userid", mobile_s);
                return data;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(request_signup);

    }
    private void showMessage(String status, String message) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Account Update");
        builder.setMessage("\n"+status+"\n"+message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                editor.clear().commit();
                Intent intent=new Intent(getActivity().getApplicationContext(), SigninActivity.class);
                startActivity(intent);
            }
        });
        builder.create().show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}