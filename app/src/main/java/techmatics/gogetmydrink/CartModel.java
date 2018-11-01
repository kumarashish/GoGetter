package techmatics.gogetmydrink;

/**
 * Created by admin on 13/09/18.
 */

public class CartModel {
    String name;
    int price;
    int quantity;


    CartModel(String name,int price,int quantity)
    {
        this.name=name;
        this.price=price;
        this.quantity=quantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
