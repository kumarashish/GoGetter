package techmatics.gogetmydrink;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextClassification;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by admin on 13/09/18.
 */

public class ListAdapter extends BaseAdapter {
    ArrayList<Model> list;
    Activity act;
    LayoutInflater inflater=null;
    callback callback;
    public ListAdapter(ArrayList<Model> list,Activity act)
    {
        this.list=list;
        this.act=act;
        inflater=(LayoutInflater)
                act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        callback=(techmatics.gogetmydrink.callback)act;
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
ViewHolder holder = null;
        if(view==null) {
            holder=new ViewHolder();
            view = inflater.inflate(R.layout.list_row,viewGroup, false);
            holder.name=(TextView)view.findViewById(R.id.name);
        }else{
            holder=(ViewHolder)view.getTag();
        }

        holder.name.setText(list.get(i).getName());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onClickItem(list.get(i));
            }
        });
        view.setTag(holder);
        return view;
    }
    public class ViewHolder{
       TextView name;
    }
}
