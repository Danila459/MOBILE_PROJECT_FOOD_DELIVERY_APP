package com.android.foodorderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    TextInputLayout email, pass;
    TextView Signin, Signinphone;
    TextView Forgotpassword, signup;
    FirebaseAuth Fauth;
    String emailid, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            email = (TextInputLayout) findViewById(R.id.Lemail);
            pass = (TextInputLayout) findViewById(R.id.Lpassword);
            Signin = (TextView) findViewById(R.id.button4);
            signup = (TextView) findViewById(R.id.textView3Create);
            Forgotpassword = (TextView) findViewById(R.id.forgotpass);

            Fauth = FirebaseAuth.getInstance();

            Signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    emailid = email.getEditText().getText().toString().trim();
                    pwd = pass.getEditText().getText().toString().trim();

                    if (isValid()) {
                        final ProgressDialog mDialog = new ProgressDialog(Login.this);
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setCancelable(false);
                        mDialog.setMessage("Подождите...");
                        mDialog.show();

                        Fauth.signInWithEmailAndPassword(emailid, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mDialog.dismiss();

                                    if (Fauth.getCurrentUser().isEmailVerified()) {
                                        mDialog.dismiss();
                                        Toast.makeText(Login.this, "Поздравляю, вы удачно вошли.", Toast.LENGTH_SHORT).show();
                                        Intent z = new Intent(Login.this, MainActivity.class);
                                        startActivity(z);
                                        finish();
                                    } else {
                                        ReusableCodeForAll.ShowAlert(Login.this, "Верификация провалилась", "Вы не верифицировали вашу почту");
                                    }
                                } else {
                                    mDialog.dismiss();
                                    ReusableCodeForAll.ShowAlert(Login.this, "Error", task.getException().getMessage());
                                }
                            }
                        });
                    }
                }
            });
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Login.this, Registration.class));
                    finish();
                }
            });

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid() {
        email.setErrorEnabled(false);
        email.setError("");
        pass.setErrorEnabled(false);
        pass.setError("");

        boolean isValid = false, isValidemail = false, isValidpassword = false;
        if (TextUtils.isEmpty(emailid)) {
            email.setErrorEnabled(true);
            email.setError("Почта обязательна");
        } else {
            if (emailid.matches(emailpattern)) {
                isValidemail = true;
            } else {
                email.setErrorEnabled(true);
                email.setError("Недопустимая почта");
            }
        }
        if(TextUtils.isEmpty(pwd)){
            pass.setErrorEnabled(true);
            pass.setError("Пароль обязателен");
        }else{
            isValidpassword=true;
        }
        isValid=(isValidemail && isValidpassword)?true:false;
        return isValid;
    }
}