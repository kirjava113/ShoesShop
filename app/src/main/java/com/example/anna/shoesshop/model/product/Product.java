package com.example.anna.shoesshop.model.product;

import android.graphics.Bitmap;

import com.example.anna.shoesshop.model.Price;
import com.example.anna.shoesshop.model.database.ProductDb;
import com.example.anna.shoesshop.model.database.enums.CategoryDb;
import com.example.anna.shoesshop.model.database.enums.CollectionDb;
import com.example.anna.shoesshop.model.database.enums.SizeDb;
import com.example.anna.shoesshop.model.database.enums.TypeDb;
import com.example.anna.shoesshop.model.repo.DBUtil;

import java.util.List;
import java.util.Objects;

public class Product {
    //generalForSegregation
    private Type typeOfProduct;
    private Category category;
    private Collection typeOfCollection;
    //generalInfo
    private long numberOfProduct;
    private Price price;
    private String name;
    private List<Bitmap> pictures;
    private List<byte[]> bytePictures;
    //details
    private List<Size> listOfSizes;
    private Size selectedSize = Size.universal;
    //promo
    private Price normalPrice;

    public Product(Type typeOfProduct,
                     Category category,
                     Collection typeOfCollection,
                     long numberOfProduct,
                     Price price,
                     String name,
                     List<Bitmap> pictures,
                     List<Size> listOfSizes,
                     Size selectedSize,
                     Price normalPrice) {
        this.typeOfProduct = typeOfProduct;
        this.category = category;
        this.typeOfCollection = typeOfCollection;
        this.numberOfProduct = numberOfProduct;
        this.price = price;
        this.name = name;
        this.pictures = pictures;
        this.listOfSizes = listOfSizes;
        this.selectedSize = selectedSize;
        this.normalPrice = normalPrice;
    }

    public Product(TypeDb typeOfProduct,
                   CategoryDb category,
                   CollectionDb typeOfCollection,
                   long numberOfProduct,
                   Price price,
                   String name,
                   List<Bitmap> pictures,
                   List<Size> listOfSizes,
                   SizeDb selectedSize,
                   Price normalPrice) {
        this.typeOfProduct = DBUtil.transferToEnum(typeOfProduct);
        this.category = DBUtil.transferToEnum(category);
        this.typeOfCollection = DBUtil.transferToEnum(typeOfCollection);
        this.numberOfProduct = numberOfProduct;
        this.price = price;
        this.name = name;
        this.pictures = pictures;
        this.listOfSizes = listOfSizes;
        this.selectedSize = DBUtil.transferToEnum(selectedSize);
        this.normalPrice = normalPrice;
    }

    public Product(Type typeOfProduct, Category category, Collection typeOfCollection,
                   long numberOfProduct, Price price, String name, List<Bitmap> pictures,
                   List<byte[]> bytePictures, List<Size> listOfSizes, Size selectedSize,
                   Price normalPrice) {
        this.typeOfProduct = typeOfProduct;
        this.category = category;
        this.typeOfCollection = typeOfCollection;
        this.numberOfProduct = numberOfProduct;
        this.price = price;
        this.name = name;
        this.pictures = pictures;
        this.bytePictures = bytePictures;
        this.listOfSizes = listOfSizes;
        this.selectedSize = selectedSize;
        this.normalPrice = normalPrice;
    }

    public List<byte[]> getBytePictures() {
        return bytePictures;
    }

    public void setBytePictures(List<byte[]> bytePictures) {
        this.bytePictures = bytePictures;
    }

    public Price getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public Price getNormalPrice() {
        return normalPrice;
    }

    public Bitmap getMainPicture() {
        if(pictures.isEmpty()) {
            return null;
        }
        return pictures.get(0);
    }

    public List<Bitmap> getPictures() {
        return pictures;
    }

    public Type getTypeOfProduct() {
        return typeOfProduct;
    }

    public Category getCategory() {
        return category;
    }

    public Collection getTypeOfCollection() {
        return typeOfCollection;
    }

    public long getNumberOfProduct() {
        return numberOfProduct;
    }

    public boolean onPromotion() {
        return !(normalPrice == price);
    }

    public List<Size> getListOfSizes() {
        return listOfSizes;
    }

    public void setSelectedSize(Size selectedSize) {
        this.selectedSize = selectedSize;
    }

    public String getSizes() {
        StringBuilder result = new StringBuilder();
        for (Size size : listOfSizes) {
            result.append(size.name());
            if(listOfSizes.indexOf(size) <= listOfSizes.size()-1) {
                result.append(", ");
            }
        }
        return result.toString();
    }

    public ProductDb transferToDBObject(){
        return ProductDb.newInstance(name,
                category,
                listOfSizes,
                numberOfProduct,
                price,
                normalPrice,
                typeOfProduct,
                typeOfCollection,
                pictures);
    }

    public Size getSelectedSize() {
        return selectedSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return numberOfProduct == product.numberOfProduct &&
                typeOfProduct == product.typeOfProduct &&
                category == product.category &&
                typeOfCollection == product.typeOfCollection &&
                Objects.equals(price, product.price) &&
                Objects.equals(name, product.name) &&
                Objects.equals(pictures, product.pictures) &&
                Objects.equals(bytePictures, product.bytePictures) &&
                Objects.equals(listOfSizes, product.listOfSizes) &&
                selectedSize == product.selectedSize &&
                Objects.equals(normalPrice, product.normalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                typeOfProduct, category, typeOfCollection,
                numberOfProduct, price, name, pictures,
                bytePictures, listOfSizes, selectedSize, normalPrice);
    }

    public Product copy() {
        return new Product(
                typeOfProduct,
                category,
                typeOfCollection,
                numberOfProduct,
                price,
                name,
                pictures,
                bytePictures,
                listOfSizes,
                selectedSize,
                normalPrice
        );
    }
}
