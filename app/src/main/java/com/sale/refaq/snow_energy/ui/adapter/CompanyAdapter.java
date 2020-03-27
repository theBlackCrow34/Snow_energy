package com.sale.refaq.snow_energy.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sale.refaq.snow_energy.R;
import com.sale.refaq.snow_energy.pojo.CompanyModel;
import com.sale.refaq.snow_energy.ui.CompanyItemsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {

    private List<CompanyModel> moviesArrayList = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CompanyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.company_name, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, final int i) {
        String name = moviesArrayList.get(i).getName();
        final String id = moviesArrayList.get(i).getId();
        String logo = moviesArrayList.get(i).getLogo();

        try {
            Picasso.get().load(logo).placeholder(R.drawable.home_illustration).into(holder.logo);
        }catch (Exception e){
            Picasso.get().load(R.drawable.home_illustration).into(holder.logo);
        }

        holder.name.setText(name);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CompanyItemsActivity.class);
                intent.putExtra("id",id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesArrayList.size();
    }

    public void setArrayList(Context context, List<CompanyModel> moviesArrayList) {
        this.moviesArrayList = moviesArrayList;
        this.context = context;
        notifyDataSetChanged();
    }

    public class CompanyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView logo;
        ConstraintLayout constraintLayout;
        public CompanyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            logo = itemView.findViewById(R.id.logo);
            constraintLayout = itemView.findViewById(R.id.company_item_constrain_layout);
        }
    }
}
