package com.codepath.apps.squawker.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kpu on 2/20/16.
 */
public class User {
    private long uId;
    private String fullName;
    private String screenName;
    private String profileImageUrl;

    public static User fromJSON(JSONObject jsonObject) {
        User user = new User();

        try {
            user.uId = jsonObject.getLong("id");
            user.fullName = jsonObject.getString("name");
            user.screenName = jsonObject.getString("screen_name");
            user.profileImageUrl = jsonObject.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }

    public long getuId() {
        return uId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
