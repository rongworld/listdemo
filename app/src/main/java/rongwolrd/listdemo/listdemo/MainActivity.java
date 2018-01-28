package rongwolrd.listdemo.listdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;
import android.widget.ListView;
import rongwolrd.listdemo.listdemo.R;

public class MainActivity extends Activity{
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list);
        new NewsLoader(listView,MainActivity.this).execute("http://www.imooc.com/api/teacher?type=4&num=30");
    }

}
