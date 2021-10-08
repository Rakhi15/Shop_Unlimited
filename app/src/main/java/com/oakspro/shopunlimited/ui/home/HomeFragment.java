package com.oakspro.shopunlimited.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.oakspro.shopunlimited.CategoryAdapter;
import com.oakspro.shopunlimited.CategoryModel;
import com.oakspro.shopunlimited.R;
import com.oakspro.shopunlimited.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    RecyclerView recyclerView;
    ArrayList<CategoryModel> arrayList=new ArrayList<>();
    CategoryAdapter adapter;
    private String cat_api="";

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



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}