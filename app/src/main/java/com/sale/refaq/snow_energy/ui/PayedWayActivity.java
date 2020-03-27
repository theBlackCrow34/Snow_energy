package com.sale.refaq.snow_energy.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sale.refaq.snow_energy.R;
import com.sale.refaq.snow_energy.databinding.ActivityPayedWayBinding;
import com.sale.refaq.snow_energy.pojo.OrderItemItemModel;

import java.util.HashMap;

public class PayedWayActivity extends AppCompatActivity {

    private static final String TAG = "PayedWayActivity";
    ActivityPayedWayBinding binding ;
    String orderId,userId,totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_payed_way);
        binding.setLifecycleOwner(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();

        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        totalPrice = intent.getStringExtra("totalPrice");
        binding.total.setText(totalPrice);



        binding.confirmPayWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(),MainViewActivity.class));
                finishOrder();
            }
        });
    }

    private void finishOrder() {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Orders").child(orderId);
        HashMap<String,Object> hashMap = new HashMap<>();

        Log.d(TAG, "getCompanyItems: testFinal : PayedWayActivity : finishOrder");
        hashMap.put("orderComplete","yes");

        if (binding.payOnline.isChecked())
        {
            Toast.makeText(this, "sorry ,we work to open it soon !", Toast.LENGTH_SHORT).show();
        }else
            {
                hashMap.put("payedWay","when delivered");

        setOrderedItem();

        databaseReference.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Intent intent = new Intent(getApplicationContext(),MainViewActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                DeliveryAllDetailsActivity.instant.finish();
                DeliveryAddressDetailsActivity.instant.finish();
                startActivity(intent);
                finish();
            }
        });
            }
    }
    private void setOrderedItem() {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ShoppingCard");
        Query query = databaseReference.orderByChild("userId").equalTo(userId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Log.d(TAG, "getCompanyItems: testFinal : PayedWayActivity : setOrderItem : for ");
                    OrderItemItemModel orderItemItemModel =ds.getValue(OrderItemItemModel.class);
                    if (orderItemItemModel.getOrdered().equals("no"))
                    {
                        Log.d(TAG, "getCompanyItems: testFinal : PayedWayActivity : setOrderItem : if ");
                        String child = ds.getKey();
                        databaseReference.child(child).child("ordered").setValue("yes"+orderId);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStop() {
        userId = "";
        orderId="";
        //System.exit(0);
        super.onStop();
    }
}
