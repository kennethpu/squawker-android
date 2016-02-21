package com.codepath.apps.squawker;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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

    @Bind(R.id.lvTweets)
    ListView lvTweets;

    @Bind(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    private SquawkerClient client;
    private TweetsArrayAdapter tweetsArrayAdapter;

    private long maxId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);

        // Set up pull-to-refresh
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                maxId = 0;
                tweetsArrayAdapter.clear();
                populateTimeline();
            }
        });

        // Create adapter and link it to the list view
        tweetsArrayAdapter = new TweetsArrayAdapter(this, new ArrayList<Tweet>());
        lvTweets.setAdapter(tweetsArrayAdapter);

        // Set up infinite scrolling
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
//                populateTimeline();
            }
        });

        // Initialize our REST client
        client = SquawkerApplication.getRestClient();

        // Refresh UI
        maxId = 0;
        populateTimeline();
    }

    private void populateTimeline() {
        client.getHomeTimeline(maxId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<Tweet> tweets = Tweet.fromJSONArray(response);
                tweetsArrayAdapter.addAll(tweets);
                maxId = tweets.get(tweets.size()-1).getuId();

                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());

                swipeContainer.setRefreshing(false);
            }
        });
    }
}
