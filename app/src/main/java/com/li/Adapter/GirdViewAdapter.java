package com.li.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.li.games.R;

import java.util.List;

/**
 * Created by li on 16-1-11.
 */
public class GirdViewAdapter extends BaseAdapter{
    private List<Picture> pic;
    private LayoutInflater minflater;
    public GirdViewAdapter(Context context,List<Picture> data) {
        this.pic=data;
        minflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return pic.size();
    }

    @Override
    public Object getItem(int position) {
        return pic.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        Viewholder viewholder;
        if (convertView==null){
           // Log.d("lixuan",""+position);
        view=minflater.inflate(R.layout.girdview,null);
            viewholder=new Viewholder();
            viewholder.icon = (ImageView) view.findViewById(R.id.gd_image);
            viewholder.icon.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setTag(viewholder);
        }else {
            view=convertView;
            viewholder=(Viewholder)view.getTag();
        }
            viewholder.icon.setImageResource(pic.get(position).getPic());
        return view;
    }
    class Viewholder{
        public ImageView icon;
    }
}
