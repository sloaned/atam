package com.example.catalyst.ata_test.network;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.catalyst.ata_test.AppController;
import com.example.catalyst.ata_test.activities.DashboardActivity;
import com.example.catalyst.ata_test.activities.SearchActivity;
import com.example.catalyst.ata_test.fragments.DashboardFragment;
import com.example.catalyst.ata_test.fragments.SearchFragment;
import com.example.catalyst.ata_test.models.Team;
import com.example.catalyst.ata_test.models.User;
import com.example.catalyst.ata_test.util.SharedPreferencesConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by dsloane on 5/2/2016.
 */
public class ApiCaller {

    public static final String TAG = ApiCaller.class.getSimpleName();

    private static final String BASE_URL = "http://pc30120.catalystsolves.com:8080/";


    private Context mContext;
    private Fragment mFragment;
    private static UpdateDashboardListener dashboardCallback;
    private static UpdateSearchListener searchCallback;
    //private static DashboardFragment mDashboardFragment;

    private static SharedPreferences prefs;

    private SharedPreferences.Editor mEditor;

    public interface UpdateDashboardListener {
        void refreshTeams(ArrayList<Team> teams);
    }

    public interface UpdateSearchListener {
        void refreshUsers(ArrayList<User> users);
    }

    public ApiCaller(Context context, Fragment fragment) {
        mContext = context;
        mFragment = fragment;
        Log.d(TAG, "Calling fragment = " + fragment);

      //  prefs = PreferenceManager.getDefaultSharedPreferences(context);
      //  mEditor = prefs.edit();

        if (mFragment instanceof DashboardFragment) {

            Log.d(TAG, "fragment an instance of DashboardFragment!");
            try {
                dashboardCallback = (UpdateDashboardListener) mFragment;
            } catch (ClassCastException e) {
                throw new ClassCastException("Dashboard fragment must implement UpdateDashboardListener");
            }

        } else if (mContext instanceof SearchActivity) {
            try {
                searchCallback = (UpdateSearchListener) mContext;
            } catch (ClassCastException e) {
                throw new ClassCastException("Search activity must implement UpdateSearchListener");
            }
        }


    }


    public void getAllUsers() {
        String url = BASE_URL + "users";

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                ArrayList<User> users = new ArrayList<User>();
                try {
                    JSONObject embedded = response.getJSONObject("_embedded");
                    JSONArray userList = embedded.getJSONArray("users");

                    Log.d(TAG, "userList length = " + userList.length());
                    for (int i = 0; i < userList.length(); i++) {
                        JSONObject jsonUser = userList.getJSONObject(i);
                        User user = new User();
                        user.setFirstName(jsonUser.getString("firstName"));
                        user.setLastName(jsonUser.getString("lastName"));
                        Log.d(TAG, "user name = " + user.getFirstName());
                        users.add(user);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Error: " + e.getMessage());
                }
                Log.d(TAG, "callback = " + searchCallback);

                if (searchCallback != null) {
                    searchCallback.refreshUsers(users);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(req);
    }

    public void getAllTeams() {
        String url = BASE_URL + "teams";

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                ArrayList<Team> teams = new ArrayList<Team>();
                try {
                    JSONObject embedded = response.getJSONObject("_embedded");
                    JSONArray teamsList = embedded.getJSONArray("teams");

                    Log.d(TAG, "teamsList length = " + teamsList.length());
                    for (int i = 0; i < teamsList.length(); i++) {
                        JSONObject jsonTeam = teamsList.getJSONObject(i);
                        Team team = new Team();
                        team.setName(jsonTeam.getString("name"));
                        Log.d(TAG, "team name = " + team.getName());
                        teams.add(team);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Error: " + e.getMessage());
                }
                Log.d(TAG, "callback = " + dashboardCallback);

                dashboardCallback.refreshTeams(teams);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(req);
    }
}
