package com.example.project.logIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.signUp.signup;

public class login extends AppCompatActivity {
    private TextView login_id,login_pw,login_signup;
    private EditText login_edid,login_edpw;
    private Button login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_id=findViewById(R.id.login_id_textview);
        login_pw=findViewById(R.id.login_pw_textview);
        login_signup=findViewById(R.id.login_signup_textview);

        login_edid=findViewById(R.id.login_id_edtext);
        login_edpw=findViewById(R.id.login_pw_edtext);

        login_btn=findViewById(R.id.login_login_btn);

        login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, signup.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean login=true;
                if(login)
                    finish();
            }
        });
        Intent intent= getIntent();
    }
}