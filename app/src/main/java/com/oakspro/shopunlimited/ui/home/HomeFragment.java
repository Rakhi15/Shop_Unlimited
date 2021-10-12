package com.oakspro.shopunlimited.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.oakspro.shopunlimited.CategoryAdapter;
import com.oakspro.shopunlimited.CategoryModel;
import com.oakspro.shopunlimited.R;
import com.oakspro.shopunlimited.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    RecyclerView recyclerView;
    ArrayList<CategoryModel> arrayList=new ArrayList<>();
    CategoryAdapter adapter;
    private String cat_api="https://oakspro.com/projects/project35/deepu/shopUnlimited/category_api.php";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView=root.findViewById(R.id.cat_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        getCategoriesFromServer();




        return root;
    }

    private void getCategoriesFromServer() {
        StringRequest request=new StringRequest(Request.Method.POST, cat_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String status=jsonObject.getString("status");
                    if (status.equals("1")){

                        arrayList.clear();
                        JSONArray jsonArray=jsonObject.getJSONArray("category");
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject object=jsonArray.getJSONObject(i);

                            CategoryModel categoryModel=new CategoryModel();
                            categoryModel.setCat_id(object.getString("cat_id"));
                            categoryModel.setCat_name(object.getString("cat_name"));
                            categoryModel.setCat_pic(object.getString("cat_pic"));
                            arrayList.add(categoryModel);
                        }
                        adapter=new CategoryAdapter(getContext(), arrayList);
                        recyclerView.setAdapter(adapter);


                    }else {
                        Toast.makeText(getContext(), "Invalid Request", Toast.LENGTH_SHORT).show();
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
                cat.put("package", getActivity().getPackageName()); //com.oakspro.shopunlimited
                return cat;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(request);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}