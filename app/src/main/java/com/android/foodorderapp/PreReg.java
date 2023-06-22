package com.android.foodorderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PreReg extends AppCompatActivity {
    TextView signinemail, signup;
    ImageView bgimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_reg);


        signinemail=(TextView)findViewById(R.id.SignWIthEmail);
        signup=(TextView)findViewById(R.id.Registration);

        signinemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signemail = new Intent(PreReg.this,Login.class);
                signemail.putExtra("Home", "Email");
                startActivity(signemail);
                finish();
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(PreReg.this,Registration.class);
                signup.putExtra("Home", "SignUp");
                startActivity(signup);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        System.gc();
    }
}