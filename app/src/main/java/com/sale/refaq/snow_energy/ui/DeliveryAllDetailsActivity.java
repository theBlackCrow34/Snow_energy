package com.sale.refaq.snow_energy.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sale.refaq.snow_energy.R;
import com.sale.refaq.snow_energy.databinding.ActivityDeliveryAllDetailsBinding;
import com.sale.refaq.snow_energy.pojo.OrderItemItemModel;
import com.sale.refaq.snow_energy.ui.adapter.OrderItemItemAdapter;
import com.sale.refaq.snow_energy.ui.view_model.OrderItemItemViewModel;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class DeliveryAllDetailsActivity extends AppCompatActivity {

    public  static DeliveryAllDetailsActivity instant = null;
    ActivityDeliveryAllDetailsBinding binding ;
    OrderItemItemAdapter orderItemItemAdapter = new OrderItemItemAdapter();
    OrderItemItemViewModel orderItemItemViewModel;
    //date picker
    MaterialDatePicker.Builder builder;
    MaterialDatePicker materialDatePicker;
    String orderId;
    String totalPrice,name,phone,city,area, street,block,floar,deliveryDate,deliveryTime;
    int iTotalPrice,delivery;
    String userId;
    private static final String TAG = "DeliveryAllDetailsActiv";
    //time picker
    private TimePickerDialog.OnTimeSetListener timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_delivery_all_details);
        binding.setLifecycleOwner(this);

        instant = this;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        //setData for all attributes

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(orderItemItemAdapter);

        //
        orderItemItemViewModel = ViewModelProviders.of(this).get(OrderItemItemViewModel.class);
        orderItemItemViewModel.getData(userId);
        orderItemItemViewModel.orderItemMutableLiveData.observe(this, new Observer<List<OrderItemItemModel>>() {
            @Override
            public void onChanged(List<OrderItemItemModel> orderItemItemModels) {
                orderItemItemAdapter.setList(orderItemItemModels,getApplicationContext());
            }
        });


        //current day
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();
        long today = MaterialDatePicker.todayInUtcMilliseconds();
        long month = MaterialDatePicker.thisMonthInUtcMilliseconds();

        //calendar constrain disable all befor
        CalendarConstraints.Builder calendarConstrain = new CalendarConstraints.Builder();
        /////////////////////set max days from convert days to numbers
        //very important to disable every day befor
        calendarConstrain.setValidator(DateValidatorPointForward.now());

        builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("select");
        builder.setSelection(today);
        builder.setCalendarConstraints(calendarConstrain.build());

        materialDatePicker = builder.build();

        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER");
            }
        });
        timePickerDialog = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String from ="";
                if (i>12){
                    i = i - 12;
                    from = " مساءا "+i + ":"+ i1 ;
                }else if(i==0){
                    i=12;
                    from = "مساءا "+i + ":"+i1;
                }
                else {
                    from =" صباحا "+ i + ":" + i1 ;
                }
                deliveryTime = from;
                binding.time.setText(from);
            }
        };
        binding.time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR);
                int minutes = calendar.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(DeliveryAllDetailsActivity.this,timePickerDialog,hours,minutes,true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                deliveryDate = materialDatePicker.getHeaderText();
                binding.date.setText(materialDatePicker.getHeaderText());
            }
        });

        binding.choosePayWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOrder();

            }
        });
        binding.changeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getData();
    }

    private void updateOrder() {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Orders").child(orderId);
        Log.d(TAG, "getCompanyItems: testFinal : DeliveryAllDetailActivity : updateOrder");
        HashMap<String,Object> hashMap = new HashMap<>();
        if (TextUtils.isEmpty(deliveryDate))
        {
            Toast.makeText(this, "من فضلك أختر تاريخ التوصيل المراد ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(deliveryTime))
        {
            Toast.makeText(this, "من فضلك أختر وقت التوصيل المراد ", Toast.LENGTH_SHORT).show();
            return;
        }
        hashMap.put("totalPrice",totalPrice);
        hashMap.put("deliveryTax",delivery);
        hashMap.put("deliveryDate",deliveryDate);
        hashMap.put("deliveryTime",deliveryTime);
        hashMap.put("payedWay","");

        databaseReference.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Intent intent = new Intent(getApplicationContext(),PayedWayActivity.class);
                intent.putExtra("orderId",orderId);
                intent.putExtra("totalPrice",totalPrice);
                startActivity(intent);
            }
        });
    }

    private void setData() {
        binding.name.setText(name);
        binding.phone.setText(phone);
        binding.city.setText(city);
        binding.area.setText(area);
        binding.districtStreet.setText(street);
        binding.block.setText(block);
        binding.floor.setText(floar);
        binding.price.setText(totalPrice);
        binding.delivery.setText(delivery+"");
        binding.total.setText(""+(delivery+iTotalPrice));
    }

    private void getData() {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Orders").child(orderId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Log.d(TAG, "getCompanyItems: testFinal : DeliveryAllDetailActivity : getData : if");
                totalPrice = dataSnapshot.child("totalPrice").getValue(String.class);
                if (dataSnapshot.hasChild("totalPrice"))
                iTotalPrice = Integer.parseInt(dataSnapshot.child("totalPrice").getValue(String.class));
                name = dataSnapshot.child("name").getValue(String.class);
                phone = dataSnapshot.child("phone").getValue(String.class);
                city = dataSnapshot.child("city").getValue(String.class);
                area = dataSnapshot.child("area").getValue(String.class);
                street = dataSnapshot.child("street").getValue(String.class);
                floar = dataSnapshot.child("floor").getValue(String.class);
                block = dataSnapshot.child("block").getValue(String.class);
                }

                delivery = 25;

                setData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStop() {
        userId="";
        orderId="";
        //System.exit(0);
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        //System.exit(0);
        super.onBackPressed();
    }
}
