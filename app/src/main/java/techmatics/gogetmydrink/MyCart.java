package techmatics.gogetmydrink;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by admin on 12/09/18.
 */

public class MyCart extends Activity implements View.OnClickListener{
    ListView list;
    Button pay;
    ImageView back;
    AppController controller;
    TextView header,customername,customerMobile,totalAmount;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_cart);
        initializeAll();
    }
    public void initializeAll()
    {
        controller=(AppController)getApplicationContext();
        back=(ImageView)findViewById(R.id.back);
        header=(TextView)findViewById(R.id.header);
        customername=(TextView)findViewById(R.id.customerName);
        customerMobile=(TextView)findViewById(R.id.customermobile);
        totalAmount=(TextView)findViewById(R.id.totalamount);
        list=(ListView)findViewById(R.id.listView);
        pay=(Button)findViewById(R.id.pay);
        pay.setOnClickListener(this);
        back.setOnClickListener(this);
        header.setText("My Cart ("+controller.getQuantity()+")");
        totalAmount.setText("$ "+controller.getTotalPrice());
        list.setAdapter(new CartAdapter(MyCart.this,controller.getMyCart()));
        customerMobile.setText(controller.getCustomerMobile());
        customername.setText(controller.getCustomerName());

    }

    @Override
    public void onClick(View view) {
      switch (view.getId())
      {
          case R.id.back:
              onBackPressed();
              break;
          case R.id.pay:
              controller.clearCart();
              Intent in=new Intent(MyCart.this,Dashboard.class);
              in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              startActivity(in);
              Toast.makeText(MyCart.this,"Your Order has been placed sucessfully.",Toast.LENGTH_SHORT).show();
              break;
      }
    }
}
