package com.sale.refaq.snow_energy.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sale.refaq.snow_energy.R;
import com.sale.refaq.snow_energy.pojo.ShoppingCardModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCardAdapter extends RecyclerView.Adapter<ShoppingCardAdapter.ShoppingCardViewHolder> {

    private List<ShoppingCardModel> moviesList = new ArrayList<>();
    Context context;
    int total = 0;
    //boolean delete = false;

    @NonNull
    @Override
    public ShoppingCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShoppingCardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopping_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ShoppingCardViewHolder holder, final int i) {

        String itemName = moviesList.get(i).getItemName();
        String companyName = moviesList.get(i).getCompanyName();
        String userId = moviesList.get(i).getUserId();
        String itemId = moviesList.get(i).getItemId();
        String logo = moviesList.get(i).getLogo();
        String price = moviesList.get(i).getPrice();
        int iPrice = Integer.parseInt(moviesList.get(i).getPrice());
        final String quantity = moviesList.get(i).getQuantity();
        final int iQuantity = Integer.parseInt(moviesList.get(i).getQuantity());
        final String key = moviesList.get(i).getKey();

        holder.quantity.setText(quantity);
        holder.price.setText("السعر : "+price);
        holder.itemName.setText(itemName);
        holder.companyName.setText(" اسم الشركة : "+companyName);
        holder.delete.setVisibility(View.GONE);


        final boolean[] del = {false};
        total = total +(iQuantity*iPrice);

        try {
            Picasso.get().load(logo).placeholder(R.drawable.home_illustration).into(holder.logo);
        }catch (Exception e){
            Picasso.get().load(R.drawable.home_illustration).into(holder.logo);
        }
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ShoppingCard").child(key);
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iQuantity ==1 )
                {
                   // showAlertDialog(i);
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                    builder.setTitle("Delete");
                    builder.setMessage("Are you sure to delete this item ?");
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            databaseReference.removeValue();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.create().show();


                }else if (iQuantity == 0)
                {}else
                databaseReference.child("quantity").setValue(""+(iQuantity-1));

            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("quantity").setValue(""+(iQuantity+1));

            }
        });
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog here
                if (del[0])
                {
                    holder.delete.setVisibility(View.GONE);
                    del[0] = false;
                }else {
                    holder.delete.setVisibility(View.VISIBLE);
                    holder.delete.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_up_four_late));
                    del[0] = true;
                }

            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                builder.setTitle("Delete");
                builder.setMessage("Are you sure to delete this item ?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseReference.removeValue();
                        holder.delete.setVisibility(View.GONE);
                        del[0] = true;
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        holder.delete.setVisibility(View.GONE);
                        del[0] = true;
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public void setList(Context context ,List<ShoppingCardModel> moviesList) {
        this.moviesList = moviesList;
        this.context = context;
        notifyDataSetChanged();
    }

    public class ShoppingCardViewHolder extends RecyclerView.ViewHolder {

        TextView companyName,itemName,price,quantity;
        ImageView logo;
        ImageButton cancel,plus,minus,delete;

        public ShoppingCardViewHolder(@NonNull View itemView) {
            super(itemView);

            companyName = itemView.findViewById(R.id.shopping_card_company_name);
            itemName = itemView.findViewById(R.id.shopping_card_name);
            price = itemView.findViewById(R.id.shopping_card_price);
            quantity = itemView.findViewById(R.id.shopping_card_quantity);
            logo = itemView.findViewById(R.id.shopping_card_image);
            cancel = itemView.findViewById(R.id.shopping_card_cancel);
            plus = itemView.findViewById(R.id.shopping_card_add_number);
            minus = itemView.findViewById(R.id.shopping_card_remove_number);
            delete = itemView.findViewById(R.id.deleteAll);
        }
    }
}
