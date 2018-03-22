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
public class SoftDrinkFragment extends Fragment implements AsyncResponse{
    public static final String PREFS = "prefFile";

    final static String url = "https://moeketsimakinta.000webhostapp.com/SoftDrinks.php";

    private ArrayList<Products> productList;
    private ListView lv;
    FunDapter<Products> adapter;

    View view;
    //ImageButton coke, fanta, sprite, pepsi;

    public SoftDrinkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_softdrink, container, false);

        ImageLoader.getInstance().init(UILConfig.config(SoftDrinkFragment.this.getActivity()));

        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(SoftDrinkFragment.this.getActivity(), this);
        taskRead.execute(url);

        /*coke = (ImageButton)view.findViewById(R.id.s_coca);
        fanta = (ImageButton)view.findViewById(R.id.s_fanta);
        sprite = (ImageButton)view.findViewById(R.id.s_sprite);
        pepsi = (ImageButton)view.findViewById(R.id.s_pepsi);

        coke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CokeFragment cokeFragment = new CokeFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().add(R.id.content_main, cokeFragment,
                        cokeFragment.getTag()).hide(SoftDrinkFragment.this).addToBackStack("Coke").commit();
            }
        });

        fanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FantaFragment fantaFragment = new FantaFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().add(R.id.content_main, fantaFragment,
                        fantaFragment.getTag()).hide(SoftDrinkFragment.this).addToBackStack("Fanta").commit();
            }
        });

        sprite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpriteFragment spriteFragment = new SpriteFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().add(R.id.content_main, spriteFragment,
                        spriteFragment.getTag()).hide(SoftDrinkFragment.this).addToBackStack("Sprite").commit();
            }
        });

        pepsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PepsiFragment pepsiFragment = new PepsiFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().add(R.id.content_main, pepsiFragment,
                        pepsiFragment.getTag()).hide(SoftDrinkFragment.this).addToBackStack("Pepsi").commit();
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

                SharedPreferences preferences = SoftDrinkFragment.this.getActivity().getSharedPreferences(PREFS, 0);
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
                        SoftDrinkFragment.this.getActivity(), postData, new AsyncResponse() {
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
                            Toast.makeText(SoftDrinkFragment.this.getActivity(), "Added to Cart", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                insertTask.execute("https://leary-bricks.000webhostapp.com/AddToCart.php");
            }
        });

        adapter = new FunDapter<>(SoftDrinkFragment.this.getActivity(), productList, R.layout.product_row, dic);
        lv = (ListView)view.findViewById(R.id.lvProduct);
        lv.setAdapter(adapter);
    }

}
