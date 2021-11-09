package com.oakspro.shopunlimited;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ProductData> arrayList=new ArrayList<>();
    ProductAdapter adapter;
    private String prod_api="https://oakspro.com/projects/project35/deepu/shopUnlimited/products_list_api.php";
    private String categoryID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        recyclerView=findViewById(R.id.cat_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        categoryID=getIntent().getStringExtra("catID");
        getProductsServer(categoryID);

    }

    private void getProductsServer(String categoryID) {

        StringRequest request=new StringRequest(Request.Method.POST, prod_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String status=jsonObject.getString("status");
                    if (status.equals("1")){

                        arrayList.clear();
                        JSONArray jsonArray=jsonObject.getJSONArray("products");
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject object=jsonArray.getJSONObject(i);

                            ProductData productData=new ProductData();
                            productData.setProdID(object.getString("prod_id"));
                            productData.setProdPrice(object.getString("prod_price"));
                            productData.setProdDetails(object.getString("prod_details"));
                            productData.setProdName(object.getString("prod_name"));
                            productData.setProdSeller(object.getString("prod_seller"));
                            productData.setProdStock(object.getString("prod_stock"));
                            productData.setCatID(object.getString("cat_id"));
                            productData.setProdImage(object.getString("prod_image"));
                            arrayList.add(productData);
                        }
                        adapter=new ProductAdapter(ProductsActivity.this, arrayList);
                        recyclerView.setAdapter(adapter);

                    }else {
                        Toast.makeText(ProductsActivity.this, "Invalid Request", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> cat=new HashMap<>();
                cat.put("catID", categoryID);
                return cat;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}