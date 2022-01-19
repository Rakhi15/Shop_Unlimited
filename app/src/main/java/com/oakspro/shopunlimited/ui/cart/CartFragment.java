package com.oakspro.shopunlimited.ui.cart;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.oakspro.shopunlimited.CartAdapter;
import com.oakspro.shopunlimited.CartData;
import com.oakspro.shopunlimited.R;
import com.oakspro.shopunlimited.databinding.FragmentCartBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartFragment extends Fragment {


    private FragmentCartBinding binding;
    RecyclerView recyclerView;
    ArrayList<CartData> arrayList=new ArrayList<>();
    CartAdapter adapter;
    private String cart_list_api="https://oakspro.com/projects/project35/deepu/shopUnlimited/cart_list_api.php";
    SharedPreferences preferences;
    String x;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView=root.findViewById(R.id.recyclerView_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        preferences = getActivity().getSharedPreferences("MyLogin", 0);
        x=preferences.getString("userid",null);
        getCartProductsFromServer();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void getCartProductsFromServer( ) {

        StringRequest request=new StringRequest(Request.Method.POST, cart_list_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String status=jsonObject.getString("status");
                    if (status.equals("1")){

                        arrayList.clear();
                        JSONArray jsonArray=jsonObject.getJSONArray("cart");
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject object=jsonArray.getJSONObject(i);

                            CartData cproductData=new CartData();
                            cproductData.setCartPname(object.getString("pname"));
                            cproductData.setCartId(object.getString("cart_id"));
                            cproductData.setCartPimg(object.getString("pimg"));
                            cproductData.setCartPid(object.getString("pid"));
                            cproductData.setCartPrice(object.getString("price"));
                            cproductData.setCartSubtotal(object.getString("subtotal"));
                            cproductData.setCartQty(object.getString("qty"));
                            arrayList.add(cproductData);
                        }
                        adapter=new CartAdapter(getContext(), arrayList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "error"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "error"+ error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> cat=new HashMap<>();
                cat.put("userid",x);
                cat.put("package",getActivity().getPackageName().toString());
                return cat;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }
}