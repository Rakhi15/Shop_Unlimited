package com.oakspro.shopunlimited;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PDetailsActivity extends AppCompatActivity {


    TextView prodname, prodprice, proddetails, pseller, pstock;
    ImageView pimg;
    Button addcart;
    String pid, cid, pname, pdetails, pprice, pstockS, psellerS, pimgS;
    Button plusBtn, minusBtn;
    EditText qtyEd;
    SharedPreferences preferences;
    String api_add_cart="https://oakspro.com/projects/project35/deepu/shopUnlimited/add_cart_api.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdetails);

        preferences=getSharedPreferences("MyLogin", 0);
        //set ids

        prodname=findViewById(R.id.prod_name);
        prodprice=findViewById(R.id.prod_price);
        proddetails=findViewById(R.id.prod_details);
        pseller=findViewById(R.id.prod_seller);
        pstock=findViewById(R.id.prod_stock);
        pimg=findViewById(R.id.prod_img);
        addcart=findViewById(R.id.addcart_btn);
        minusBtn=findViewById(R.id.minus_btn);
        plusBtn=findViewById(R.id.plus_btn);
        qtyEd=findViewById(R.id.qty_ed);


        pid=getIntent().getStringExtra("pid").toString();
        cid=getIntent().getStringExtra("cid").toString();
        pname=getIntent().getStringExtra("pname").toString();
        pdetails=getIntent().getStringExtra("pdetails").toString();
        pprice=getIntent().getStringExtra("pprice").toString();
        pstockS=getIntent().getStringExtra("pstock").toString();
        psellerS=getIntent().getStringExtra("pseller").toString();
        pimgS=getIntent().getStringExtra("pimage").toString();


        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer qty=Integer.parseInt(qtyEd.getText().toString());
                qty=qty-1;
                if (qty>0){
                    qtyEd.setText(qty.toString());
                }else{
                    Toast.makeText(PDetailsActivity.this, "Minimum 1 Qty required", Toast.LENGTH_SHORT).show();
                }
            }
        });
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer qty=Integer.parseInt(qtyEd.getText().toString());
                qty=qty+1;
                if (qty<21){
                    qtyEd.setText(qty.toString());
                }else{
                    Toast.makeText(PDetailsActivity.this, "Too many qty not acceptable", Toast.LENGTH_SHORT).show();
                }
            }
        });


        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qty_s=qtyEd.getText().toString();

                addToCart(cid, pid, qty_s,pprice, preferences.getString("userid", null));
            }
        });

        prodname.setText(pname);
        Picasso.get().load("https://oakspro.com/projects/project35/deepu/shopUnlimited/products_pics/"+pimgS).into(pimg);
        prodprice.setText("Rs. "+pprice);
        proddetails.setText("Details: "+pdetails);
        pseller.setText("Seller: "+psellerS);
        pstock.setText("Stock: "+pstockS);

    }

    private void addToCart(String cid, String pid, String qty_s, String pprice, String userid) {

        StringRequest request=new StringRequest(Request.Method.POST, api_add_cart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    String status=object.getString("status");
                    if (status.equals("SUCCESS")){
                        Toast.makeText(PDetailsActivity.this, "Added", Toast.LENGTH_SHORT).show();
                    }else if (status.equals("EXISTS")){
                        Toast.makeText(PDetailsActivity.this, "Already Added", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(PDetailsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PDetailsActivity.this, "VError: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data=new HashMap<>();
                data.put("pid", pid);
                data.put("cid", cid);
                data.put("qty", qty_s);
                data.put("price", pprice);
                data.put("userid", userid);
                return data;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);

    }


    public void go_back(View view) {
        Intent intent=new Intent(PDetailsActivity.this, ShopActivity.class);
        startActivity(intent);
    }
}