package com.codepath.apps.squawker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.codepath.apps.squawker.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TimelineActivity extends AppCompatActivity {

    private SquawkerClient client;
    private TweetsArrayAdapter tweetsArrayAdapter;

    @Bind(R.id.lvTweets)
    ListView lvTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);

        tweetsArrayAdapter = new TweetsArrayAdapter(this, new ArrayList<Tweet>());
        lvTweets.setAdapter(tweetsArrayAdapter);

        client = SquawkerApplication.getRestClient();
        populateTimeline();
    }

    private void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<Tweet> tweets = Tweet.fromJSONArray(response);
                Log.d("DEBUG", tweets.toString());
                tweetsArrayAdapter.addAll(tweets);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }
}
