package com.example.fortune.pnpstore.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fortune.pnpstore.Interfaces.Products;
import com.example.fortune.pnpstore.Interfaces.UILConfig;
import com.example.fortune.pnpstore.R;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;


public class HomeDetailFragment extends Fragment {
    public static final String PREFS = "prefFile";
    final String LOG = "HomeDetailFragment";

    TextView tvName;
    TextView tvDesc;
    TextView tvPrice;
    String img_url;
    ImageView imageView;
    Button btnAddtocart;

    public HomeDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home_detail, container, false);

        ImageLoader.getInstance().init(UILConfig.config(HomeDetailFragment.this.getActivity()));

        Products product = (Products) getArguments().getSerializable("productList");

        tvName = (TextView) v.findViewById(R.id.tvName);
        tvDesc = (TextView) v.findViewById(R.id.tvDesc);
        tvPrice = (TextView) v.findViewById(R.id.tvPrice);
        img_url = product.img_url;
        imageView = (ImageView)v.findViewById(R.id.ivImage);
        btnAddtocart = (Button)v.findViewById(R.id.btnCheckout);

        final TextView name, desc, price;
        final String qty, imurl;

        //Load all the products
        if(product !=null)
        {
            tvName.setText(product.name);
            tvDesc.setText(product.description);
            tvPrice.setText("" + product.price);
            ImageLoader.getInstance().displayImage(img_url, imageView);
        }

        SharedPreferences preferences = HomeDetailFragment.this.getActivity().getSharedPreferences(PREFS, 0);
        final String customer = preferences.getString("username", null);

        name = tvName;
        desc = tvDesc;
        qty = ""+1;
        price = tvPrice;
        imurl = img_url;

        btnAddtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HashMap postData = new HashMap();

                postData.put("txtName", name.getText().toString());
                postData.put("txtDesc", desc.getText().toString());
                postData.put("txtQty", qty);
                postData.put("txtPrice",price.getText().toString());
                postData.put("txtImageUrl", imurl);
                postData.put("txtCustomer", customer);

                PostResponseAsyncTask insertTask = new PostResponseAsyncTask(
                        HomeDetailFragment.this.getActivity(), postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(LOG, s);
                        String aName = name.toString();
                        String aQty = qty.toString();

                        if(s.contains("success"))
                        {
                            if(postData.equals(aName))
                            {
                                aQty += 1;
                            }
                            Toast.makeText(HomeDetailFragment.this.getActivity(), "Added to Cart", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                insertTask.execute("https://leary-bricks.000webhostapp.com/AddToCart.php");
            }
        });
        return v;
    }
}