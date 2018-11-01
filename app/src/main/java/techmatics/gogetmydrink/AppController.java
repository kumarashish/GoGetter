package techmatics.gogetmydrink;

import android.app.Application;
import android.os.Environment;
import android.os.StrictMode;

import java.util.ArrayList;

/**
 * Created by admin on 13/09/18.
 */

public class AppController extends Application {
    ArrayList<CartModel> myCart;
    String CustomerName="";
    String customerMobile="";
    WebApiCall webApiCall;
    @Override
    public void onCreate() {
        super.onCreate();
        myCart=new ArrayList<>();
        webApiCall=new WebApiCall(getApplicationContext());
       Common.makeFolder(String.valueOf(Environment.getExternalStorageDirectory()), "/GoGetter");
        Common.sdCardPath = Environment.getExternalStorageDirectory() + "/GoGetter";
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

    }

    public WebApiCall getWebApiCall() {
        return webApiCall;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void addItemToCart(CartModel model) {
        if (myCart.size() == 0) {
            myCart.add(model);

        } else {
            boolean isItemFound=false;
            for (int i = 0; i < myCart.size(); i++) {
                if (myCart.get(i).getName().equalsIgnoreCase(model.getName())) {
                    int quantity = model.getQuantity() + myCart.get(i).getQuantity();
                    myCart.get(i).setQuantity(quantity);
                    isItemFound=true;
                    break;

                }
            }
            if(isItemFound==false)
            {
                myCart.add(model);
            }
        }
    }

    public int getTotalPrice() {
        int totalPrice = 0;

        for (int i = 0; i < myCart.size(); i++) {
            int price = myCart.get(i).getQuantity() * myCart.get(i).getPrice();

            totalPrice += price;
        }
        return totalPrice;
    }

    public int getQuantity() {
        int quantity = 0;
        for (int i = 0; i < myCart.size(); i++) {

            quantity += myCart.get(i).getQuantity();
        }
        return quantity;
    }

    public void clearCart()
    {
        myCart.clear();
    }

    public ArrayList<CartModel> getMyCart() {
        return myCart;
    }
}
