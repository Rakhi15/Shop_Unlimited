package com.oakspro.shopunlimited;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    ArrayList<ProductData> arrayList;
    private String img_address="https://oakspro.com/projects/project35/deepu/shopUnlimited/products_pics/";

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
        holder.prodItemLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PDetailsActivity.class);
                intent.putExtra("pid", model.getProdID());
                intent.putExtra("cid", model.getCatID());
                intent.putExtra("pname", model.getProdName());
                intent.putExtra("pprice", model.getProdPrice());
                intent.putExtra("pdetails", model.getProdDetails());
                intent.putExtra("pstock", model.getProdStock());
                intent.putExtra("pseller", model.getProdSeller());
                intent.putExtra("pimage", model.getProdImage());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView prodImg;
        TextView prodName, prodPrice;
        LinearLayout prodItemLL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            prodImg=itemView.findViewById(R.id.product_img);
            prodName=itemView.findViewById(R.id.product_name);
            prodPrice=itemView.findViewById(R.id.product_price);
            prodItemLL=itemView.findViewById(R.id.prod_item_ll);

        }
    }
}
