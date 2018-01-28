package rongwolrd.listdemo.listdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageLoader {

    private ImageView imageView;
    private String url;


    public void loadImage() {

        LoadImageAsyncTask loadImageAsyncTask = new LoadImageAsyncTask(imageView, url);
        loadImageAsyncTask.execute(url);
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

    public ImageLoader(ImageView imageView, String url) {
        this.imageView = imageView;
        this.url = url;


        //    this.lruCache = MainActivity.lruCache;
    }

}

class LoadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;
    private String url;

    public LoadImageAsyncTask(ImageView imageView, String url) {
        this.imageView = imageView;
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
        //if (imageView != null && bitmap != null) {
        //}

        if (imageView.getTag().equals(url)) {
            imageView.setImageBitmap(bitmap);
        }
    }

    private Bitmap getBitmapFromUrl(String urlString) {
        Bitmap bitmap;
        InputStream is = null;
        try {
            Log.i("url",urlString);
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
