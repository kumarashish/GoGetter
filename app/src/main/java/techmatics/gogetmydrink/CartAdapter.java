package techmatics.gogetmydrink;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by admin on 13/09/18.
 */

public class CartAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<CartModel> myCart;
    LayoutInflater inflater=null;
    public CartAdapter(Activity activity, ArrayList<CartModel> myCart) {
        this.activity = activity;
        this.myCart = myCart;
        inflater=(LayoutInflater)
                activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return myCart.size();
    }

    @Override
    public Object getItem(int i) {
        return myCart.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        CartModel model=myCart.get(i);
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.mycart_row, viewGroup, false);
            holder.name=(TextView)view.findViewById(R.id.name);
            holder.price=(TextView)view.findViewById(R.id.price);
            holder.quantity=(TextView)view.findViewById(R.id.quantity);
            holder.totalAmount=(TextView)view.findViewById(R.id.totalamount);

        } else {
            holder = (ViewHolder) view.getTag();
        }
       holder.name.setText(model.getName());
        holder.price.setText("Price : "+model.getPrice());
        holder.quantity.setText("Quantity : "+model.getQuantity());
        int totalAmount=model.getPrice()*model.getQuantity();
        holder.totalAmount.setText("Amount : $ "+totalAmount);
        view.setTag(holder);
        return view;
    }

    public class ViewHolder{
        TextView name,price,quantity,totalAmount;

    }
}
