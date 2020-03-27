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
import com.sale.refaq.snow_energy.pojo.CompanyModel;

import java.util.ArrayList;
import java.util.List;

public class CompanyItemsViewModel extends ViewModel {

    private static final String TAG = "CompanyItemsViewModel";
    List<CompanyItemsModel> companyItemsViewModels = new ArrayList<>();
    public MutableLiveData<List<CompanyItemsModel>> companyItemsMutableLiveData = new MutableLiveData<>();

    private List<CompanyItemsModel> setData()
    {
        return companyItemsViewModels;
    }
    public void getData(String id)
    {
        getCompanyItems(id);
    }
    private void getCompanyItems (String id)
    {

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Companies").child(id).child("items");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                companyItemsViewModels.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Log.d(TAG, "getCompanyItems: testFinal : CompanyItemsViewModel");
                    CompanyItemsModel companyModel =ds.getValue(CompanyItemsModel.class);
                    //Log.d(TAG, "onDataChange: sherif"+companyModel.getName());
                    companyItemsViewModels.add(companyModel);
                    //Log.d(TAG, "onDataChange: sherif"+"size :"+companyModels.size());
                }
                //setData();
                companyItemsMutableLiveData.setValue(setData());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
