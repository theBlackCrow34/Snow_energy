package com.sale.refaq.snow_energy.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.sale.refaq.snow_energy.R;
import com.sale.refaq.snow_energy.databinding.ActivityShoppingCartBinding;
import com.sale.refaq.snow_energy.pojo.ShoppingCardModel;
import com.sale.refaq.snow_energy.ui.adapter.ShoppingCardAdapter;
import com.sale.refaq.snow_energy.ui.view_model.ShoppingCardViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity {

    private static final String TAG = "ShoppingCartActivity";
    boolean here = false;
    ShoppingCardAdapter shoppingCardAdapter = new ShoppingCardAdapter();
    ShoppingCardViewModel shoppingCardViewModel;
    ActivityShoppingCartBinding binding;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_shopping_cart);
        binding.setLifecycleOwner(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();

        setSupportActionBar(binding.shoppingCardToolBar);
        binding.shoppingCardRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.shoppingCardRecyclerView.setAdapter(shoppingCardAdapter);
        binding.shoppingCardGo.setEnabled(false);

        shoppingCardViewModel = ViewModelProviders.of(this).get(ShoppingCardViewModel.class);
        shoppingCardViewModel.getData(userId);
        shoppingCardViewModel.shoppingCardMutableLiveData.observe(this, new Observer<List<ShoppingCardModel>>() {
            @Override
            public void onChanged(List<ShoppingCardModel> shoppingCardModels) {
                shoppingCardAdapter.setList(getApplicationContext(),shoppingCardModels);
                if (shoppingCardViewModel.getTotal() == 0){
                    binding.shoppingCardGo.setEnabled(false);
                }
                else{
                binding.shoppingCardTotal.setText(" المجموع : "+shoppingCardViewModel.getTotal());
                    binding.shoppingCardGo.setEnabled(true);
                }
            }
        });
        binding.shoppingCardGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveOrder();
                Intent intent = new Intent(getApplicationContext(),DeliveryAddressDetailsActivity.class);
                intent.putExtra("totalPrice",shoppingCardViewModel.getTotal()+"");
                userId="";
                startActivity(intent);
            }
        });

    }
    private void checkOrdersComplete() {
        DatabaseReference databaseReferenceComp = FirebaseDatabase.getInstance().getReference("Orders");
        Query query = databaseReferenceComp.orderByChild("userId").equalTo(userId);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.d(TAG, "getCompanyItems: testFinal : ShoppingCartActivity : checkOrdersComplete : for ");
                    String child = ds.getKey();
                    checkNonCompleteOrders(child);
                }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void checkNonCompleteOrders(String child) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Orders").child(child);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                String check = dataSnapshot.child("orderComplete").getValue(String.class);
                if (check!= null)
                if (check.equals("no"))
                {
                    if (here){
                    Toast.makeText(ShoppingCartActivity.this, "your last UnComplete Order Was Deleted ..", Toast.LENGTH_SHORT).show();
                    databaseReference.removeValue();
                    }
                }
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.delete_view :
                //confirm delete dialog
                Toast.makeText(this, "deleted", Toast.LENGTH_SHORT).show();
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        here = true;
        checkOrdersComplete();
        super.onStart();
    }

    @Override
    protected void onStop() {
        here = false;
        //Toast.makeText(this, "gagagaga", Toast.LENGTH_SHORT).show();
        super.onStop();
    }
}
