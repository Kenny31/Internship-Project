package com.example.karan.zailet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Topics_Activity extends AppCompatActivity {
    private String TAG = Topics_Activity.class.getSimpleName();

    TopicsList_Adapter mAdapter;
    private ArrayList<Integer> mTopicsSelected = new ArrayList<Integer>();

    private ProgressDialog pDialog;
    private RecyclerView rv,rv1;
    ArrayList<Topic> topicList;
    ArrayList<Topic> trendingtopicList;
    private RecyclerView.LayoutManager layoutManager,layoutManager1;

    // URL to get contacts JSON
    private static String url = "http://zailet.com/zailet_internship_assignment/get_data.php?get_topics=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);
        setTitle("Select Topics");

        topicList = new ArrayList<>();
        trendingtopicList = new ArrayList<>();
        rv = (RecyclerView) findViewById(R.id.cardview);
        rv1= (RecyclerView) findViewById(R.id.cardviewtrending);
        rv.setHasFixedSize(true);
        rv1.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        layoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rv.setLayoutManager(layoutManager);
        rv1.setLayoutManager(layoutManager1);
        GetTopics gt = new GetTopics();
        gt.execute();
        showData();

    }



    public void showData() {
        rv.setAdapter(new TopicsList_Adapter(Topics_Activity.this,topicList));
    }

    private class GetTopics extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Topics_Activity.this);
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
                    JSONArray results = jsonObj.getJSONArray("result");

                    // looping through All topics
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject c = results.getJSONObject(i);

                        String id = c.getString("id");
                        String interest = c.getString("interest");
                        String cover = c.getString("cover");
                        String time = c.getString("time");
                        Topic topic = new Topic();
                        topic.setId(id);
                        topic.setInterest(interest);
                        topic.setCover(cover);
                        topic.setTime(time);

                        // adding topic to topic list
                        topicList.add(topic);
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

//    Intent intent = new Intent(Topics_Activity.this, Blog_Activity.class);
//    startActivity(intent);
}