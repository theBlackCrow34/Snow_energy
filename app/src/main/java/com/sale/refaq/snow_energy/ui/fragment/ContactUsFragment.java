package com.sale.refaq.snow_energy.ui.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sale.refaq.snow_energy.R;
import com.sale.refaq.snow_energy.databinding.FragmentContactUsBinding;
import com.sale.refaq.snow_energy.ui.SendComplaint;
import com.sale.refaq.snow_energy.ui.SendSuggestion;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment {

    FragmentContactUsBinding binding;
    public ContactUsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_contact_us,container,false);
        View view = binding.getRoot();

        binding.sendComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SendComplaint.class));
            }
        });
        binding.sendSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SendSuggestion.class));
            }
        });

        return view;
    }

}
