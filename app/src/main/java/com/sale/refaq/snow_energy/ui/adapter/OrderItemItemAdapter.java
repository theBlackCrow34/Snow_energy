package com.sale.refaq.snow_energy.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sale.refaq.snow_energy.R;
import com.sale.refaq.snow_energy.pojo.OrderItemItemModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OrderItemItemAdapter extends RecyclerView.Adapter<OrderItemItemAdapter.OrderItemItemViewHolder> {

    private List<OrderItemItemModel> items = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public OrderItemItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderItemItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemItemViewHolder holder, int i) {

        String itemName = items.get(i).getItemName();
        String companyName = items.get(i).getCompanyName();
        String userId = items.get(i).getUserId();
        String itemId = items.get(i).getItemId();
        String logo = items.get(i).getLogo();
        String price = items.get(i).getPrice();
        final String quantity = items.get(i).getQuantity();
        final String key = items.get(i).getKey();

        holder.quantity.setText(" الكمية : "+quantity);
        holder.price.setText("السعر : "+price);
        holder.itemName.setText(itemName);
        holder.companyName.setText(" اسم الشركة : "+companyName);

        try {
            Picasso.get().load(logo).placeholder(R.drawable.home_illustration).into(holder.logo);
        }catch (Exception e){
            Picasso.get().load(R.drawable.home_illustration).into(holder.logo);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setList(List<OrderItemItemModel> item ,Context context) {
        this.items = item;
        this.context = context;
        notifyDataSetChanged();
    }

    public class OrderItemItemViewHolder extends RecyclerView.ViewHolder {

        TextView companyName,itemName,price,quantity;
        ImageView logo;

        public OrderItemItemViewHolder(@NonNull View itemView) {
            super(itemView);

            companyName = itemView.findViewById(R.id.company_name);
            itemName = itemView.findViewById(R.id.item_name);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);
            logo = itemView.findViewById(R.id.logo);
        }
    }
}
