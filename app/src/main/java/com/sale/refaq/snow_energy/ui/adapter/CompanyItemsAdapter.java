package com.sale.refaq.snow_energy.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sale.refaq.snow_energy.R;
import com.sale.refaq.snow_energy.pojo.CompanyItemsModel;
import com.sale.refaq.snow_energy.ui.ItemDetailActivity;
import com.sale.refaq.snow_energy.ui.ShoppingCartActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CompanyItemsAdapter extends RecyclerView.Adapter<CompanyItemsAdapter.CompanyItemsViewHolder> {

    private List<CompanyItemsModel> moviesList = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public CompanyItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CompanyItemsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.company_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyItemsViewHolder holder, int i) {
        final String id = moviesList.get(i).getItemId();
        final String companyId = moviesList.get(i).getCompanyId();
        String name = moviesList.get(i).getName();
        String size = moviesList.get(i).getSize();
        String price = moviesList.get(i).getPrice();
        String min = moviesList.get(i).getMinOrder();
        String exit = moviesList.get(i).getExit();
        String logo = moviesList.get(i).getLogo();

        try {
            Picasso.get().load(logo).placeholder(R.drawable.home_illustration).into(holder.image);
        }catch (Exception e){
            Picasso.get().load(R.drawable.home_illustration).into(holder.image);
        }

        holder.min.setText(" الحد الادنى : "+min);
        holder.size.setText("الحجم : "+size+" جالون ");
        holder.price.setText(" السعر :"+price);
        holder.name.setText(name);

        holder.itemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemDetailActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("companyId",companyId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public void setList(Context context , List<CompanyItemsModel> moviesList) {
        this.moviesList = moviesList;
        this.context = context;
        notifyDataSetChanged();
    }

    public class CompanyItemsViewHolder extends RecyclerView.ViewHolder {

        Button itemBtn;
        TextView name,size,min,price;
        ImageView image;
        public CompanyItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.company_item_detail_name);
            size = itemView.findViewById(R.id.company_item_detail_size);
            min = itemView.findViewById(R.id.company_item_detail_min);
            price = itemView.findViewById(R.id.company_item_detail_price);
            itemBtn = itemView.findViewById(R.id.company_item_detail_add);
            image = itemView.findViewById(R.id.company_item_detail_image);
        }
    }
}
