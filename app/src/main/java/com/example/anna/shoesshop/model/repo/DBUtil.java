package com.example.anna.shoesshop.model.repo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.anna.shoesshop.model.database.AccountDb;
import com.example.anna.shoesshop.model.database.ClientDb;
import com.example.anna.shoesshop.model.database.DeliveryDb;
import com.example.anna.shoesshop.model.database.OrderDb;
import com.example.anna.shoesshop.model.database.ProductDb;
import com.example.anna.shoesshop.model.database.enums.CategoryDb;
import com.example.anna.shoesshop.model.database.enums.CollectionDb;
import com.example.anna.shoesshop.model.database.enums.GenderDb;
import com.example.anna.shoesshop.model.database.enums.SizeDb;
import com.example.anna.shoesshop.model.database.enums.StatusDb;
import com.example.anna.shoesshop.model.database.enums.TypeDb;
import com.example.anna.shoesshop.model.database.enums.TypeOfDeliveryDb;
import com.example.anna.shoesshop.model.order.Delivery;
import com.example.anna.shoesshop.model.order.Order;
import com.example.anna.shoesshop.model.order.Status;
import com.example.anna.shoesshop.model.order.TypeOfDelivery;
import com.example.anna.shoesshop.model.product.Category;
import com.example.anna.shoesshop.model.product.Collection;
import com.example.anna.shoesshop.model.product.Product;
import com.example.anna.shoesshop.model.product.Size;
import com.example.anna.shoesshop.model.product.Type;
import com.example.anna.shoesshop.model.userInfo.Account;
import com.example.anna.shoesshop.model.userInfo.Client;
import com.example.anna.shoesshop.model.userInfo.Gender;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public class DBUtil {

    public static TypeOfDeliveryDb transferToEnum(TypeOfDelivery type){
        return new TypeOfDeliveryDb(type.toString());
    }

    public static StatusDb transferToEnum(Status status){
        return new StatusDb(status.toString());
    }

    public static GenderDb transferToEnum(Gender sex){
        return new GenderDb(sex.toString());
    }

    public static Gender transferToEnum(GenderDb sex){
        return Gender.valueOf(sex.toString());
    }

    public static OrderDb transferToEnum(Order order){
        return order.transfer();
    }

    public static Order transferToEnum(OrderDb order){
        return new Order().transfer(order);
    }

    public static Client transferToEnum(ClientDb clientDb) {
        return new Client(clientDb);
    }

    public static ClientDb transferToEnum(Client clientDb) {
        return clientDb.transferToDb();
    }

    public static Account transferToEnum(AccountDb accountDb) {
        return new Account(accountDb);
    }

    public static Delivery transferToEnum(DeliveryDb deliveryInformation) {
        return new Delivery(deliveryInformation);
    }

    public static DeliveryDb transferToEnum(Delivery deliveryInformation) {
        return new DeliveryDb(deliveryInformation);
    }

    public static ProductDb transferToEnum(Product product){
        return product.transferToDBObject();
    }

    public static Product transferToEnum(ProductDb product){
        Product result = new Product(product.getTypeOfProduct(),
                product.getCategory(),
                product.getTypeOfCollection(),
                product.getNumberOfProduct(),
                product.getPrice(),
                product.getName(),
                DBUtil.transferToEnumListOfBitmap(product.getPictures()),
                DBUtil.transferToEnumList(product.getListOfSizes()),
                product.getSelectedSize(),
                product.getNormalPrice() );
        result.setBytePictures(product.getPictures());
        return result;
    }

    private static List<Bitmap> transferToEnumListOfBitmap(RealmList<byte[]> pictures) {
        List<Bitmap> list = new ArrayList<>();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        for (byte[] p : pictures) {
            list.add(BitmapFactory.decodeByteArray(p, 0, p.length, options));
        }
        return list;
    }

    public static Bitmap transferToBigBitmap(byte[] picture) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        return BitmapFactory.decodeByteArray(picture, 0, picture.length, options);
    }

    private static List<Size> transferToEnumList(RealmList<SizeDb> listOfSizes) {
        List<Size> res = new ArrayList<>();
        for (SizeDb l : listOfSizes) {
            res.add(DBUtil.transferToEnum(l));
        }
        return res;
    }

    public static CollectionDb transferToEnum(Collection typeOfCollection) {
        return new CollectionDb(typeOfCollection.toString());
    }

    public static Category transferToEnum(CategoryDb categoryDb) {
        if (categoryDb == null) {
            return Category.shoes;
        }
        return (categoryDb.toString().equals("shoes"))? Category.shoes :
                (categoryDb.toString().equals("clothes")? Category.clothes : Category.accessories);
    }

    public static Type transferToEnum(TypeDb typeDb) {
        if(typeDb == null) {
            return Type.women;
        }
        return (typeDb.toString().equals("women"))? Type.women :
                (typeDb.toString().equals("men")? Type.men : Type.kid);
    }

    public static Size transferToEnum(SizeDb selectedSize) {
        switch (selectedSize.toString()){
            case ("36"): return Size.woman36;
            case ("37"): return Size.woman37;
            case ("38"): return Size.woman38;
            case ("39"): return Size.woman39;
            case ("40"): return Size.woman40;
            case ("41"): return Size.woman41;
            case ("42"): return Size.woman42;
            case ("43"): return Size.man43;
            case ("44"): return Size.man44;
            case ("45"): return Size.man45;
            case ("46"): return Size.man46;
            case ("30"): return Size.kid30;
            case ("31"): return Size.kid31;
            case ("32"): return Size.kid32;
            case ("33"): return Size.kid33;
            case ("34"): return Size.kid34;
            case ("35"): return Size.kid35;

        }
        return Size.universal;
    }

    public static String collectionToString(Collection collection) {
        String result = "Kolekcja ";
        if (collection == Collection.summer) {
            return result + "letnia";
        }
        if (collection == Collection.winter) {
            return result + "zimowa";
        }
        if (collection == Collection.spring) {
            return result + "wiosenna";
        }
        if (collection == Collection.autumn) {
            return result + "jesienna";
        }
        return result + "ogólna";
    }

    public static Collection transferToEnum(CollectionDb typeOfCollection) {
        if(typeOfCollection == null) {
            return Collection.spring;
        }
        switch (typeOfCollection.toString()) {
            case ("summer"): return Collection.summer;
            case ("autumn"): return Collection.autumn;
            case ("winter"): return Collection.winter;
            case ("spring"): return Collection.spring;
            default: return Collection.spring;
        }
    }

    public static List<OrderDb> transferFromEnumOrderList(List<Order> list){
        ArrayList<OrderDb> result = new ArrayList<>();
        for (Order o : list) {
            result.add(DBUtil.transferToEnum(o));
        }
        return result;
    }

    public static RealmList<ProductDb> transferFromEnumProductList(List<Product> list){
        RealmList<ProductDb> result = new RealmList<>();
        for (Product o : list) {
            result.add(DBUtil.transferToEnum(o));
        }
        return result;
    }

    public static List<Product> transferToProductList(List<ProductDb> list){
        ArrayList<Product> result = new ArrayList<>();
        if(list != null) {
            for (ProductDb o : list) {
                result.add(DBUtil.transferToEnum(o));
            }
        }
        return result;
    }

    public static List<Order> transferToOrderList(List<OrderDb> orders) {
        ArrayList<Order> result = new ArrayList<>();
        for (OrderDb o : orders) {
            result.add(DBUtil.transferToEnum(o));
        }
        return result;
    }

    public static RealmList<byte[]> transferToByteArray(List<Bitmap> bitmaps) {
        RealmList<byte[]> result = new RealmList<>();
        for (Bitmap b : bitmaps) {
            result.add(bitmapToByte(b));
        }
        return result;
    }

    private static byte[] bitmapToByte(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bitmap.recycle();
        return byteArray;
    }

    public static RealmList<SizeDb> transferToSizesList(List<Size> sizes) {
        RealmList<SizeDb> result = new RealmList<>();
        for (Size s : sizes) {
            result.add(new SizeDb(s));
        }
        return result;
    }
}
