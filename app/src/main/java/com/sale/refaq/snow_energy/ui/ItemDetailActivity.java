package com.sale.refaq.snow_energy.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sale.refaq.snow_energy.R;
import com.sale.refaq.snow_energy.databinding.ActivityItemDetailBinding;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.HashMap;

public class ItemDetailActivity extends AppCompatActivity {

    String itemId,companyId;
    String userUid,itemName,price,companyName,logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityItemDetailBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_item_detail);
        binding.setLifecycleOwner(this);

        setSupportActionBar(binding.itemDetailToolBar);
        binding.itemDetailToolBar.setTitle("");

        final Intent intent = getIntent();
        itemId = intent.getStringExtra("id");
        companyId = intent.getStringExtra("companyId");

        binding.companyItemDetailAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemToShoppingCard();
            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Companies").child(companyId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                companyName = dataSnapshot.child("name").getValue(String.class);
                binding.itemDetailNameCompany.setText(" اسم الشركة : "+companyName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("items").child(itemId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemName = dataSnapshot.child("name").getValue(String.class);
                String size =dataSnapshot.child("size").getValue(String.class);
                price = dataSnapshot.child("price").getValue(String.class);
                String min = dataSnapshot.child("minOrder").getValue(String.class);
                logo = dataSnapshot.child("logo").getValue(String.class);
                String details = dataSnapshot.child("details").getValue(String.class);


                binding.itemDetailName.setText(itemName);
                binding.itemDetailSize.setText("الحجم : "+size +" جالون ");
                binding.itemDetailPrice.setText("السعر : "+price);
                binding.itemDetailMin.setText("حد أدنى : "+min);
                binding.descriptionDetailPrice.setText(details);

                binding.companyItemDetailAdd.setEnabled(true);

                try {
                    Picasso.get().load(logo).placeholder(R.drawable.home_illustration).into(binding.itemDetailImage);
                }catch (Exception e){
                    Picasso.get().load(R.drawable.home_illustration).into(binding.itemDetailImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addItemToShoppingCard() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ShoppingCard");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userUid = user.getUid();
        String key =databaseReference.push().getKey();
        HashMap<String,Object> results = new HashMap<>();

        results.put("companyId",companyId);
        results.put("itemId",itemId);
        results.put("companyName",companyName);
        results.put("key",key);
        results.put("userId",userUid);
        results.put("logo",logo);
        results.put("itemName",itemName);
        results.put("price",price);
        results.put("quantity","1");
        results.put("ordered","no");
        databaseReference.child(key).updateChildren(results).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Intent intent = new Intent(getApplicationContext(),ShoppingCartActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.main_view_shopping :
                startActivity(new Intent(getApplicationContext(),ShoppingCartActivity.class));
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }
}
