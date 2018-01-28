package rongwolrd.listdemo.listdemo;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.List;


public class NewsAdapter extends BaseAdapter implements AbsListView.OnScrollListener{


    private int start,end;
    private List<NewsBean> newsBeanList;
    private LayoutInflater layoutInflater;
    public static String[] URLS;
    private ImageLoader imageLoader;
    public NewsAdapter(Context context, List<NewsBean> newsBeanList,ListView listView) {
        listView.setOnScrollListener(this);
        this.newsBeanList = newsBeanList;
         imageLoader = new ImageLoader(listView);
        layoutInflater = LayoutInflater.from(context);
        URLS = new String[newsBeanList.size()];
        for(int i = 0;i<newsBeanList.size();i++){
            URLS[i] = newsBeanList.get(i).getImg();
        }
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
        return view;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if(scrollState == SCROLL_STATE_IDLE){
            //加载可见项
            imageLoader.loadImage(start,end);
        }else{
            //停止任务
            imageLoader.cancelLoad();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        start = firstVisibleItem;
        end = firstVisibleItem+visibleItemCount;
    }



    class ViewHolder {
        private ImageView img;
        private TextView title, content;
    }
}
