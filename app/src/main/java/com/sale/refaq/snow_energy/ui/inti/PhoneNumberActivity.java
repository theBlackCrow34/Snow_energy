package com.sale.refaq.snow_energy.ui.inti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rilixtech.widget.countrycodepicker.Country;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;
import com.sale.refaq.snow_energy.R;
import com.sale.refaq.snow_energy.databinding.ActivityPhoneNumberBinding;
import com.sale.refaq.snow_energy.ui.MainViewActivity;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class PhoneNumberActivity extends AppCompatActivity {

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;
    int counter = 0;
    ActivityPhoneNumberBinding binding;
    //String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_phone_number);
        binding.setLifecycleOwner(this);

        mAuth = FirebaseAuth.getInstance();
        binding.constrainVerify.setVisibility(View.GONE);

        binding.ccp.registerPhoneNumberTextView(binding.phoneNumberInput);
        binding.ccp.setCountryForPhoneCode(+20);
        binding.ccp.enableSetCountryByTimeZone(true);
        //binding.phoneNumberInput.setText(binding.ccp.getFullNumberWithPlus());

        //step view
        binding.stepView.getState()
                .animationType(StepView.ANIMATION_ALL)
                // You should specify only stepsNumber or steps array of strings.
                // In case you specify both steps array is chosen.
                .steps(new ArrayList<String>() {{
                    add("phone number");
                    add("verify sms");
                }})
                // You should specify only steps number or steps array of strings.
                // In case you specify both steps array is chosen.
                .stepsNumber(2)
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                // other state methods are equal to the corresponding xml attributes
                .commit();

        binding.ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {

                binding.phoneNumberInput.setText("");
                //binding.phoneNumberInput.setText(binding.ccp.getFullNumberWithPlus());
            }
        });
        binding.skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterDataActivity.class));
            }
        });
        binding.sendVerCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = binding.phoneNumberInput.getText().toString();
                if (TextUtils.isEmpty(phoneNumber))
                {
                    Toast.makeText(getApplicationContext(),"phone number is required", Toast.LENGTH_SHORT).show();
                }else
                {
                    //Toast.makeText(PhoneNumberActivity.this, ""+phoneNumber, Toast.LENGTH_SHORT).show();
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+2"+phoneNumber,        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            PhoneNumberActivity.this,               // Activity (for callback binding)
                            callbacks);        // OnVerificationStateChangedCallbacks
                }
            }
        });
        binding.verCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String verificationCode = getAllPassword();
                if (TextUtils.isEmpty(verificationCode))
                {
                    Toast.makeText(getApplicationContext(), R.string.write_verification_code, Toast.LENGTH_SHORT).show();
                }else
                {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);

                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential)
            {

                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e)
            {
                //dialog.dismiss();
                Toast.makeText(getApplicationContext(), R.string.invalid_phone_number, Toast.LENGTH_SHORT).show();
                //binding.phoneNumberInput.setText("");

                /*sendVerificationCodeButton.setVisibility(View.VISIBLE);
                inputPhoneNumber.setVisibility(View.VISIBLE);
                ccp.setVisibility(View.VISIBLE);

                inputVerificationCode.setVisibility(View.INVISIBLE);
                verifyButton.setVisibility(View.INVISIBLE);*/
            }
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {

                binding.stepView.go(1,true);
                timer();
                binding.constrainNumber.setVisibility(View.GONE);
                binding.constrainVerify.setVisibility(View.VISIBLE);
                checkPassword();
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                //Toast.makeText(getApplicationContext(), R.string.code_has_sent, Toast.LENGTH_SHORT).show();

                /*sendVerificationCodeButton.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out));
                inputPhoneNumber.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out));
                sendVerificationCodeButton.setVisibility(View.INVISIBLE);
                inputPhoneNumber.setVisibility(View.INVISIBLE);
                ccp.setVisibility(View.INVISIBLE);

                inputVerificationCode.setVisibility(View.VISIBLE);
                inputVerificationCode.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in));
                verifyButton.setVisibility(View.VISIBLE);
                verifyButton.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in));*/
                /*Intent intent = new Intent(getApplicationContext(),PassCodeLoginActivity.class);
                intent.putExtra("verifivationId",verificationId);
                startActivity(intent);*/
                //Toast.makeText(LoginActivity.this, "code is "+verificationId, Toast.LENGTH_SHORT).show();
                //sendVerificationCodeButton.setText(verificationId);
            }
        };

        binding.sendCode.setText("انتظر ارسال الكود مرة أخري بعد");
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        //dialog.show();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {

                            /*FirebaseUser user = mAuth.getCurrentUser();
                            if (task.getResult().getAdditionalUserInfo().isNewUser()) // note new user condition
                            {
                                String phone = user.getPhoneNumber();
                                String uid = user.getUid();

                                HashMap<String,Object> hashMap = new HashMap<>();
                                hashMap.put("phone",phone);
                                hashMap.put("uid",uid);
                                hashMap.put("photo","");
                                hashMap.put("nickname","");
                                hashMap.put("name","");
                                hashMap.put("isProfile","no");
                                hashMap.put("isReport","no");
                                hashMap.put("notes","");
                                hashMap.put("isDead","no");
                                hashMap.put("disableAccount","no");
                                hashMap.put("sharedPhotos","no");
                                hashMap.put("days","no");
                                hashMap.put("count","no");
                                hashMap.put("rang","no");
                                hashMap.put("password","");

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference("Users");
                                reference.child(uid).setValue(hashMap);

                                startActivity(new Intent(getApplicationContext(),EditProfileActivity.class));
                                finishAffinity();
                            }else {
                               *//* startActivity(new Intent(getApplicationContext(),DashBoardActivity.class));
                                finishAffinity();*//*
                            }*/

                            startActivity(new Intent(getApplicationContext(), MainViewActivity.class));
                            finish();
                        } else {
                            //dialog.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(getApplicationContext(), "Error : "+ message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void timer()
    {
        new CountDownTimer(120000,1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) millisUntilFinished/1000;
                if (seconds >= 60)
                {
                    if ((seconds-60) <10)
                        binding.timer.setText("01:0"+(seconds-60));
                    else
                        binding.timer.setText("01:"+(seconds-60));

                }else if (seconds < 60)
                {
                    if ((seconds-counter) < 10)
                        binding.timer.setText("00:0"+(seconds-counter));
                    else
                        binding.timer.setText("00:"+(seconds-counter));
                    counter++;
                }

            }

            @Override
            public void onFinish() {
                binding.sendCode.setText("ارسال الكود");
            }
        }.start();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    private void checkPassword()
    {
        SetCursor(binding.txInput1,binding.txInput2);
        SetCursor(binding.txInput2,binding.txInput3);
        SetCursor(binding.txInput3,binding.txInput4);
        SetCursor(binding.txInput4,binding.txInput5);
        SetCursor(binding.txInput5,binding.txInput6);
        SetCursor(binding.txInput6,binding.txInput6);
    }
    private void SetCursor(final EditText tx_bg, final EditText tx_forward)
    {
        tx_bg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (tx_bg == tx_forward && s.length()!=0)
                {
                    // binding.progressDialog.setVisibility(View.VISIBLE);

                    binding.verCodeButton.setEnabled(true);
                    String all = getAllPassword();
                    //Toast.makeText(PassCodeLoginActivity.this, ""+all, Toast.LENGTH_SHORT).show();
                    /*if (all.equals(password)){
                        Intent intent = new Intent(getApplicationContext(),DashBoardActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }*/ //must entry automated
                }else if (s.length()!=0)
                {
                    tx_forward.setFocusableInTouchMode(true);
                    tx_forward.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (getAllPassword().length()==6)
                    binding.verCodeButton.setEnabled(true);
                else
                    binding.verCodeButton.setEnabled(false);
            }
        });
    }
    private String getAllPassword()
    {
        String c_password = binding.txInput1.getText().toString() + binding.txInput2.getText().toString()
                + binding.txInput3.getText().toString() + binding.txInput4.getText().toString()
                + binding.txInput5.getText().toString() + binding.txInput6.getText().toString();
        return c_password;
    }
}
