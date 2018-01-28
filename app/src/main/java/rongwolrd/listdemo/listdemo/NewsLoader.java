package rongwolrd.listdemo.listdemo;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsLoader extends AsyncTask<String, Void, List<NewsBean>> {

    private ListView listView;
    private Context context;

    public NewsLoader(ListView listView, Context context) {
        this.context = context;
        this.listView = listView;
    }

    @Override
    protected void onPostExecute(List<NewsBean> newsBeans) {
        super.onPostExecute(newsBeans);
        NewsAdapter newsAdapter = new NewsAdapter(context, newsBeans,listView);
        listView.setAdapter(newsAdapter);
    }

    @Override
    protected List<NewsBean> doInBackground(String... strings) {
        String newsUrl = strings[0];
        return getNewsList(newsUrl);
    }

    private List<NewsBean> getNewsList(String newsUrl) {
        List<NewsBean> newsBeanList = new ArrayList<>();
        try {
            URL url = new URL(newsUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream is = httpURLConnection.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int n = 0;
            byte[] b = new byte[1024];
            while ((n = is.read(b)) != -1) {
                outputStream.write(b, 0, n);
            }
            String response = new String(outputStream.toByteArray());
            httpURLConnection.disconnect();
            is.close();
            outputStream.close();
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = (JSONArray) jsonObject.get("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                NewsBean newsBean = new NewsBean();
                newsBean.setImg(jsonObject1.getString("picSmall"));
                newsBean.setTitle(jsonObject1.getString("name"));
                newsBean.setContent(jsonObject1.getString("description"));
                newsBeanList.add(newsBean);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsBeanList;
    }

}
