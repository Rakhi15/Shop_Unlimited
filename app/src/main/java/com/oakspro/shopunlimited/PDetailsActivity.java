package com.oakspro.shopunlimited;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PDetailsActivity extends AppCompatActivity {


    TextView prodname, prodprice, proddetails, pseller, pstock;
    ImageView pimg;
    Button addcart;
    String pid, cid, pname, pdetails, pprice, pstockS, psellerS, pimgS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdetails);
        //set ids

        prodname=findViewById(R.id.prod_name);
        prodprice=findViewById(R.id.prod_price);
        proddetails=findViewById(R.id.prod_details);
        pseller=findViewById(R.id.prod_seller);
        pstock=findViewById(R.id.prod_stock);
        pimg=findViewById(R.id.prod_img);
        addcart=findViewById(R.id.addcart_btn);


        pid=getIntent().getStringExtra("pid").toString();
        cid=getIntent().getStringExtra("cid").toString();
        pname=getIntent().getStringExtra("pname").toString();
        pdetails=getIntent().getStringExtra("pdetails").toString();
        pprice=getIntent().getStringExtra("pprice").toString();
        pstockS=getIntent().getStringExtra("pstock").toString();
        psellerS=getIntent().getStringExtra("pseller").toString();
        pimgS=getIntent().getStringExtra("pimage").toString();

        prodname.setText(pname);
        Picasso.get().load("https://oakspro.com/projects/project35/deepu/shopUnlimited/products_pics/"+pimgS).into(pimg);
        prodprice.setText("Rs. "+pprice);
        proddetails.setText("Details: "+pdetails);
        pseller.setText("Seller: "+psellerS);
        pstock.setText("Stock: "+pstockS);



    }

    public void go_back(View view) {
        Intent intent=new Intent(PDetailsActivity.this, ShopActivity.class);
        startActivity(intent);
    }
}