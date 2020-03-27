package com.sale.refaq.snow_energy.ui.view_model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sale.refaq.snow_energy.pojo.CompanyItemsModel;
import com.sale.refaq.snow_energy.pojo.ShoppingCardModel;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCardViewModel extends ViewModel {
    private static final String TAG = "ShoppingCardViewModel";
    int total;
    List<ShoppingCardModel> shoppingCardModels = new ArrayList<>();
    public MutableLiveData<List<ShoppingCardModel>> shoppingCardMutableLiveData = new MutableLiveData<>();

    public int getTotal(){
        return total;
    }
    private List<ShoppingCardModel> setData()
    {
        return shoppingCardModels;
    }
    public void getData(String userId)
    {
        getCardData(userId);
    }
    public void getCardData(final String userId)
    {


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ShoppingCard");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                shoppingCardModels.clear();

                total = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Log.d(TAG, "getCompanyItems: testFinal : ShoppingCardViewModel");
                    ShoppingCardModel shoppingCardModel =ds.getValue(ShoppingCardModel.class);
                    //Log.d(TAG, "onDataChange: sherif"+companyModel.getName());
                    if (shoppingCardModel.getUserId().equals(userId) && shoppingCardModel.getOrdered().equals("no"))
                    {
                    shoppingCardModels.add(shoppingCardModel);
                    total = total + (Integer.parseInt(shoppingCardModel.getQuantity()) * Integer.parseInt(shoppingCardModel.getPrice()));
                    }
                    //Log.d(TAG, "onDataChange: sherif"+"size :"+companyModels.size());
                }
                //setData();
                shoppingCardMutableLiveData.setValue(setData());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
