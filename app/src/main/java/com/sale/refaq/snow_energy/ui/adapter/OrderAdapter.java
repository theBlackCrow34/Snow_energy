package com.sale.refaq.snow_energy.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sale.refaq.snow_energy.R;
import com.sale.refaq.snow_energy.pojo.OrderItemItemModel;
import com.sale.refaq.snow_energy.pojo.OrderModel;
import com.sale.refaq.snow_energy.ui.view_model.OrderItemItemViewModel;
import com.sale.refaq.snow_energy.ui.view_model.OrderItemOrderedViewModel;
import com.shuhart.stepview.StepView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<OrderModel> orders = new ArrayList<>();
    Context context;

    private static final String TAG = "OrderAdapter";
    ////////recycler

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderViewHolder holder, int i) {

        //show details maybe expanded

        long orderNumber = orders.get(i).getOrderNumber();
        String orderId = orders.get(i).getOrderId();
        long orderDate = orders.get(i).getOrderDate();
        String userId = orders.get(i).getUserId();
        String progress = orders.get(i).getProgress();
        int iProgress = Integer.parseInt(orders.get(i).getProgress());
        String deliveryDate = orders.get(i).getDeliveryDate();
        String name = orders.get(i).getName();
        String phone = orders.get(i).getPhone();
        String city = orders.get(i).getCity();
        String street = orders.get(i).getStreet();
        String block = orders.get(i).getBlock();
        String floor = orders.get(i).getFloor();
        String area = orders.get(i).getArea();


        //recycler

        //OrderItemOrderedViewModel orderItemItemViewModel;
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));


        setRecyclerList(userId,orderId,holder.recyclerView);
        /*orderItemItemViewModel = ViewModelProviders.of((FragmentActivity) context).get(OrderItemOrderedViewModel.class);
        orderItemItemViewModel.getData(userId,orderId);
        orderItemItemViewModel.orderItemMutableLiveData.observe((FragmentActivity) context, new Observer<List<OrderItemItemModel>>() {
            @Override
            public void onChanged(List<OrderItemItemModel> orderItemItemModels) {
                orderItemItemAdapter.setList(orderItemItemModels,context);
            }
        });*/


        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(orderDate);
        Date CD = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String date = dateFormat.format(CD);

        holder.deliveryDate.setText(deliveryDate);
        holder.orderDate.setText(date);
        holder.orderNumber.setText(orderNumber+"");
        holder.block.setText(block);
        holder.name.setText(name);
        holder.phone.setText(phone);
        holder.area.setText(area);
        holder.street.setText(street);
        holder.city.setText(city);
        holder.floor.setText(floor);
        final boolean[] vis = {true};
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vis[0]){
                holder.attach.setVisibility(View.VISIBLE);
                holder.cardView.setVisibility(View.VISIBLE);
                    holder.expand.setImageResource(R.drawable.ic_expand_less_black_24dp);
                    holder.details.setText("عرض تفاصيل أقل");
                vis[0] = false;
                }else
                    {
                        holder.expand.setImageResource(R.drawable.ic_expand_more_black_24dp);
                        holder.attach.setVisibility(View.GONE);
                        holder.cardView.setVisibility(View.GONE);
                        holder.details.setText("عرض تفاصيل أكثر");
                        vis[0] = true;
                    }
            }
        });

        holder.stepView.getState()
                .animationType(StepView.ANIMATION_ALL)
                // You should specify only stepsNumber or steps array of strings.
                // In case you specify both steps array is chosen.
                .steps(new ArrayList<String>() {{
                    add("تم تجهيز الطلب");
                    add("المندوب ف الطريق");
                    add("لقد وصل المندوب");
                    add("تم تسليم الطلب");
                }})
                // You should specify only steps number or steps array of strings.
                // In case you specify both steps array is chosen.
                .stepsNumber(4)
                .animationDuration(context.getResources().getInteger(android.R.integer.config_shortAnimTime))
                // other state methods are equal to the corresponding xml attributes
                .commit();
        int checked = iProgress+1;
        if (checked==1)
        {
            holder.stepView.go(0,true);
        }else if (checked==2)
        {holder.stepView.go(1,true);}
        else if (checked==3)
        {holder.stepView.go(2,true);}
        else if (checked==4)
        {holder.stepView.go(3,true);}
    }

    private void setRecyclerList(String userId, final String orderId, final RecyclerView recyclerView) {
        final OrderItemItemAdapter orderItemItemAdapter = new OrderItemItemAdapter();
        final List<OrderItemItemModel> orderItemItemlist = new ArrayList<>();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ShoppingCard");
        Query query = databaseReference.orderByChild("userId").equalTo(userId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderItemItemlist.clear();

                for (final DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Log.d(TAG, "getCompanyItems: testFinal : orderAdapter : setRecyclerList");
                    OrderItemItemModel orderItemItemModel =ds.getValue(OrderItemItemModel.class);

                    if (orderItemItemModel.getOrdered().equals("yes"+orderId)){
                        //OrderItemItemModel orderItemItemModelCheck = ds.getValue(OrderItemItemModel.class);
                        orderItemItemlist.add(orderItemItemModel);
                    }

                    //orderItemItemlist.add(orderItemItemModel);
                    //Log.d(TAG, "onDataChange: sherif"+"size :"+companyModels.size());
                }
                //setData();
                orderItemItemAdapter.setList(orderItemItemlist,context);
                recyclerView.setAdapter(orderItemItemAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void setList(Context context , List<OrderModel> moviesList) {
        this.orders = moviesList;
        this.context = context;
        notifyDataSetChanged();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        StepView stepView;
        TextView orderDate,orderNumber,deliveryDate,details;
        TextView name,phone,city,area,block,floor,street;
        RecyclerView recyclerView;
        ImageView expand;
        CardView cardView;
        TextView attach;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            orderDate = itemView.findViewById(R.id.ordered_date);
            orderNumber = itemView.findViewById(R.id.number_order);
            deliveryDate = itemView.findViewById(R.id.delivery_date);
            recyclerView = itemView.findViewById(R.id.recycler);
            details = itemView.findViewById(R.id.details);
            stepView = itemView.findViewById(R.id.step_view);
            expand = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
            city = itemView.findViewById(R.id.city);
            area = itemView.findViewById(R.id.area);
            block = itemView.findViewById(R.id.block);
            floor = itemView.findViewById(R.id.floor);
            street = itemView.findViewById(R.id.street);
            attach = itemView.findViewById(R.id.text1);
            cardView = itemView.findViewById(R.id.constraintLayout3);
        }
    }
}
