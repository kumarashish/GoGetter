package techmatics.gogetmydrink;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v4.widget.TextViewCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by admin on 12/09/18.
 */

public class DrinkList extends Activity implements View.OnClickListener,callback{
    TextView beerTv,whishkyTv,rumTv,vodkaTv,ginTv,wineTv,name,price;
    EditText quantity;
    Button add;
    ArrayList<Model>beer=new ArrayList<>();
    ArrayList<Model>whisky=new ArrayList<>();
    ArrayList<Model>rum=new ArrayList<>();
    ArrayList<Model>vodka=new ArrayList<>();
    ArrayList<Model>gin=new ArrayList<>();
    ArrayList<Model>wine=new ArrayList<>();
    ListView listView;
    int selectedCategory=0;
    ImageView back;
    Model selectedmodel=null;
    AppController controller;
    TextView cardCount;
    View cartDetails;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chose_drink);
        initializeArray();
        initializeTextView();

    }
    public void initializeTextView()
    {controller=(AppController)getApplicationContext();
        beerTv=(TextView)findViewById(R.id.beer);
        whishkyTv=(TextView)findViewById(R.id.whisky);
        rumTv=(TextView)findViewById(R.id.rum);
        vodkaTv=(TextView)findViewById(R.id.vodka);
        ginTv=(TextView)findViewById(R.id.gin);
        wineTv=(TextView)findViewById(R.id.wine);
        back=(ImageView)findViewById(R.id.back);
        name=(TextView)findViewById(R.id.name);
       price=(TextView)findViewById(R.id.price);
        cardCount=(TextView)findViewById(R.id.cart_count);
       quantity=(EditText) findViewById(R.id.quantity);
        add=(Button)findViewById(R.id.add);
        listView=(ListView)findViewById(R.id.listView);
        cartDetails=(View)findViewById(R.id.cart_details);

        add.setOnClickListener(this);
        beerTv.setOnClickListener(this);
        whishkyTv.setOnClickListener(this);
        rumTv.setOnClickListener(this);
        vodkaTv.setOnClickListener(this);
        ginTv.setOnClickListener(this);
        wineTv.setOnClickListener(this);
        add.setOnClickListener(this);
        back.setOnClickListener(this);
        cartDetails.setOnClickListener(this);
        name.setText(beer.get(0).getName());
        price.setText("Price: $ "+beer.get(0).getPrice());
        quantity.setText("");
        selectedmodel=beer.get(0);
        handleTab();
    }
    public void initializeArray()
    {
        beer.add(new Model("Beer 1",10));
        beer.add(new Model("Beer 2",18));
        beer.add(new Model("Beer 3",22));
        beer.add(new Model("Beer 4",26));
        beer.add(new Model("Beer 5",24));
        beer.add(new Model("Beer 6",12));

        whisky.add(new Model("Whisky 1",11));
        whisky.add(new Model("Whisky 2",8));
        whisky.add(new Model("Whisky 3",78));
        whisky.add(new Model("Whisky 4",25));

        rum.add(new Model("Rum 1",33));
        rum.add(new Model("Rum 2",21));
        rum.add(new Model("Rum 3",87));
        rum.add(new Model("Rum 4",98));
        rum.add(new Model("Rum 5",45));
        rum.add(new Model("Rum 6",23));
        rum.add(new Model("Rum 7",67));
        rum.add(new Model("Rum 8",12));

        vodka.add(new Model("Vodka 1",12));
        vodka.add(new Model("Vodka 2",65));
        vodka.add(new Model("Vodka 3",21));
        vodka.add(new Model("Vodka 4",90));

        gin.add(new Model("Gin 1",77));
        gin.add(new Model("Gin 2",22));
        gin.add(new Model("Gin 3",14));
        gin.add(new Model("Gin 4",97));

        wine.add(new Model("Wine 1",32));
        wine.add(new Model("Wine 2",28));
        wine.add(new Model("Wine 3",23));
        wine.add(new Model("Wine 4",24));
        wine.add(new Model("Wine 5",22));
        wine.add(new Model("Wine 6",12));
        wine.add(new Model("Wine 7",11));
        wine.add(new Model("Wine 8",66));
    }

    public void handleTab()
    {
switch (selectedCategory)
{
    case 0:
        beerTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.yellow_rounded_corner_buton));
        beerTv.setTextColor(getResources().getColor(R.color.white));
        whishkyTv.setBackgroundColor(getResources().getColor(R.color.white));
        whishkyTv.setTextColor(getResources().getColor(R.color.black));
        rumTv.setBackgroundColor(getResources().getColor(R.color.white));
        rumTv.setTextColor(getResources().getColor(R.color.black));
        vodkaTv.setBackgroundColor(getResources().getColor(R.color.white));
        vodkaTv.setTextColor(getResources().getColor(R.color.black));
        ginTv.setBackgroundColor(getResources().getColor(R.color.white));
        ginTv.setTextColor(getResources().getColor(R.color.black));
        wineTv.setBackgroundColor(getResources().getColor(R.color.white));
        wineTv.setTextColor(getResources().getColor(R.color.black));
        listView.setAdapter(new ListAdapter(beer,DrinkList.this));


        break;
    case 1:
        whishkyTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.yellow_rounded_corner_buton));
        whishkyTv.setTextColor(getResources().getColor(R.color.white));

        beerTv.setBackgroundColor(getResources().getColor(R.color.white));
        beerTv.setTextColor(getResources().getColor(R.color.black));

        rumTv.setBackgroundColor(getResources().getColor(R.color.white));
        rumTv.setTextColor(getResources().getColor(R.color.black));
        vodkaTv.setBackgroundColor(getResources().getColor(R.color.white));
        vodkaTv.setTextColor(getResources().getColor(R.color.black));
        ginTv.setBackgroundColor(getResources().getColor(R.color.white));
        ginTv.setTextColor(getResources().getColor(R.color.black));
        wineTv.setBackgroundColor(getResources().getColor(R.color.white));
        wineTv.setTextColor(getResources().getColor(R.color.black));
        listView.setAdapter(new ListAdapter(whisky,DrinkList.this));
        break;
    case 2:
        rumTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.yellow_rounded_corner_buton));
        rumTv.setTextColor(getResources().getColor(R.color.white));

        beerTv.setBackgroundColor(getResources().getColor(R.color.white));
        beerTv.setTextColor(getResources().getColor(R.color.black));
        whishkyTv.setBackgroundColor(getResources().getColor(R.color.white));
        whishkyTv.setTextColor(getResources().getColor(R.color.black));

        vodkaTv.setBackgroundColor(getResources().getColor(R.color.white));
        vodkaTv.setTextColor(getResources().getColor(R.color.black));
        ginTv.setBackgroundColor(getResources().getColor(R.color.white));
        ginTv.setTextColor(getResources().getColor(R.color.black));
        wineTv.setBackgroundColor(getResources().getColor(R.color.white));
        wineTv.setTextColor(getResources().getColor(R.color.black));

        listView.setAdapter(new ListAdapter(rum,DrinkList.this));
        break;
    case 3:
        vodkaTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.yellow_rounded_corner_buton));
        vodkaTv.setTextColor(getResources().getColor(R.color.white));

        beerTv.setBackgroundColor(getResources().getColor(R.color.white));
        beerTv.setTextColor(getResources().getColor(R.color.black));
        whishkyTv.setBackgroundColor(getResources().getColor(R.color.white));
        whishkyTv.setTextColor(getResources().getColor(R.color.black));
        rumTv.setBackgroundColor(getResources().getColor(R.color.white));
        rumTv.setTextColor(getResources().getColor(R.color.black));

        ginTv.setBackgroundColor(getResources().getColor(R.color.white));
        ginTv.setTextColor(getResources().getColor(R.color.black));
        wineTv.setBackgroundColor(getResources().getColor(R.color.white));
        wineTv.setTextColor(getResources().getColor(R.color.black));
        listView.setAdapter(new ListAdapter(vodka,DrinkList.this));
        break;
    case 4:
        ginTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.yellow_rounded_corner_buton));
        ginTv.setTextColor(getResources().getColor(R.color.white));

        beerTv.setBackgroundColor(getResources().getColor(R.color.white));
        beerTv.setTextColor(getResources().getColor(R.color.black));
        whishkyTv.setBackgroundColor(getResources().getColor(R.color.white));
        whishkyTv.setTextColor(getResources().getColor(R.color.black));
        rumTv.setBackgroundColor(getResources().getColor(R.color.white));
        rumTv.setTextColor(getResources().getColor(R.color.black));
        vodkaTv.setBackgroundColor(getResources().getColor(R.color.white));
        vodkaTv.setTextColor(getResources().getColor(R.color.black));

        wineTv.setBackgroundColor(getResources().getColor(R.color.white));
        wineTv.setTextColor(getResources().getColor(R.color.black));
        listView.setAdapter(new ListAdapter(gin,DrinkList.this));
        break;
    case 5:
        wineTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.yellow_rounded_corner_buton));
        wineTv.setTextColor(getResources().getColor(R.color.white));

        beerTv.setBackgroundColor(getResources().getColor(R.color.white));
        beerTv.setTextColor(getResources().getColor(R.color.black));
        whishkyTv.setBackgroundColor(getResources().getColor(R.color.white));
        whishkyTv.setTextColor(getResources().getColor(R.color.black));
        rumTv.setBackgroundColor(getResources().getColor(R.color.white));
        rumTv.setTextColor(getResources().getColor(R.color.black));
        vodkaTv.setBackgroundColor(getResources().getColor(R.color.white));
        vodkaTv.setTextColor(getResources().getColor(R.color.black));
        ginTv.setBackgroundColor(getResources().getColor(R.color.white));
        ginTv.setTextColor(getResources().getColor(R.color.black));
        listView.setAdapter(new ListAdapter(wine,DrinkList.this));

        break;
}
    }


    @Override
    public void onClick(View view) {

            switch (view.getId()) {
                case R.id.back:
                    finish();
                    break;
                case R.id.beer:
                    selectedCategory = 0;
                    selectedmodel=beer.get(0);
                    price.setText(beer.get(0).getName());
                    name.setText(beer.get(0).getName());
                    quantity.setText("1");
                    handleTab();
                    break;
                case R.id.whisky:
                    selectedCategory = 1;
                    selectedmodel=whisky.get(0);
                    name.setText(whisky.get(0).getName());
                    price.setText("Price: $ "+whisky.get(0).getPrice());
                    quantity.setText("");
                    handleTab();
                    break;
                case R.id.rum:
                    selectedCategory = 2;
                    selectedmodel=rum.get(0);
                    name.setText(rum.get(0).getName());
                    price.setText("Price: $ "+rum.get(0).getPrice());
                    quantity.setText("");
                    handleTab();
                    break;
                case R.id.vodka:
                    selectedCategory = 3;
                    selectedmodel=vodka.get(0);
                    name.setText(vodka.get(0).getName());
                    price.setText("Price: $ "+vodka.get(0).getPrice());
                    quantity.setText("");
                    handleTab();
                    break;
                case R.id.gin:
                    selectedCategory = 4;
                    selectedmodel=gin.get(0);
                    name.setText(gin.get(0).getName());
                    price.setText("Price: $ "+gin.get(0).getPrice());
                    quantity.setText("");
                    handleTab();
                    break;
                case R.id.wine:
                    selectedCategory = 5;
                    selectedmodel=wine.get(0);
                    name.setText(wine.get(0).getName());
                    price.setText("Price: $ "+wine.get(0).getPrice());
                    quantity.setText("");
                    handleTab();
                    break;
                case R.id.add:
                    if(quantity.getText().length()>0)
                    { controller.addItemToCart(new CartModel(selectedmodel.getName(),selectedmodel.getPrice(),Integer.parseInt(quantity.getText().toString())));
                        cardCount.setText(Integer.toString(controller.getQuantity()));
                        cardCount.setVisibility(View.VISIBLE);
                        quantity.setText("");
                        Toast.makeText(DrinkList.this,"Item added to your cart",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(DrinkList.this,"Please enter quantity",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.cart_details:
                    if(controller.getQuantity()>0)
                    {
                        Intent in=new Intent(this,MyCart.class);
                        startActivity(in);
                    }else{
                        Toast.makeText(DrinkList.this,"You dont have any item in cart",Toast.LENGTH_SHORT).show();
                    }
                    break;

            }





    }

    @Override
    public void onClickItem(final Model model) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                selectedmodel=model;
                name.setText(model.getName());
                price.setText("Price: $ "+model.getPrice());
                quantity.setText("1");
            }
        });
    }
}
