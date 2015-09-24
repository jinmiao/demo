package com.appcutt.demo.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.appcutt.demo.R;
import com.appcutt.demo.image.loader.ImageLoaderUtil;

import java.util.List;

/**
 * Created by ouyangjinmiao on 18/5/15.
 */
public class HomeAdapter extends BaseAdapter {

    private static List<String> mDatas;

    private LayoutInflater mInflater;

    private Context mContext;

    public HomeAdapter(Context context, List<String> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }

    public void addDatas (List<String> datas) {

        if (null != datas) {

            if (null != mDatas && mDatas.size() > 1) {
                mDatas.addAll(datas);
                notifyDataSetChanged();
            } else {
                mDatas = datas;
            }
        }

    }

    public void clearData() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas == null ? null : mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHoler viewHoler;

        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.demo_home_adapter_item, null);

            viewHoler = new ViewHoler();

            viewHoler.itemImageview = (ImageView) convertView.findViewById(R.id.item_image);
            viewHoler.title = (TextView) convertView.findViewById(R.id.title);

            convertView.setTag(viewHoler);
        } else {
            viewHoler = (ViewHoler) convertView.getTag();
        }

        String title = mDatas.get(position);

        String imgUrl = "http://img4.duitang.com/uploads/item/201407/24/20140724150834_KLywV.thumb.700_0.jpeg";
        ImageLoaderUtil.getImageLoader().displayImage(imgUrl, viewHoler.itemImageview);
        viewHoler.title.setText(title);

        return convertView;
    }

    private static class ViewHoler {

        ImageView itemImageview;

        TextView title;

    }
}
