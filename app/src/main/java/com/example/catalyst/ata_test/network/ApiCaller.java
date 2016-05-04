package com.example.catalyst.ata_test.network;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.catalyst.ata_test.AppController;
import com.example.catalyst.ata_test.activities.SearchActivity;
import com.example.catalyst.ata_test.fragments.DashboardFragment;
import com.example.catalyst.ata_test.models.Team;
import com.example.catalyst.ata_test.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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


    private ArrayList<User> teamMembers = new ArrayList<User>();

    private static SharedPreferences prefs;

    private SharedPreferences.Editor mEditor;

    public interface UpdateDashboardListener {
        void refreshTeams(ArrayList<Team> teams);
        void viewTeam(Team team);
        void getTeamMembers(Team team);
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
                        team.setId(jsonTeam.getString("id"));
                        teams.add(team);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Error: " + e.getMessage());
                }

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

    public void getTeamById(String id) {
        String url = BASE_URL + "teams/" + id + "/";

        Log.d(TAG, "team url = " + url);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                Team team = new Team();
                try {
                    team.setId(response.getString("id"));
                    team.setName(response.getString("name"));
                    team.setDescription(response.getString("description"));
                    team.setActive(response.getBoolean("active"));
                    JSONArray memberList = response.getJSONArray("userList");
                    ArrayList<User> members = new ArrayList<User>();
                    for (int i = 0; i < memberList.length(); i++) {
                        JSONObject member = memberList.getJSONObject(i);
                        String memberId = member.getString("userId");
                        User user = new User();
                        user.setId(memberId);
                        members.add(user);
                       // getUserById(memberId);
                    }

                    team.setUserList(members);

                } catch (JSONException e) {
                    Log.e(TAG, "Error: " + e.getMessage());
                }

                if (mFragment instanceof DashboardFragment) {
                    dashboardCallback.getTeamMembers(team);
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

    public void getTeamMembers(Team team) {
        teamMembers.clear();
        for (User user : team.getUserList()) {
            getUserById(user.getId());
        }
        team.setUserList(teamMembers);
        dashboardCallback.viewTeam(team);
    }

    public void getUserById(String id) {
        String url = BASE_URL + "users/" + id + "/";

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                User user = new User();
                try {
                    user.setId(response.getString("id"));
                    user.setFirstName(response.getString("firstName"));
                    user.setLastName(response.getString("lastName"));
                    user.setRole(response.getString("role"));
                    user.setTitle(response.getString("title"));
                    user.setEmail(response.getString("email"));
                    user.setDescription(response.getString("profileDescription"));

                    teamMembers.add(user);
                } catch (JSONException e) {
                    Log.e(TAG, "Error: " + e.getMessage());
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
}
