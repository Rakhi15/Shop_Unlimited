package com.oakspro.shopunlimited;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    ArrayList<ProductData> arrayList;
    private String img_address="https://oakspro.com/projects/project35/deepu/shopUnlimited/prod_pics/";

    public ProductAdapter(Context context, ArrayList<ProductData> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductData model=arrayList.get(position);
        holder.prodName.setText(model.getProdName());
        holder.prodPrice.setText("Price: Rs. "+model.getProdPrice());
        Picasso.get().load(img_address+model.getProdImage()).into(holder.prodImg);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView prodImg;
        TextView prodName, prodPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            prodImg=itemView.findViewById(R.id.product_img);
            prodName=itemView.findViewById(R.id.product_name);
            prodPrice=itemView.findViewById(R.id.product_price);

        }
    }
}
