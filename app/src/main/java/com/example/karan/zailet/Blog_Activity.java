package com.example.karan.zailet;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

/**
 * Created by Karan on 08-06-2017.
 */

public class Blog_Activity extends AppCompatActivity {
    private String TAG = Blog_Activity.class.getSimpleName();

    private ProgressDialog pDialog;
    private RecyclerView rv;
    ArrayList<Blog> blogList;
    private RecyclerView.LayoutManager layoutManager;

    // URL to get contacts JSON
    private static String url = "http://zailet.com/zailet_internship_assignment/get_data.php?topics_info=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogs);
        setTitle("Blog");
        
        rv = (RecyclerView) findViewById(R.id.blogcardview);
        rv.setHasFixedSize(true);       
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rv.setLayoutManager(layoutManager);
        Getblogs gt = new Getblogs();
        gt.execute();
        showData();
    }

    public void showData() {
        rv.setAdapter(new BlogList_Adapter(Blog_Activity.this, blogList));
//        for(int i=0; i<blogList.size(); i++) {
//            if (i % 3 == 0)
//                trendingblogList.add(blogList.get(i));
//        }
//        rv1.setAdapter(new BlogList_Adapter(this,trendingblogList));

    }

    private class Getblogs extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Blog_Activity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();


            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray results = jsonObj.getJSONArray("posts");

                    // looping through All blogs
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject c = results.getJSONObject(i);

                        JSONObject info = c.getJSONObject("post_info");

                        String title = info.getString("title");
                        String description = info.getString("description");
                        String thumbnail = info.getString("thumbnail");
//                        String time = info.getString("time");
                        Blog blog = new Blog(title, description, thumbnail);
//                        blog.setId(id);
//                        blog.setInterest(interest);
//                        blog.setCover(cover);
//                        blog.setTime(time);

                        // adding blog to blog list
                        blogList.add(blog);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            showData();
        }
    }

}
