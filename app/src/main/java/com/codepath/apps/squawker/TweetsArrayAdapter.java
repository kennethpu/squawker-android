package com.codepath.apps.squawker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.squawker.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by kpu on 2/20/16.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    static class ViewHolder {
        @Bind(R.id.ivProfileImage)
        ImageView ivProfileImage;

        @Bind(R.id.tvFullName)
        TextView tvFullName;

        @Bind(R.id.tvScreenName)
        TextView tvScreenName;

        @Bind(R.id.tvBody)
        TextView tvBody;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        Tweet tweet = getItem(position);

        viewHolder.ivProfileImage.setImageResource(0);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).transform(new RoundedCornersTransformation(5, 1)).into(viewHolder.ivProfileImage);
        viewHolder.tvFullName.setText(tweet.getUser().getFullName());
        viewHolder.tvScreenName.setText("@" + tweet.getUser().getScreenName());
        viewHolder.tvBody.setText(tweet.getBody());

        return convertView;
    }
}
