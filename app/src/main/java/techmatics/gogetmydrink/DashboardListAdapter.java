package techmatics.gogetmydrink;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by admin on 13/09/18.
 */

public class DashboardListAdapter extends BaseAdapter {
    AppController controller;
    ArrayList<Profile> list;
    Activity act;
    LayoutInflater inflater = null;

    public DashboardListAdapter(ArrayList<Profile> list, Activity act) {
        this.list = list;
        this.act = act;
        inflater = (LayoutInflater)
                act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        controller = (AppController) act.getApplicationContext();

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final Profile model = list.get(i);
        TextView name,contact,deliveryarea;
        RatingBar rating;
        ImageView image;
        ImageButton call;

            view = inflater.inflate(R.layout.dashboard_row, viewGroup, false);
           name = (TextView) view.findViewById(R.id.name);
           contact = (TextView) view.findViewById(R.id.contact);
            deliveryarea = (TextView) view.findViewById(R.id.delivery_area);
            image = (ImageView) view.findViewById(R.id.imageView);
           rating = (RatingBar) view.findViewById(R.id.rating);
           call=(ImageButton)view.findViewById(R.id.call);

       name.setText(model.FIRST_NAME+" "+model.getLAST_NAME());

        contact.setText(model.getMOBILE());
        deliveryarea.setText(model.getdeliveryArea());
        if(model.getIMAGE().length()>0) {
            Picasso.with(act).load(model.getIMAGE()).resize(130, 80).placeholder(R.drawable.placeholder).into(image);
        }else{
            Picasso.with(act).load("https://wingslax.com/wp-content/uploads/2017/12/no-image-available.png").resize(130, 80).placeholder(R.drawable.placeholder).into(image);


        }

       call.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if(model.getMOBILE().length()>0) {
                    controller.setCustomerMobile(model.getMOBILE());
                    controller.setCustomerName(model.getFIRST_NAME() + " " + model.getLAST_NAME());
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + model.getMOBILE()));
                    if (ActivityCompat.checkSelfPermission(act, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        Toast.makeText(act, "You have not given call pernission,Please enable call permission from settings", Toast.LENGTH_SHORT).show();

                        return;
                    }
                    act.startActivity(callIntent);
                }else {
                    Toast.makeText(act, "Contact number not available", Toast.LENGTH_SHORT).show();


                }
            }
        });


        return view;
    }

}
