package com.sale.refaq.snow_energy.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sale.refaq.snow_energy.BuildConfig;
import com.sale.refaq.snow_energy.R;
import com.sale.refaq.snow_energy.databinding.ActivityMainViewBinding;
import com.sale.refaq.snow_energy.databinding.NavHeaderBinding;
import com.sale.refaq.snow_energy.ui.fragment.AboutAppFragment;
import com.sale.refaq.snow_energy.ui.fragment.AllOrdersFragment;
import com.sale.refaq.snow_energy.ui.fragment.ContactUsFragment;
import com.sale.refaq.snow_energy.ui.fragment.HomeFragment;
import com.sale.refaq.snow_energy.ui.fragment.UsingPolicyFragment;
import com.sale.refaq.snow_energy.ui.inti.LoginActivity;

import java.util.zip.Inflater;

public class MainViewActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityMainViewBinding binding;
    NavHeaderBinding _bind;
    FirebaseUser user;
    String userId;
    HomeFragment homeFragment = new HomeFragment();
    AboutAppFragment aboutAppFragment = new AboutAppFragment();
    AllOrdersFragment allOrdersFragment = new AllOrdersFragment();
    ContactUsFragment contactUsFragment = new ContactUsFragment();
    UsingPolicyFragment usingPolicyFragment = new UsingPolicyFragment();

    private boolean backButtonCount = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main_view);
        binding.setLifecycleOwner(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        //init main fragment
        fragmentHandler(homeFragment);

        setSupportActionBar(binding.mainViewToolBar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle
                (
                        this,
                        binding.mainViewDrawer,
                        binding.mainViewToolBar,
                        R.string.openNavDrawer,
                        R.string.closeNavDrawer
                );

        binding.mainViewDrawer.addDrawerListener(toggle);
        toggle.syncState();
        binding.mainViewNavigationView.setNavigationItemSelectedListener(this);

         _bind = DataBindingUtil.inflate(getLayoutInflater(), R.layout.nav_header, binding
                .mainViewNavigationView, false);
        binding.mainViewNavigationView.addHeaderView(_bind.getRoot());

        _bind.headerEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),EditDataActivity.class));
            }
        });
        getUserName();
    }

    private void getUserName() {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue(String.class);
                _bind.headerName.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.nav_home :
                /*FragmentTransaction fragmentTransactionH = getSupportFragmentManager().beginTransaction();
                fragmentTransactionH.replace(R.id.fragment_container,homeFragment);
                fragmentTransactionH.commit();*/
                fragmentHandler(homeFragment);
                binding.mainViewDrawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_all_orders :
                fragmentHandler(allOrdersFragment);
                binding.mainViewDrawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_contact_us :
                fragmentHandler(contactUsFragment);
                binding.mainViewDrawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_about_app :
                fragmentHandler(aboutAppFragment);
                binding.mainViewDrawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_policy :
                fragmentHandler(usingPolicyFragment);
                binding.mainViewDrawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_share_app :
                //share app dialog
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Snow Energy");
                    String shareMessage = "\n let me recommend you this application\n\n";
                    shareMessage = shareMessage+"https://play.google.com/store/apps/details?id="+ BuildConfig.APPLICATION_ID+"\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT,shareMessage);
                    startActivity(Intent.createChooser(shareIntent,"choose one"));
                }catch (Exception e){}
                binding.mainViewDrawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_convert_language :
                //show dialog to confirm this option
                binding.mainViewDrawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_log_out :
                signOut();

                break;
        }
        return true;
    }

    private void signOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("تسجيل الخروج");
        builder.setMessage("هل تريد تسجيل الخروج !");
        builder.setPositiveButton("تسجيل الخروج", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        }).setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private void fragmentHandler(Fragment fragment )
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
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
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if (backButtonCount){
            super.onBackPressed();
        }else{
            this.backButtonCount = true;
            Toast.makeText(getApplicationContext(),"press again to exit",Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backButtonCount = false;
                }
            },2000); }
    }
}
