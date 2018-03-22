package com.example.fortune.pnpstore.Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.amigold.fundapter.interfaces.DynamicImageLoader;
import com.amigold.fundapter.interfaces.ItemClickListener;
import com.example.fortune.pnpstore.Interfaces.Products;
import com.example.fortune.pnpstore.Interfaces.UILConfig;
import com.example.fortune.pnpstore.R;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlcoholFragment extends Fragment implements AsyncResponse{
    public static final String PREFS = "prefFile";

    //ImageButton beer, whisky, rum, vodka;

    final static String url = "http://moeketsimakinta.000webhostapp.com/Beer.php";

    private ArrayList<Products> productList;
    private ListView lv;
    FunDapter<Products> adapter;
    View view;


    public AlcoholFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_alcohol, container, false);

        ImageLoader.getInstance().init(UILConfig.config(AlcoholFragment.this.getActivity()));

        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(AlcoholFragment.this.getActivity(), this);
        taskRead.execute(url);

        /*beer = (ImageButton)view.findViewById(R.id.ibBeer);
        whisky = (ImageButton)view.findViewById(R.id.ibWhisky);
        rum = (ImageButton)view.findViewById(R.id.ibRum);
        vodka = (ImageButton)view.findViewById(R.id.ibVodka);



        rum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RumFragment rumFragment = new RumFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().add(R.id.content_main, rumFragment,
                        rumFragment.getTag()).hide(AlcoholFragment.this).addToBackStack("Rum").commit();
            }
        });

        whisky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WhiskyFragment whiskyFragment = new WhiskyFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().add(R.id.content_main, whiskyFragment,
                        whiskyFragment.getTag()).hide(AlcoholFragment.this).addToBackStack("Whisky").commit();
            }
        });

        beer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BeerFragment beerFragment = new BeerFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().add(R.id.content_main, beerFragment,
                        beerFragment.getTag()).hide(AlcoholFragment.this).addToBackStack("Beer").commit();

            }
        });

        vodka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VodkaFragment vodkaFragment = new VodkaFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().add(R.id.content_main, vodkaFragment,
                        vodkaFragment.getTag()).hide(AlcoholFragment.this).addToBackStack("Vodka").commit();

            }
        });*/

        return view;
    }

    @Override
    public void processFinish(String s) {

        productList = new JsonConverter<Products>().toArrayList(s, Products.class);

        BindDictionary dic = new BindDictionary();

        dic.addStringField(R.id.tvName, new StringExtractor<Products>() {
            @Override
            public String getStringValue(Products item, int position) {
                return item.name;
            }
        });

        dic.addStringField(R.id.tvDesc, new StringExtractor<Products>() {
            @Override
            public String getStringValue(Products item, int position) {
                return item.description;
            }
        }).visibilityIfNull(View.GONE);

        dic.addStringField(R.id.tvPrice, new StringExtractor<Products>() {
            @Override
            public String getStringValue(Products item, int position) {
                return ""+item.price;
            }
        });

        dic.addDynamicImageField(R.id.ivImage, new StringExtractor<Products>() {
            @Override
            public String getStringValue(Products item, int position) {
                return item.img_url;
            }
        }, new DynamicImageLoader() {
            @Override
            public void loadImage(String url, ImageView img) {
                //Set image
                ImageLoader.getInstance().displayImage(url, img);
            }
        });

        dic.addBaseField(R.id.btnCart).onClick(new ItemClickListener() {
            @Override
            public void onClick(Object item, int position, View view) {

                SharedPreferences preferences = AlcoholFragment.this.getActivity().getSharedPreferences(PREFS, 0);
                final String customer = preferences.getString("username", null);

                final String qty = ""+1;
                final Products selectedItem = productList.get(position);
                final HashMap postData = new HashMap();

                postData.put("txtName", selectedItem.name);
                postData.put("txtDesc", selectedItem.description);
                postData.put("txtQty", qty);
                postData.put("txtPrice", ""+selectedItem.price);
                postData.put("txtImageUrl", selectedItem.img_url);
                postData.put("txtCustomer", customer);

                PostResponseAsyncTask insertTask = new PostResponseAsyncTask(
                        AlcoholFragment.this.getActivity(), postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        // Log.d(LOG, s);
                        String aName = selectedItem.name;
                        String aQty = qty.toString();

                        if(s.contains("success"))
                        {
                            if(postData.equals(aName))
                            {
                                aQty += 1;
                            }
                            Toast.makeText(AlcoholFragment.this.getActivity(), "Added to Cart", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                insertTask.execute("https://moeketsimakinta.000webhostapp.com/AddToCart.php");

            }
        });

        adapter = new FunDapter<>(AlcoholFragment.this.getActivity(), productList, R.layout.product_row, dic);
        lv = (ListView)view.findViewById(R.id.lvProduct);
        lv.setAdapter(adapter);
    }
}
