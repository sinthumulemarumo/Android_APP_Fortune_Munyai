package com.example.fortune.pnpstore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;

public class ThanksActivity extends AppCompatActivity {
    final String LOG = "ThanksActivity";
    public EditText location,contact,time;
    Button home, track;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        location = (EditText)findViewById(R.id.location);
        contact = (EditText)findViewById(R.id.contact);
        time = (EditText)findViewById(R.id.time);

        home = (Button)findViewById(R.id.btnHome);
        track = (Button)findViewById(R.id.btnTrack);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Intent intent = new Intent(ThanksActivity.this, MainActivity.class);
                    startActivity(intent);
            }
        });

        track.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HashMap postData = new HashMap();

                    postData.put("location", location.getText().toString());
                    postData.put("contact", contact.getText().toString());
                    postData.put("time", time.getText().toString());

                    PostResponseAsyncTask cardTask = new
                            PostResponseAsyncTask(ThanksActivity.this, postData, new
                            AsyncResponse() {
                                @Override
                                public void processFinish(String s) {
                                    if((location.getText().toString().isEmpty() ||
                                            contact.getText().toString().isEmpty() ||
                                            time.getText().toString().isEmpty()))
                                    {
                                        AlertDialog.Builder dialogBox = new
                                                AlertDialog.Builder(ThanksActivity.this);
                                        dialogBox.setMessage("Not successful ")
                                                .setCancelable(false)
                                                .setNegativeButton("Try again!",
                                                        new DialogInterface.OnClickListener() {
                                                            public void
                                                            onClick(DialogInterface dialog, int which) {
                                                                dialog.cancel();//closes the dialog box
                                                            }
                                                        });
                                        AlertDialog dialog = dialogBox.create();
                                        dialog.setTitle("Error");
                                        dialog.show();


                                    }
                                    else
                                    {
                                        Log.d(LOG, s);
                                        Toast.makeText(ThanksActivity.this, "Thank you order will be delivered", Toast.LENGTH_LONG).show();
                                                Intent intent = new
                                                        Intent(ThanksActivity.this, TrackOrderActivity.class);
                                        startActivity(intent);

                                    }
                                }
                            });
                    //Intent intent = new Intent(ThanksActivity.this, TrackOrderActivity.class );//startActivity(intent);

                    cardTask.execute("https://leary-bricks.000webhostapp.com/AddLocation.php");

                }
            });
        }

    }

