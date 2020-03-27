package com.sale.refaq.snow_energy.ui.view_model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sale.refaq.snow_energy.pojo.OrderItemItemModel;
import com.sale.refaq.snow_energy.pojo.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class OrderViewModel extends ViewModel {

    private static final String TAG = "OrderViewModel";
    List<OrderModel> orderList = new ArrayList<>();
    public MutableLiveData<List<OrderModel>> orderMutableLiveData = new MutableLiveData<>();

    private List<OrderModel> setData()
    {
        return orderList;
    }
    public void getData(String userId){getOrderData(userId);}
    public void getOrderData(String userId)
    {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
        databaseReference.onDisconnect();
        Query query = databaseReference.orderByChild("userId").equalTo(userId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderList.clear();


                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Log.d(TAG, "getCompanyItems: testFinal : OrderViewModel");
                    OrderModel orderModel = ds.getValue(OrderModel.class);
                    if (orderModel.getOrderComplete().equals("yes"))
                    {
                        orderList.add(orderModel);

                    }

                    //orderItemItemlist.add(orderItemItemModel);
                    //Log.d(TAG, "onDataChange: sherif"+"size :"+companyModels.size());
                }
                //setData();
                orderMutableLiveData.setValue(setData());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
