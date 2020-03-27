package com.sale.refaq.snow_energy.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.sale.refaq.snow_energy.R;
import com.sale.refaq.snow_energy.databinding.ActivityDeliveryDataBinding;
import com.sale.refaq.snow_energy.pojo.OrderItemItemModel;
import com.sale.refaq.snow_energy.pojo.ShoppingCardModel;

import java.util.HashMap;

public class DeliveryAddressDetailsActivity extends AppCompatActivity {

    public  static DeliveryAddressDetailsActivity instant = null;
    ActivityDeliveryDataBinding binding;
    String userId,totalPrice;
    String name,phone,city,area, street,block,floar,location,key;
    boolean here = false;

    private static final String TAG = "DeliveryAddressDetailsA";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_delivery_data);
        binding.setLifecycleOwner(this);

        instant = this;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();

        Intent intent = getIntent();
        totalPrice = intent.getStringExtra("totalPrice");


        setSupportActionBar(binding.deliveryToolBar);
        binding.go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInputs();
            }
        });
        binding.currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MapActivity.class));
            }
        });
    }

    /*private void setCompleteOrder() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
        databaseReference.child(orderId).child("orderComplete").setValue("no");
    }*/
    private void saveOrder() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
        key = databaseReference.push().getKey();

        HashMap<String,Object> results = new HashMap<>();

        results.put("orderId",key);
        results.put("orderComplete","no");
        results.put("userId",userId);
        results.put("orderDate", ServerValue.TIMESTAMP);
        results.put("orderNumber",ServerValue.TIMESTAMP);
        results.put("deliveryDate",ServerValue.TIMESTAMP);
        results.put("progress","0");
        results.put("name",name);
        results.put("phone",phone);
        results.put("city",city);
        results.put("area",area);
        results.put("street",street);
        results.put("location",location);
        results.put("block",block);
        results.put("floor",floar);
        results.put("status","ordered");
        results.put("totalPrice",totalPrice);
        Log.d(TAG, "getCompanyItems: testFinal : DeliveryAddressDetailActivity : saveOrder");
        databaseReference.child(key).updateChildren(results).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                //checkOrder();
                Intent intent = new Intent(getApplicationContext(),DeliveryAllDetailsActivity.class);
                intent.putExtra("orderId",key);
                key="";
                userId="";
                startActivity(intent);
            }
        });
    }

    private void checkInputs() {
        city = binding.city.getText().toString();
        name = binding.name.getText().toString();
        phone = binding.phoneNumberInput.getText().toString();
        street = binding.districtStreet.getText().toString();
        area = binding.area.getText().toString();
        block = binding.block.getText().toString();
        floar = binding.floor.getText().toString();
        location = binding.currentLocation.getText().toString()+" lo ";
        if (TextUtils.isEmpty(name))
        {
            binding.name.setError("invalid name !");
            binding.name.setFocusable(true);
            return;
        }
        if (TextUtils.isEmpty(phone))
        {
            binding.phoneNumberInput.setError("invalid phone !");
            binding.phoneNumberInput.setFocusable(true);
            return;
        }
        if (TextUtils.isEmpty(city))
        {
            binding.city.setError("invalid city !");
            binding.city.setFocusable(true);
            return;
        }
        if (TextUtils.isEmpty(area))
        {
            binding.area.setError("invalid area !");
            binding.area.setFocusable(true);
            return;
        }
        if (TextUtils.isEmpty(street))
        {
            binding.districtStreet.setError("invalid street !");
            binding.districtStreet.setFocusable(true);
            return;
        }

        /*if (TextUtils.isEmpty(location))
        {
            binding.currentLocation.setError("invalid city !");
            binding.currentLocation.setFocusable(true);
            return;
        }*/
        saveOrder();
    }

    private void getAllItems() {
        if (here)
        {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ShoppingCard");
        //if (checkOrder()){}
        final DatabaseReference databaseReferenceOrders = FirebaseDatabase.getInstance().getReference("Orders").child(key).child("items");
        Query query = databaseReference.orderByChild("userId").equalTo(userId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Log.d(TAG, "getCompanyItems: testFinal : DeliveryAddressDetailActivity : getAllItems : for");
                    OrderItemItemModel orderItemItemModel = ds.getValue(OrderItemItemModel.class);
                    if (orderItemItemModel.getOrdered().equals("no"))
                    {
                        Log.d(TAG, "getCompanyItems: testFinal : DeliveryAddressDetailActivity : getAllItems : if");
                        String child = ds.getKey();
                        databaseReferenceOrders.child(child).child("itemId").setValue(child);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        }
    }

    private void checkOrder() {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Orders").child(key);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String check = dataSnapshot.child("orderComplete").getValue(String.class);
                Log.d(TAG, "getCompanyItems: testFinal : DeliveryAddressDetailActivity : checkOrder");
                if (check.equals("yes"))
                {
                    Log.d(TAG, "getCompanyItems: testFinal : DeliveryAddressDetailActivity : checkOrder : if : yes");
                }else
                    {
                        Log.d(TAG, "getCompanyItems: testFinal : DeliveryAddressDetailActivity : checkOrder : if : else");
                        getAllItems();
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        //setCompleteOrder();
        here = true;
        super.onStart();
    }

    @Override
    protected void onStop() {
        here = false;
        //System.exit(0);
        key="";
        userId="";
        super.onStop();
    }

    @Override
    public void onBackPressed() {
       // System.exit(0);
        finish();
        super.onBackPressed();
    }
}
