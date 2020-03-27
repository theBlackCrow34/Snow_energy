package com.sale.refaq.snow_energy.ui.fragment;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sale.refaq.snow_energy.R;
import com.sale.refaq.snow_energy.databinding.FragmentAllOrdersBinding;
import com.sale.refaq.snow_energy.pojo.OrderModel;
import com.sale.refaq.snow_energy.ui.adapter.OrderAdapter;
import com.sale.refaq.snow_energy.ui.view_model.OrderViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllOrdersFragment extends Fragment {

    private static final String TAG = "AllOrdersFragment";
    FragmentAllOrdersBinding binding;
    OrderViewModel orderViewModel;
    OrderAdapter orderAdapter = new OrderAdapter();

    String userId;


    public AllOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_all_orders, container, false);
        View view = binding.getRoot();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();

        binding.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recycler.setAdapter(orderAdapter);

        orderViewModel = ViewModelProviders.of((FragmentActivity) getContext()).get(OrderViewModel.class);
        orderViewModel.getData(userId);
        orderViewModel.orderMutableLiveData.observe(this, new Observer<List<OrderModel>>() {
            @Override
            public void onChanged(List<OrderModel> orderModels) {
                Log.d(TAG, "getCompanyItems: testFinal : AllOrdersFragment : viewModel  ");
                orderAdapter.setList(getContext(),orderModels);
            }
        });


        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
