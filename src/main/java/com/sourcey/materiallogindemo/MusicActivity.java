package com.sourcey.materiallogindemo;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class MusicActivity extends Fragment {

    private static final String TAG = MainActivity.class.getSimpleName();
    private List<MusicBean> musicBeanList;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayout;
    private MusicAdapter adapter ;





    private void getMoviesFromDB(int id) {

        AsyncTask<Integer, Void, Void> asyncTask = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... movieIds) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(" the service path here" + movieIds[0])
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    //musicBeanList.clear();
                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        MusicBean musicBean = new MusicBean(object.getInt("id"), object.getString("music_name"),
                                object.getString("music_image"), object.getString("music_genre"),object.getString("mp3"));

                        musicBeanList.add(musicBean);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        };

        asyncTask.execute(id);
    }
}