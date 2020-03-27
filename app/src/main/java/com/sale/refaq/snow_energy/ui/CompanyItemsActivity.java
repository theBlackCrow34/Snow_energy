package com.sale.refaq.snow_energy.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sale.refaq.snow_energy.R;
import com.sale.refaq.snow_energy.databinding.ActivityCompanyItemsBinding;
import com.sale.refaq.snow_energy.pojo.CompanyItemsModel;
import com.sale.refaq.snow_energy.ui.adapter.CompanyItemsAdapter;
import com.sale.refaq.snow_energy.ui.view_model.CompanyItemsViewModel;
import com.sale.refaq.snow_energy.ui.view_model.CompanyViewModel;

import java.util.ArrayList;
import java.util.List;

public class CompanyItemsActivity extends AppCompatActivity {

    CompanyItemsViewModel companyViewModel;
    ActivityCompanyItemsBinding binding;
    CompanyItemsAdapter companyItemsAdapter = new CompanyItemsAdapter();
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_company_items);
        binding.setLifecycleOwner(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        setSupportActionBar(binding.companyItemsToolBar);

        setCompanyName(id);

        binding.activityCompanyItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.activityCompanyItemsRecyclerView.setAdapter(companyItemsAdapter);

        companyViewModel = ViewModelProviders.of(this).get(CompanyItemsViewModel.class);
        companyViewModel.getData(id);
        companyViewModel.companyItemsMutableLiveData.observe(this, new Observer<List<CompanyItemsModel>>() {
            @Override
            public void onChanged(List<CompanyItemsModel> companyItemsModels) {

                companyItemsAdapter.setList(getApplicationContext(),companyItemsModels);
            }
        });
    }

    private void setCompanyName(String id) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Companies").child(id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String companyName = dataSnapshot.child("name").getValue(String.class);
                binding.companyName.setText(companyName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
