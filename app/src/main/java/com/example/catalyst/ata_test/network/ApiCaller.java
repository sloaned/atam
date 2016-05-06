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
import com.example.catalyst.ata_test.events.InitialSearchEvent;
import com.example.catalyst.ata_test.events.TeamsEvent;
import com.example.catalyst.ata_test.events.ViewTeamEvent;
import com.example.catalyst.ata_test.fragments.DashboardFragment;
import com.example.catalyst.ata_test.models.Team;
import com.example.catalyst.ata_test.models.User;
import com.example.catalyst.ata_test.util.JsonConstants;

import org.greenrobot.eventbus.EventBus;
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
   // private static UpdateDashboardListener dashboardCallback;
   // private static UpdateSearchListener searchCallback;

    private ArrayList<User> teamMembers = new ArrayList<User>();

    private static SharedPreferences prefs;
    private SharedPreferences.Editor mEditor;
    /*
    public interface UpdateDashboardListener {
        void refreshTeams(ArrayList<Team> teams);
        void viewTeam(Team team);
        void getTeamMembers(Team team);
    }

    public interface UpdateSearchListener {
        void refreshUsers(ArrayList<User> users);
    }  */

    public ApiCaller(Context context, Fragment fragment) {
        mContext = context;
        mFragment = fragment;
        Log.d(TAG, "Calling fragment = " + fragment);

      //  prefs = PreferenceManager.getDefaultSharedPreferences(context);
      //  mEditor = prefs.edit();

       /* if (mFragment instanceof DashboardFragment) {

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
        } */


    }


    public void getAllUsers() {
        String url = BASE_URL + "users?size=3000";

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                ArrayList<User> users = new ArrayList<User>();
                try {
                    JSONObject embedded = response.getJSONObject(JsonConstants.JSON_EMBEDDED);
                    JSONArray userList = embedded.getJSONArray(JsonConstants.JSON_USERS);

                    Log.d(TAG, "userList length = " + userList.length());
                    for (int i = 0; i < userList.length(); i++) {
                        JSONObject jsonUser = userList.getJSONObject(i);
                        User user = new User();
                        user.setFirstName(jsonUser.getString(JsonConstants.JSON_USER_FIRST_NAME));
                        user.setLastName(jsonUser.getString(JsonConstants.JSON_USER_LAST_NAME));
                        user.setTitle(jsonUser.getString(JsonConstants.JSON_USER_TITLE));
                        user.setEmail(jsonUser.getString(JsonConstants.JSON_USER_EMAIL));
                        if (!jsonUser.getString(JsonConstants.JSON_USER_DESCRIPTION).equals("null")) {
                            user.setDescription(jsonUser.getString(JsonConstants.JSON_USER_DESCRIPTION));
                        }

                        Log.d(TAG, "user name = " + user.getFirstName());
                        users.add(user);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Error: " + e.getMessage());
                }
                /*
                if (searchCallback != null) {
                    searchCallback.refreshUsers(users);
                }  */
                EventBus.getDefault().post(new InitialSearchEvent(users));

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
        String url = BASE_URL + "teams?size=3000000";

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                ArrayList<Team> teams = new ArrayList<Team>();
                try {
                    JSONObject embedded = response.getJSONObject(JsonConstants.JSON_EMBEDDED);
                    JSONArray teamsList = embedded.getJSONArray(JsonConstants.JSON_TEAMS);

                    for (int i = 0; i < teamsList.length(); i++) {
                        JSONObject jsonTeam = teamsList.getJSONObject(i);
                        Team team = new Team();
                        team.setName(jsonTeam.getString(JsonConstants.JSON_TEAM_NAME));
                        team.setId(jsonTeam.getString(JsonConstants.JSON_TEAM_ID));
                        teams.add(team);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Error: " + e.getMessage());
                }

                EventBus.getDefault().post(new TeamsEvent(teams));
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
        String url = BASE_URL + "teams/" + id;

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                Team team = new Team();
                try {
                    team.setId(response.getString(JsonConstants.JSON_TEAM_ID));
                    team.setName(response.getString(JsonConstants.JSON_TEAM_NAME));
                    team.setDescription(response.getString(JsonConstants.JSON_TEAM_DESCRIPTION));
                    team.setActive(response.getBoolean(JsonConstants.JSON_TEAM_ACTIVE));
                    if (!response.getString(JsonConstants.JSON_TEAM_MEMBERLIST).equals("null")) {
                        JSONArray memberList = response.getJSONArray(JsonConstants.JSON_TEAM_MEMBERLIST);
                        ArrayList<User> members = new ArrayList<User>();
                        for (int i = 0; i < memberList.length(); i++) {
                            JSONObject member = memberList.getJSONObject(i);
                            String memberId = member.getString(JsonConstants.JSON_TEAM_MEMBER_ID);
                            User user = new User();
                            user.setId(memberId);
                            members.add(user);
                        }

                        team.setUserList(members);
                    }


                } catch (JSONException e) {
                    Log.e(TAG, "Error: " + e.getMessage());
                }

                EventBus.getDefault().post(new ViewTeamEvent(team));

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
        EventBus.getDefault().post(new ViewTeamEvent(team));
    }

    public void getUserById(String id) {
        String url = BASE_URL + "users/" + id + "/";

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                User user = new User();
                try {
                    user.setId(response.getString(JsonConstants.JSON_USER_ID));
                    user.setFirstName(response.getString(JsonConstants.JSON_USER_FIRST_NAME));
                    user.setLastName(response.getString(JsonConstants.JSON_USER_LAST_NAME));
                    user.setRole(response.getString(JsonConstants.JSON_USER_ROLE));
                    user.setTitle(response.getString(JsonConstants.JSON_USER_TITLE));
                    user.setEmail(response.getString(JsonConstants.JSON_USER_EMAIL));
                    user.setDescription(response.getString(JsonConstants.JSON_USER_DESCRIPTION));

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
