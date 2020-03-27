package com.sale.refaq.snow_energy.ui.fragment;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sale.refaq.snow_energy.R;
import com.sale.refaq.snow_energy.databinding.FragmentHomeBinding;
import com.sale.refaq.snow_energy.pojo.CompanyModel;
import com.sale.refaq.snow_energy.ui.adapter.CompanyAdapter;
import com.sale.refaq.snow_energy.ui.view_model.CompanyViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }
    FragmentHomeBinding binding;
    CompanyViewModel companyViewModel;
    CompanyAdapter companyAdapter = new CompanyAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false);
        View view = binding.getRoot();

        binding.homeFragmentRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.homeFragmentRecycler.setAdapter(companyAdapter);

        companyViewModel = ViewModelProviders.of((FragmentActivity) getContext()).get(CompanyViewModel.class);
        companyViewModel.getData();
        companyViewModel.companyMutableLiveData.observe(this, new Observer<List<CompanyModel>>() {
            @Override
            public void onChanged(List<CompanyModel> companyModels) {
                companyAdapter.setArrayList(getContext(),companyModels);
            }
        });

        return view;
    }

}
