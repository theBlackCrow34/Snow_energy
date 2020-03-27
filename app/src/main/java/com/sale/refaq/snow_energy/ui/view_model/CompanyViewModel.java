package com.sale.refaq.snow_energy.ui.view_model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sale.refaq.snow_energy.pojo.CompanyModel;

import java.util.ArrayList;
import java.util.List;

public class CompanyViewModel extends ViewModel {

    List<CompanyModel> companyModels = new ArrayList<>();
    private static final String TAG = "CompanyViewModel";
    public MutableLiveData<List<CompanyModel>> companyMutableLiveData = new MutableLiveData<>();

    private List<CompanyModel> setData()
    {

        return companyModels;
    }
    public void getData()
    {

       getCompaniesList();
    }
    public void getCompaniesList()
    {

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Companies");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                companyModels.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Log.d(TAG, "getCompanyItems: testFinal : CompanyViewModel");
                    CompanyModel companyModel =ds.getValue(CompanyModel.class);
                    //Log.d(TAG, "onDataChange: sherif"+companyModel.getName());
                    companyModels.add(companyModel);
                    //Log.d(TAG, "onDataChange: sherif"+"size :"+companyModels.size());
                }
                //setData();
                companyMutableLiveData.setValue(setData());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Log.d(TAG, "onDataChange: sherif"+"size :"+companyModels.size());

    }
}
