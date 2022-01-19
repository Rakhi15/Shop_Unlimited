package com.oakspro.shopunlimited;

import static com.android.volley.VolleyLog.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.oakspro.shopunlimited.ui.cart.CartFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context context;
    ArrayList<CartData> arrayList;
    private String img_address="https://oakspro.com/projects/project35/deepu/shopUnlimited/products_pics/";
    private String api="https://oakspro.com/projects/project35/deepu/shopUnlimited/delete_item.php";

    public CartAdapter(Context context, ArrayList<CartData> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartData model=arrayList.get(position);
        holder.cprodName.setText(model.getCartPname());
        holder.cprodPrice.setText("Price: Rs. "+model.getCartPrice());
        holder.cprodquantity.setText("Quantity: "+model.getCartQty());
        holder.csubtotal.setText("Sub Total: "+model.getCartSubtotal());
        Picasso.get().load(img_address+model.getCartPimg()).into(holder.cprodImg);
        holder.cproddelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cartid=model.getCartId();
                delete_item_from_cart(cartid, holder.getAdapterPosition());
            }
        });
    }

    private void delete_item_from_cart(String cartid, int position) {
        StringRequest request=new StringRequest(Request.Method.POST,api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object=new JSONObject(response);
                    String res=object.getString("item");

                    if(res.equals("1")){
                        Toast.makeText(context, "Item has deleted", Toast.LENGTH_SHORT).show();
                        removeAt(position);

                    }else{
                        Toast.makeText(context, "Item not deleted", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "ERRor"+error, Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
                  map.put("catid",cartid) ;
                  return map;
            }
        };
        RequestQueue object = Volley.newRequestQueue(context);
        object.add(request);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView cprodImg,cproddelete;
        TextView cprodName, cprodPrice,csubtotal,cprodquantity;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cprodImg=itemView.findViewById(R.id.cproduct_img);
            cproddelete=itemView.findViewById(R.id.cproduct_delete);
            cprodName=itemView.findViewById(R.id.cproduct_name);
            cprodPrice=itemView.findViewById(R.id.cproduct_price);
            csubtotal=itemView.findViewById(R.id.cproduct_subtotal);
            cprodquantity=itemView.findViewById(R.id.cproduct_quantity);
        }
    }

    public void removeAt(int position) {
        arrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, arrayList.size());
    }

}
