package techmatics.gogetmydrink;

/**
 * Created by admin on 13/09/18.
 */

public class Model {
    String name;
    int price;


    public Model(String name,int price)
    {
        this.name=name;
        this.price=price;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
