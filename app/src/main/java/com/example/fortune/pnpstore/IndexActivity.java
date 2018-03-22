package com.example.fortune.pnpstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class IndexActivity extends AppCompatActivity{

    String LOG = "IndexActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        OnButtonClick();

    }

    public void OnButtonClick() {
        Button Return = (Button) findViewById(R.id.loginReturn);
        Button ButtonSignUp = (Button) findViewById(R.id.btnSignUp);

        //Upon being pressed, user is taken back to the login page
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });


        //By pressing the Sign-Up button, the Sign-up page appears
        ButtonSignUp.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);
            }
        });







}



}
