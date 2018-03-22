package com.example.fortune.pnpstore;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;

public class CardActivity extends AppCompatActivity {

    Spinner cardSpinner, monthSpinner, yearSpinner;
    EditText Cardno, Cvc, cHolder;
    CheckBox saveCard;
    public static final String PREFS = "prefFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        saveCard = (CheckBox)findViewById(R.id.Cardsave) ;

        Button payButton = (Button) findViewById(R.id.btnPay);

        Cardno = (EditText) findViewById(R.id.Cardno);
        Cvc = (EditText) findViewById(R.id.Cvc);
        cHolder = (EditText)findViewById(R.id.Cardholdername);
        SharedPreferences prefs = getSharedPreferences(PREFS,MODE_PRIVATE);
        final String restoredText = prefs.getString("username", null);


        cardSpinner = (Spinner) findViewById(R.id.spinnercardtype);

        ArrayAdapter<String> myAdaptor = new ArrayAdapter<String>(CardActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));

        myAdaptor.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        cardSpinner.setAdapter(myAdaptor);

        cardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Text = parent.getSelectedItem().toString();
                TextView Cvctxt = (TextView) findViewById(R.id.Cvctxt);
                if (Text.equals("Maestro")) {
                    Cvc.setVisibility(View.INVISIBLE);
                    Cvctxt.setVisibility(View.INVISIBLE);
                } else
                {
                    Cvc.setVisibility(View.VISIBLE);
                    Cvctxt.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        monthSpinner = (Spinner) findViewById(R.id.spinnerMonth);

        ArrayAdapter<String> myAdaptormonth = new ArrayAdapter<String>(CardActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.month));

        myAdaptor.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        monthSpinner.setAdapter(myAdaptormonth);

        yearSpinner = (Spinner) findViewById(R.id.spinnerYear);

        ArrayAdapter<String> myAdaptoryear = new ArrayAdapter<String>(CardActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.year));

        myAdaptor.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        yearSpinner.setAdapter(myAdaptoryear);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap postData = new HashMap();

                postData.put("cartType", cardSpinner.getSelectedItem().toString());
                postData.put("cardHolder", cHolder.getText().toString());
                postData.put("cardNo", Cardno.getText().toString());
                postData.put("expMonth", monthSpinner.getSelectedItem().toString());
                postData.put("expYear", yearSpinner.getSelectedItem().toString());
                postData.put("Cvc", Cvc.getText().toString());
                postData.put("customer", restoredText);


                PostResponseAsyncTask cardTask = new PostResponseAsyncTask(CardActivity.this, postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        if (Cardno.getText().toString().length() != 5 || Cvc.getText().toString().length() != 3 || cHolder.getText().toString().isEmpty()) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(CardActivity.this);
                            dialog.setMessage("Please check the details entered are correct")
                                    .setCancelable(false)
                                    .setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog box = dialog.create();
                            box.setTitle("ERROR MESSAGE");
                            box.show();
                        } else {

                            Toast.makeText(CardActivity.this, "Payment is Successful", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(CardActivity.this, ThanksActivity.class);
                            startActivity(intent);
                        }
                    }

                    });

                cardTask.execute("https://leary-bricks.000webhostapp.com/Payment1.php");
                }
            });
        }
        }