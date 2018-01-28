package rongwolrd.listdemo.listdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class ImageLoader {

    private ListView listView;

    private Set<LoadImageAsyncTask> loadImageAsyncTasks;
    private static LruCache<String, Bitmap> lruCache;

    static {

        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSizes = maxMemory / 4;
        lruCache = new LruCache<String, Bitmap>(cacheSizes) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };


    }



    public void loadImage(int start, int end) {
        for (int i = start; i < end; i++) {
            String url = NewsAdapter.URLS[i];
            Bitmap bitmap = lruCache.get(url);
            if(bitmap != null) {
                Log.i("url",String.valueOf(url));
                Log.i("bm",String.valueOf(bitmap.getByteCount()));
                ImageView imageView = listView.findViewWithTag(url);
                imageView.setImageBitmap(bitmap);
                return;
            }

            LoadImageAsyncTask loadImageAsyncTask = new LoadImageAsyncTask(listView, url);
            loadImageAsyncTask.execute(url);
            loadImageAsyncTasks.add(loadImageAsyncTask);
        }
    }


    public void cancelLoad() {
        if (loadImageAsyncTasks != null && loadImageAsyncTasks.size() != 0) {
            for (LoadImageAsyncTask loadImageAsyncTask : loadImageAsyncTasks) {
                loadImageAsyncTask.cancel(false);
            }
        }
    }


















/*


    public void showImage(ImageView imageView, String url) {
        int i = lruCache.putCount();

        this.url = url;
        this.imageView = imageView;
        if (lruCache.get(url) == null) {
            new LoadImageAsyncTask().execute(url);
        } else {
            if (imageView.getTag().equals(url)) {
                imageView.setImageBitmap(lruCache.get(url));
            }
        }
    }

*/

    public ImageLoader(ListView listView) {
        loadImageAsyncTasks = new HashSet<>();
        this.listView = listView;
    //    this.lruCache = MainActivity.lruCache;
    }


    class LoadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        private ListView listView;

        private String url;

        public LoadImageAsyncTask(ListView listView, String url) {
            this.listView = listView;
            this.url = url;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            return getBitmapFromUrl(url);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            lruCache.put(url, bitmap);
            ImageView imageView = listView.findViewWithTag(url);
            //if (imageView != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            //}
        }

        private Bitmap getBitmapFromUrl(String urlString) {
            Bitmap bitmap;
            InputStream is = null;
            try {
                URL mUrl = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) mUrl.openConnection();
                is = new BufferedInputStream(connection.getInputStream());
                bitmap = BitmapFactory.decodeStream(is);
                connection.disconnect();
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }


}
