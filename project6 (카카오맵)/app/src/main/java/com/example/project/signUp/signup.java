package com.example.project.signUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project.R;

public class signup extends AppCompatActivity {
    private TextView id,pw,name,phone;
    private EditText edid,edpw,edname,edphone;
    private Button signup_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        id=findViewById(R.id.id_textview);
        pw=findViewById(R.id.pw_textview);
        name=findViewById(R.id.name_textview);
        phone=findViewById(R.id.phone_textview);

        edid=findViewById(R.id.id_edtext);
        edpw=findViewById(R.id.pw_edtext);
        edname=findViewById(R.id.name_edtext);
        edphone=findViewById(R.id.phone_edtext);

        signup_btn=findViewById(R.id.signup_btn);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean signup=true;
                if(signup)
                    finish();
            }
        });


        Intent intent = getIntent();

    }
    protected void onDestroy() {
        super.onDestroy();
        Intent intent=getIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }
}