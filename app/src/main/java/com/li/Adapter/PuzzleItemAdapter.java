package com.li.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.li.Tools.GamesUtil;
import com.li.games.R;

import java.util.List;

/**
 * Created by li on 16-1-12.
 */
public class PuzzleItemAdapter extends BaseAdapter {
    private List<Bitmap> mDataBitmap;
    private LayoutInflater inflate;
    private Context mContext;

    public PuzzleItemAdapter() {
    }

    public PuzzleItemAdapter(Context context,List<Bitmap> mData){
        inflate=LayoutInflater.from(context);
        mContext=context;
        mDataBitmap=mData;
    }

    @Override
    public int getCount() {
        return mDataBitmap.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataBitmap.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView iv_pic_item = null;
        if (convertView == null) {
            iv_pic_item = new ImageView(mContext);
            // 设置布局 图片
            iv_pic_item.setLayoutParams(new GridView.LayoutParams(
                    mDataBitmap.get(position).getWidth(),
                    mDataBitmap.get(position).getHeight()));
            // 设置显示比例类型
            iv_pic_item.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            iv_pic_item = (ImageView) convertView;
        }
        iv_pic_item.setImageBitmap(mDataBitmap.get(position));
        return iv_pic_item;

  /*      View view;
        Viewholder viewholder;
        if (convertView==null){
            // Log.d("lixuan",""+position);
            view=inflate.inflate(R.layout.puzzle_adapter,null);
            viewholder=new Viewholder();
            viewholder.icon = (ImageView) view.findViewById(R.id.p_iv_item);
            viewholder.icon.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setTag(viewholder);
        }else {
            view=convertView;
            viewholder=(Viewholder)view.getTag();
        }
        viewholder.icon.setImageBitmap(mDataBitmap.get(position));
        return view;
    }
    class Viewholder{
        public ImageView icon;
    }*/
    }

}
