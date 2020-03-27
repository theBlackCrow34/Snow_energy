package com.sale.refaq.snow_energy.ui.inti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sale.refaq.snow_energy.ui.steper.EmailStep;
import com.sale.refaq.snow_energy.ui.steper.NameStep;
import com.sale.refaq.snow_energy.ui.steper.PasswordStep;
import com.sale.refaq.snow_energy.R;
import com.sale.refaq.snow_energy.ui.steper.UserNameStep;
import com.sale.refaq.snow_energy.databinding.ActivityRegisterDataBinding;
import com.sale.refaq.snow_energy.ui.MainViewActivity;

import java.util.HashMap;

import ernestoyaquello.com.verticalstepperform.listener.StepperFormListener;
// if we want to user stepper we miust use ->>>> implements StepperFormListener

public class RegisterDataActivity extends AppCompatActivity  {

    /*private NameStep nameStep;
    private UserNameStep userNameStep;
    private EmailStep emailStep;
    private PasswordStep passwordStep;*/
    ActivityRegisterDataBinding binding;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register_data);
        binding.setLifecycleOwner(this);

        firebaseAuth = FirebaseAuth.getInstance();
       /* nameStep = new NameStep("الإسم");
        userNameStep = new UserNameStep("اسم المستخدم");
        passwordStep = new PasswordStep("كلمة المرور");
        emailStep = new EmailStep("البريد الإلكتروني");*/

        /*binding.stepperForm.setup(this,nameStep,userNameStep,emailStep,passwordStep)
                .allowNonLinearNavigation(true)
                .displayBottomNavigation(false)
                .lastStepNextButtonText("التسجيل")
                .init();
*/
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = binding.email.getText().toString().trim();
                String password = binding.password.getText().toString().trim();
                String name = binding.name.getText().toString().trim();
                String username = binding.username.getText().toString().trim();

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
                registerUser(email,password);
            }
        });


    }
    private void registerUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    String email = user.getEmail();
                    String uid = user.getUid();

                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("email",email);
                    hashMap.put("status","client");
                    hashMap.put("password",binding.password.getText().toString());
                    hashMap.put("uid",uid);
                    hashMap.put("phone","");
                    hashMap.put("name",binding.name.getText().toString());
                    hashMap.put("username",binding.username.getText().toString());
                    hashMap.put("image","");
                    hashMap.put("cover","");
                    hashMap.put("onlineStatus","online");
                    hashMap.put("typingTo","noOne");


                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference("Users");

                    reference.child(uid).setValue(hashMap);

                    //Toast.makeText(getApplicationContext(), "registered "+user, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainViewActivity.class));
                    finish();
                }else
                {
                    Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
   /* @Override
    public void onCompletedForm() {

        Intent intent = new Intent(getApplicationContext(), MainViewActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCancelledForm() {

    }
    //no any thing known
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("user_name", userNameStep.getStepData());
        savedInstanceState.putString("name", nameStep.getStepData());
        savedInstanceState.putString("email", emailStep.getStepData());
        savedInstanceState.putString("password", passwordStep.getStepData());

        // IMPORTANT: The call to the super method must be here at the end.
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState.containsKey("user_name")) {
            String userName = savedInstanceState.getString("user_name");
            userNameStep.restoreStepData(userName);
        }

        if(savedInstanceState.containsKey("name")) {
            String name = savedInstanceState.getString("name");
            nameStep.restoreStepData(name);
        }
        if(savedInstanceState.containsKey("email")) {
            String email = savedInstanceState.getString("email");
            emailStep.restoreStepData(email);
        }
        if(savedInstanceState.containsKey("password")) {
            String password = savedInstanceState.getString("password");
            passwordStep.restoreStepData(password);
        }


        // IMPORTANT: The call to the super method must be here at the end.
        super.onRestoreInstanceState(savedInstanceState);
    }*/
}
