package com.oakspro.shopunlimited;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    Context context;
    ArrayList<CategoryModel> arrayList;
    private String img_address="https://oakspro.com/projects/project35/deepu/shopUnlimited/category_pics/";

    public CategoryAdapter(Context context, ArrayList<CategoryModel> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel model=arrayList.get(position);
        holder.catName.setText(model.getCat_name());

        Picasso.get().load(img_address+model.getCat_pic()).into(holder.catImg);

        holder.catCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cat_id=model.getCat_id();
                Intent intent=new Intent(context, ProductsActivity.class);
                intent.putExtra("catID", cat_id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView catImg;
        TextView catName;
        CardView catCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            catImg=itemView.findViewById(R.id.cat_img);
            catName=itemView.findViewById(R.id.cat_name);
            catCard=itemView.findViewById(R.id.card_item);
        }
    }
}


/*


<?php

include 'connection.php';

if($_SERVER["REQUEST_METHOD"]=="POST"){

	$pack=$_POST["package"];
	$status="1";

	$details['category']=array();
	if($pack=="com.oakspro.shopunlimited"){


		$sqlcmd="SELECT * FROM SU_category WHERE status='$status' ";
		$res=mysqli_query($con, $sqlcmd);



		while($row=mysqli_fetch_assoc($res)){

			$index['cat_id']=$row['cat_id'];
			$index['cat_name']=$row['cat_name'];
			$index['cat_pic']=$row['cat_pic'];

			array_push($details['category'], $index);

		}

		$details['status']="1";
		echo json_encode($details);


	}else{

		$details['status']="0";
		echo json_encode($details);
	}



}else{

echo "NO PERMISSION";
}



?>


 */
