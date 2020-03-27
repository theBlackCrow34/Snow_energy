package com.sale.refaq.snow_energy.ui.inti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;
import com.sale.refaq.snow_energy.R;
import com.sale.refaq.snow_energy.databinding.ActivityLoginBinding;
import com.sale.refaq.snow_energy.ui.MainViewActivity;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.setLifecycleOwner(this);

        auth = FirebaseAuth.getInstance();

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.email.getText().toString().trim();
                String password = binding.password.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    binding.email.setError("invalid email !");
                    binding.email.setFocusable(true);
                    return;
                }
                if (password.length()<6)
                {
                    binding.password.setError("password must be al least 6 characters!");
                    binding.password.setFocusable(true);
                    return;
                }
                signInUsre(email,password);
            }
        });

        binding.textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegisterDataActivity.class);
                startActivity(intent);
            }
        });
        /*binding.materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.scrollView.setBackgroundColor(Color.BLACK);
                binding.constrainView.setBackgroundColor(Color.BLACK);
                binding.constrainView.getBackground().setAlpha(128);
                binding.scrollView.getBackground().setAlpha(128);
            }
        });*/

    }
    private void signInUsre(String email, String password)
    {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    //FirebaseUser user = auth.getCurrentUser();
                    startActivity(new Intent(getApplicationContext(), MainViewActivity.class));
                    finish();
                }else
                {
                    Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
