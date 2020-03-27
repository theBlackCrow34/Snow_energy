package com.sale.refaq.snow_energy.ui.view_model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sale.refaq.snow_energy.pojo.Empty;
import com.sale.refaq.snow_energy.pojo.OrderItemItemModel;

import java.util.ArrayList;
import java.util.List;

public class OrderItemOrderedViewModel extends ViewModel {

    private static final String TAG = "OrderItemOrderedViewMod";
    List<OrderItemItemModel> orderItemItemlist = new ArrayList<>();
    public MutableLiveData<List<OrderItemItemModel>> orderItemMutableLiveData = new MutableLiveData<>();

    private List<OrderItemItemModel> setData()
    {
        return orderItemItemlist;
    }
    public void getData(String userId,String orderId)
    {
        getCardData(userId,orderId);
    }
    public void getCardData(final String userId, final String orderId)
    {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ShoppingCard");
        Query query = databaseReference.orderByChild("userId").equalTo(userId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderItemItemlist.clear();

                for (final DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Log.d(TAG, "getCompanyItems: testFinal : OrderItemOrderedViewModel");
                    OrderItemItemModel orderItemItemModel =ds.getValue(OrderItemItemModel.class);

                        if (orderItemItemModel.getOrdered().equals("yes"+orderId)){
                        //OrderItemItemModel orderItemItemModelCheck = ds.getValue(OrderItemItemModel.class);
                            orderItemItemlist.add(orderItemItemModel);
                        }

                    //orderItemItemlist.add(orderItemItemModel);
                    //Log.d(TAG, "onDataChange: sherif"+"size :"+companyModels.size());
                }
                //setData();
                orderItemMutableLiveData.setValue(setData());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
