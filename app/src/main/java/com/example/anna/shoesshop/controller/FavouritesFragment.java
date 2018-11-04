package com.example.anna.shoesshop.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anna.shoesshop.R;
import com.example.anna.shoesshop.controller.adapters.FavouritesAdapter;
import com.example.anna.shoesshop.model.product.Product;

import java.util.List;

public class FavouritesFragment extends Fragment {

    private List<Product> favProducts;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    public FavouritesFragment() {
        // Required empty public constructor
    }

    public static FavouritesFragment newInstance(List<Product> products) {
        FavouritesFragment fragment = new FavouritesFragment();
        fragment.favProducts = products;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        if(favProducts != null) {
            if(!favProducts.isEmpty()){
                // default view disabled
                View layout = view.findViewById(R.id.no_fav_prod);
                layout.setVisibility(View.INVISIBLE);
                // creating list
                recyclerView = view.findViewById(R.id.fav_prod_recycler_view);

//                Log.i("", mRecyclerView == null? "null" : mRecyclerView.toString());
                adapter = new FavouritesAdapter(getActivity(), favProducts);

                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);

//                getProductsFromDatabase();
            }
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}