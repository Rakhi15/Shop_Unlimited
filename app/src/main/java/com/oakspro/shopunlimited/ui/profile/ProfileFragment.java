package com.oakspro.shopunlimited.ui.profile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.oakspro.shopunlimited.R;
import com.oakspro.shopunlimited.databinding.FragmentNotificationsBinding;

public class ProfileFragment extends Fragment {


    private FragmentNotificationsBinding binding;
    EditText userEd, mobileEd, emailEd, addressEd;
    Button updateBtn;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        preferences=getActivity().getSharedPreferences("MyLogin", 0);
        editor=preferences.edit();

        final TextView textView = binding.textNotifications;

        userEd=(EditText)root.findViewById(R.id.username_ed);
       // userEd=root.findViewById(R.id.username_ed);
        mobileEd=root.findViewById(R.id.mobile_ed);
        emailEd=root.findViewById(R.id.email_ed);
        addressEd=root.findViewById(R.id.address_ed);
        updateBtn=root.findViewById(R.id.btn22);



        userEd.setText(preferences.getString("name", null));
        mobileEd.setText(preferences.getString("mobile", null));
        emailEd.setText(preferences.getString("email", null));
        addressEd.setText(preferences.getString("address", null));

        userEd.setEnabled(false);
        mobileEd.setEnabled(false);
        emailEd.setEnabled(false);
        addressEd.setEnabled(false);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}