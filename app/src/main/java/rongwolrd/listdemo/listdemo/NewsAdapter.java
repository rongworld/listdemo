package rongwolrd.listdemo.listdemo;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.List;


public class NewsAdapter extends BaseAdapter {


    private List<NewsBean> newsBeanList;
    private LayoutInflater layoutInflater;
    NewsAdapter(Context context, List<NewsBean> newsBeanList, ListView listView) {
        this.newsBeanList = newsBeanList;
        layoutInflater = LayoutInflater.from(context);
    }





    @Override
    public int getCount() {
        return newsBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return newsBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.view_list_item,null);
            viewHolder.img = view.findViewById(R.id.item_image);
            viewHolder.title = view.findViewById(R.id.item_title);
            viewHolder.content = view.findViewById(R.id.item_content);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.img.setImageResource(R.drawable.hua);
        //设置tag
        viewHolder.img.setTag(newsBeanList.get(i).getImg());
        viewHolder.title.setText(newsBeanList.get(i).getTitle());
        viewHolder.content.setText(newsBeanList.get(i).getContent());
        ImageLoader imageLoader = new ImageLoader(viewHolder.img,newsBeanList.get(i).getImg());
        imageLoader.loadImage();
        return view;
    }


    private class ViewHolder {
        private ImageView img;
        private TextView title, content;
    }
}
