package com.popland.pop.aboutjsoup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hai on 24/07/2016.
 */
public class CustomBaseAdapter extends BaseAdapter {
    Context myContext;
    int myLayout;
    List<RSSitems> myItems;
    LayoutInflater inflater = null;
    public CustomBaseAdapter(Context context, int layout,List<RSSitems> items){
        myContext = context;
        myLayout = layout;
        myItems = items;
        inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return myItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
class ViewHolder{
    ImageView IV;
    TextView TVtitle, TVdes, TVpubDate;
}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.cutom_layoutlv,null);
            viewHolder = new ViewHolder();
            viewHolder.IV = (ImageView)convertView.findViewById(R.id.IV);
            viewHolder.TVtitle = (TextView)convertView.findViewById(R.id.TVtitle);
            viewHolder.TVdes = (TextView)convertView.findViewById(R.id.TVdes);
            viewHolder.TVpubDate = (TextView)convertView.findViewById(R.id.TVpubDate);
            convertView.setTag(viewHolder);
        }
            viewHolder= (ViewHolder) convertView.getTag();
            viewHolder.TVtitle.setText(myItems.get(position).title);
            viewHolder.TVdes.setText(myItems.get(position).description);
            viewHolder.TVpubDate.setText(myItems.get(position).pubDate);

            try{
                Picasso.with(myContext).load(myItems.get(position).media_thumbnail).resize(100,100).centerCrop().into(viewHolder.IV);
            }catch(IllegalArgumentException e){
                viewHolder.IV.setLayoutParams(new LinearLayout.LayoutParams(0,0));
            }

        return convertView;
    }
}
