package com.example.fortune.pnpstore;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    public static final String PREFS = "prefFile";

    EditText UsernameEt, PasswordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        onButtonClick();
        UsernameEt = (EditText) findViewById(R.id.edUsername);
        PasswordEt = (EditText) findViewById(R.id.edPassword);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
       // finish();

        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    public void onLogin(View view) {
        final String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();

        HashMap postData = new HashMap();
        postData.put("txtUsername", username);
        postData.put("txtPassword", password);

        PostResponseAsyncTask task = new PostResponseAsyncTask(LoginActivity.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if (s.contains("success")) {
                    //if the username and password match the corresponding contents, then the user
                    //navigates to the appropriate page

                    SharedPreferences preferences = getSharedPreferences(PREFS, 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("username", username);
                    editor.commit();

                    Toast.makeText(LoginActivity.this, "Successful login", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    //if the username and/or password is invalid, a dialog box appears informs them of possible errors
                    AlertDialog.Builder dialogBox = new AlertDialog.Builder(LoginActivity.this);
                    dialogBox.setMessage("These reasons cause errors..."+"\n1. Incorrect username and/or password"+"\n2. Username and/or password fields are empty")
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
        task.execute("https://leary-bricks.000webhostapp.com//FetchUserDataFinal.php");
    }

    public void onButtonClick() {
        Button signUp = (Button) findViewById(R.id.btnSignUp);
        Button FMP = (Button) findViewById(R.id.forgotPasswordButton);
        ImageButton usernameHelpButton = (ImageButton) findViewById(R.id.imageButton1);
        ImageButton passwordHelpButton = (ImageButton) findViewById(R.id.imageButton2);

        /** If the help button next to the username textfield is pressed, a dialog box appears and
         *  informs the user to enter their username to gain access to Pick n Pay's menu
         */
        usernameHelpButton.setOnClickListener (new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       AlertDialog.Builder dialogBox = new AlertDialog.Builder(LoginActivity.this);
                                                       dialogBox.setMessage("Please enter your username")
                                                               .setCancelable(false)
                                                               .setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                                                                   public void onClick(DialogInterface dialog, int which) {
                                                                       dialog.cancel();//closes the dialog box
                                                                   }
                                                               });

                                                       AlertDialog dialog = dialogBox.create();
                                                       dialog.setTitle("Help?");
                                                       dialog.show();
                                                   }
                                               }
        );

        /** If the help button next to the password textfield is pressed, a dialog box appears and
         *  informs the user to enter their password to gain access to Pick n Pay's menu
         */
        passwordHelpButton.setOnClickListener (new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       AlertDialog.Builder dialogBox = new AlertDialog.Builder(LoginActivity.this);
                                                       dialogBox.setMessage("Please enter your password")
                                                               .setCancelable(false)
                                                               .setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                                                                   public void onClick(DialogInterface dialog, int which) {
                                                                       dialog.cancel();//closes the dialog box
                                                                   }
                                                               });

                                                       AlertDialog dialog = dialogBox.create();
                                                       dialog.setTitle("Help?");
                                                       dialog.show();
                                                   }
                                               }
        );

        //By pressing the Sign-Up button, the Sign-up page appears
        signUp.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);
            }
        });

        //By pressing the forgot my password button, the forgot my password page opens
        FMP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(getApplicationContext(), ForgotPassActivity.class);
               startActivity(i);
            }
        });
    }

}

