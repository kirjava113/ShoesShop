package com.example.anna.shoesshop.controller.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anna.shoesshop.MainMenuActivity;
import com.example.anna.shoesshop.R;
import com.example.anna.shoesshop.controller.PicturesActivity;
import com.example.anna.shoesshop.model.product.Product;
import com.example.anna.shoesshop.model.repo.DBUtil;

public class ProductDetailsFragment extends Fragment {

    private Product product;
    private ImageView image;
    private TextView name, collection, category, sizes, price;
    private Button addToBasket;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    public static ProductDetailsFragment newInstance(Product product) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        fragment.product = product;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);
        image = view.findViewById(R.id.imageProduct);
        name = view.findViewById(R.id.productName);
        collection = view.findViewById(R.id.textViewCollectionContent);
        category = view.findViewById(R.id.textViewCategoryContent);
        sizes = view.findViewById(R.id.textViewSizesContent);
        price = view.findViewById(R.id.textViewPrice);
        addToBasket = view.findViewById(R.id.buttonAddBasket);
        setImageListener();
        setButtonListener();

        setContentInfo();
        return view;
    }

    private void setButtonListener() {
        addToBasket.setOnClickListener(view -> {
            MainMenuActivity.getSession().addProductToOrder(product);
            new AlertDialog.Builder(getActivity())
                    .setTitle("Info")
                    .setMessage("Dodano produkt do koszyka")
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, id) -> dialog.cancel())
                    .show();
                });
    }

    private void setImageListener(){
        image.setOnClickListener(view -> {
            PicturesActivity.pictures = product.getBytePictures();
            Intent intent = new Intent(getActivity(), PicturesActivity.class);
            startActivity(intent);
        });
    }

    private void setContentInfo() {
        name.setText(product.getName());
        collection.setText(product.getTypeOfCollection().toString());
        category.setText(product.getCategory().toString());
        sizes.setText(product.getSizes());
        price.setText(product.getPrice().toString());

        @SuppressLint("StaticFieldLeak") AsyncTask<Product, Void, Bitmap> execute = new AsyncTask<Product, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Product... products) {
                Bitmap bitmap = null;
                for (Product p : products) {
                    bitmap = DBUtil.transferToBigBitmap(p.getBytePictures().get(0));
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap msg) {
                if (msg != null) {
                    image.setImageBitmap(msg);
                }
            }

        }.execute(product);

    }

}