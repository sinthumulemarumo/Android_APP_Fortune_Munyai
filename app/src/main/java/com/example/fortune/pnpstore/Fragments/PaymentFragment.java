package com.example.fortune.pnpstore.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.fortune.pnpstore.CardActivity;
import com.example.fortune.pnpstore.PaypalActivity;
import com.example.fortune.pnpstore.R;
import com.example.fortune.pnpstore.ThanksActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment {

    View view;
    Button paypal, card, cash;
    ImageButton infoPaypal, infoCard, infoCash;

    public PaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_payment, container, false);

        paypal = (Button)view.findViewById(R.id.btnPaypal);
        card = (Button)view.findViewById(R.id.btnCard);
        cash = (Button)view.findViewById(R.id.btnCash);

        infoPaypal = (ImageButton)view.findViewById(R.id.info_paypal);
        infoCard = (ImageButton)view.findViewById(R.id.info_card);
        infoCash = (ImageButton)view.findViewById(R.id.info_cash);

        paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentFragment.this.getActivity(), PaypalActivity.class);
                startActivity(intent);
            }
        });

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentFragment.this.getActivity(), CardActivity.class);
                startActivity(intent);
            }
        });

        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentFragment.this.getActivity(), ThanksActivity.class);
                startActivity(intent);
            }
        });

        //Info buttons
        infoPaypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(PaymentFragment.this.getActivity());
                dialog.setTitle("Paypal");
                dialog.setMessage("Pay by your PayPal account");
                dialog.show();

            }
        });

        infoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(PaymentFragment.this.getActivity());
                dialog.setTitle("Card");
                dialog.setMessage("Pay by Visa, Visa Debit, Mastercard, Maestro or American Express");
                dialog.show();
            }
        });

        infoCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(PaymentFragment.this.getActivity());
                dialog.setTitle("Cash");
                dialog.setMessage("Pay by cash when collecting your order at Pick n Pay");
                dialog.show();


            }
        });

        return view;
    }

}
