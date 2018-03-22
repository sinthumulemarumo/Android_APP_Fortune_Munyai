package com.example.fortune.pnpstore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;

public class ForgotPassActivity extends AppCompatActivity {
    EditText username, newPass, newPass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        TextView title = (TextView) findViewById(R.id.FPTitle);

        //Underlines the page's headline
        title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        OnButtonClick();
    }

    public void OnButtonClick() {
        Button Return = (Button) findViewById(R.id.returnButton);
        Button updateBtn = (Button) findViewById(R.id.setAsNewPassword);
        username = (EditText) findViewById(R.id.usernameET);
        newPass = (EditText) findViewById(R.id.newPasswordET);
        newPass2 = (EditText) findViewById(R.id.reenterNewPasswordET);


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap postData = new HashMap();
                postData.put("txtUsername", username.getText().toString());
                postData.put("txtPassword", newPass2.getText().toString());
                PostResponseAsyncTask taskUpdate = new PostResponseAsyncTask(ForgotPassActivity.this, postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {

                        if (s.contains("Update Successful")) {
                            Toast.makeText(ForgotPassActivity.this, "Update Successful", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ForgotPassActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else if (s.contains("Update Unsuccessful") || !(newPass.getText().equals(newPass2.getText().toString())) || ((newPass.getText().length() < 8) || (newPass2.getText().length() < 8))) {
                            AlertDialog.Builder dialogBox = new AlertDialog.Builder(ForgotPassActivity.this);
                            dialogBox.setMessage("These reasons causes errors..."+"\n1. Both passwords are not identical"+"\n2. One (or both) of your passwords are not 8 characters long"+"\n3. Your username is incorrect (or doesn't exist)")
                                    .setCancelable(false)
                                    .setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();//closes the dialog box
                                        }
                                    });

                            AlertDialog dialog = dialogBox.create();
                            dialog.setTitle("An Error was Detected!");
                            dialog.show();
                        }
                    }
                });
                taskUpdate.execute("https://leary-bricks.000webhostapp.com/EditPassword.php");
            }
        });

        //Upon being pressed, takes the user to the login page
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }
}
