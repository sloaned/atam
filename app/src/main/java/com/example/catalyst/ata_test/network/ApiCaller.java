package com.example.catalyst.ata_test.network;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.webkit.CookieManager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.catalyst.ata_test.AppController;
import com.example.catalyst.ata_test.activities.LoginActivity;
import com.example.catalyst.ata_test.activities.SearchActivity;
import com.example.catalyst.ata_test.events.GetKudosInfoEvent;
import com.example.catalyst.ata_test.events.InitialSearchEvent;
import com.example.catalyst.ata_test.events.TeamsEvent;
import com.example.catalyst.ata_test.events.UpdateKudosEvent;
import com.example.catalyst.ata_test.events.UpdateTeamMembersEvent;
import com.example.catalyst.ata_test.events.ViewTeamEvent;
import com.example.catalyst.ata_test.fragments.DashboardFragment;
import com.example.catalyst.ata_test.models.Kudo;
import com.example.catalyst.ata_test.models.Team;
import com.example.catalyst.ata_test.models.User;
import com.example.catalyst.ata_test.util.JsonConstants;
import com.example.catalyst.ata_test.util.NetworkConstants;

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

    private static final String DATA_URL = "http://pc30120.catalystsolves.com:8080/";   // change to your own computer name
    private static final String API_URL = NetworkConstants.ATA_BASE;

    private ArrayList<User> teamMembers = new ArrayList<User>();
    private ArrayList<Kudo> kudos = new ArrayList<Kudo>();

    private Context mContext;

    public ApiCaller(Context context) {
        mContext = context;
    }

    public void logout() {

        //TODO: Oauth and ATA don't have logout funcionality. This code will need to be updated once
        //they update their code to allow a legitimate logout.

        //To facilitate loging out, the session ID with ATA is being destroyed client side.
        //This effectively logs the user out.


        String url = API_URL + "/logout";
        StringRequest logoutRequest = new StringRequest(url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                CookieManager.getInstance().removeAllCookie();
                System.out.println("Logout was successful!");

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CookieManager.getInstance().removeAllCookie();
                System.out.println("Error occured with Volley, logging user out anyways.");

                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);

            }
        });
        AppController.getInstance().addToRequestQueue(logoutRequest);
    }

    public void getAllUsers() {

        /* may need to be updated depending on number of employees */
        String url = DATA_URL + "users?size=3000";

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
                        user.setId(jsonUser.getString(JsonConstants.JSON_USER_ID));
                        user.setFirstName(jsonUser.getString(JsonConstants.JSON_USER_FIRST_NAME));
                        user.setLastName(jsonUser.getString(JsonConstants.JSON_USER_LAST_NAME));
                        user.setTitle(jsonUser.getString(JsonConstants.JSON_USER_TITLE));
                        user.setEmail(jsonUser.getString(JsonConstants.JSON_USER_EMAIL));
                        if (!jsonUser.getString(JsonConstants.JSON_USER_DESCRIPTION).equals("null")) {
                            user.setDescription(jsonUser.getString(JsonConstants.JSON_USER_DESCRIPTION));
                        }

                        users.add(user);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Error: " + e.getMessage());
                }

                EventBus.getDefault().post(new InitialSearchEvent(users));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        // avoid data caching on the device, which can cause 500 errors
        req.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(req);
    }

    /* currently gets all teams in database. Should be changed to only retrieve teams that a given user is on */
    public void getAllTeams() {

        // should be updated to reflect approx. number of teams
        String url = DATA_URL + "teams?size=3000000";

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

        // avoid data caching on the device, which can cause 500 errors
        req.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(req);
    }

    public void getTeamById(String id) {
        String url = DATA_URL + "teams/" + id;

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

                /* activate callback function to view a team activity */
                EventBus.getDefault().post(new ViewTeamEvent(team));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null) {
                    Log.e("Volley", "Error. HTTP Status Code:"+networkResponse.statusCode);
                    Log.e("Volley", "" + networkResponse.data);
                }

            }

        });

        // avoid data caching on the device, which can cause 500 errors
        req.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(req);
    }

    /*
        get team member info on all members of a team
     */
    public void getTeamMembers(Team team) {
        teamMembers.clear();
        for (int i = 0; i < team.getUserList().size(); i++) {
            User user = team.getUserList().get(i);
            getTeamMemberById(i, team.getUserList().size(), user.getId());
        }
    }

    /*
        @params: int counter - iterator of team member in team's list of team members
                 int size - total number of team member's on the team
                 String id - user id of team member[counter]
        copy of getUserById method, but this one will call the updateTeamMember event on the team page when it finishes
     */
    public void getTeamMemberById(final int counter, final int size, String id) {
        String url = DATA_URL + "users/" + id + "/";

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                User user = new User();
                try {
                    user.setId(response.getString(JsonConstants.JSON_USER_ID));
                    user.setFirstName(response.getString(JsonConstants.JSON_USER_FIRST_NAME));
                    user.setLastName(response.getString(JsonConstants.JSON_USER_LAST_NAME));
                    user.setTitle(response.getString(JsonConstants.JSON_USER_TITLE));
                    user.setEmail(response.getString(JsonConstants.JSON_USER_EMAIL));
                    user.setDescription(response.getString(JsonConstants.JSON_USER_DESCRIPTION));

                    teamMembers.add(user);

                    /*
                        if this was the final team member in the list, activate callback function
                        to update list view
                     */
                    if (counter == size-1) {
                        EventBus.getDefault().post(new UpdateTeamMembersEvent(teamMembers));
                    }
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

        // avoid data caching on the device, which can cause 500 errors
        req.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(req);
    }


    /* generic getUserById function */
    public void getUserById(String id) {
        String url = DATA_URL + "users/" + id + "/";

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                User user = new User();
                ArrayList<User> teamMembers = new ArrayList<User>();
                try {
                    user.setId(response.getString(JsonConstants.JSON_USER_ID));
                    user.setFirstName(response.getString(JsonConstants.JSON_USER_FIRST_NAME));
                    user.setLastName(response.getString(JsonConstants.JSON_USER_LAST_NAME));
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

        // avoid data caching on the device, which can cause 500 errors
        req.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(req);
    }

    /*
        @param: String reviewedId - the id of the user who was given the kudos

        creates an arrayList of all kudos given to the user with the given id. Activates
        a callback function in the profile fragment upon completion
     */
    public void getKudos(final String reviewedId) {
        /* size should be updated */
        String url = DATA_URL + "kudos?size=3000000";

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                kudos.clear();
                try {
                    JSONObject embedded = response.getJSONObject(JsonConstants.JSON_EMBEDDED);
                    JSONArray kudosArray = embedded.getJSONArray(JsonConstants.JSON_KUDOS);
                    Log.d(TAG, "kudosArray length = " + kudosArray.length());
                    for (int i = 0; i < kudosArray.length(); i++) {
                        JSONObject jsonKudo = kudosArray.getJSONObject(i);
                        Kudo kudo = new Kudo();
                        User reviewer = new User();
                        User reviewed = new User();
                        kudo.setKudo(jsonKudo.getString(JsonConstants.JSON_KUDOS_COMMENT));
                        kudo.setSubmittedDate(jsonKudo.getString(JsonConstants.JSON_KUDOS_SUBMITTED_DATE));
                        reviewer.setId(jsonKudo.getString(JsonConstants.JSON_KUDOS_REVIEWER_ID));
                        kudo.setReviewer(reviewer);
                        reviewed.setId(jsonKudo.getString(JsonConstants.JSON_KUDOS_REVIEWED_ID));
                        kudo.setReviewed(reviewed);

                        Log.d(TAG, "user's id = " + reviewedId + ", reviewed id = " + reviewed.getId());
                        Log.d(TAG, "Match?  " + (reviewedId.equals(reviewed.getId())));
                        if (reviewedId.equals(reviewed.getId())) {
                            Log.d(TAG, "match!!!!!!!");
                            kudos.add(kudo);
                        }
                    }

                    EventBus.getDefault().post(new UpdateKudosEvent(kudos));


                } catch(JSONException e) {
                    Log.e(TAG, "Error: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        // avoid data caching on the device, which can cause 500 errors
        req.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(req);
    }

    /*
        gets the usernames, by id, of all users who have given a given user kudos.
        It will The callback function is called to update the kudos tab after
        info for every kudos in the list is retrieved
     */
    public void getKudosReviewers(ArrayList<Kudo> kudosList) {
        kudos = kudosList;
        Log.d(TAG, "in getKudosReviewers, kudos size = " + kudos.size());
        for (int i = 0; i < kudos.size(); i++) {
            getKudosReviewerInfo(i, kudos.size(), kudos.get(i));
        }
    }

    /*
        @params: int counter - an iterator of the kudo in the list of kudos a given user has
                int size - the total number of kudos that user has
                Kudo kudo - kudo[counter] in the user's list of kudos
        This function will activate the callback function in the kudos fragment once it has
        retrieved the information for all of a user's kudos (calculated by comparing the total
        size to the current counter)
     */
    public void getKudosReviewerInfo (final int counter, final int size, final Kudo kudo) {
        String url = DATA_URL + "users/" + kudo.getReviewer().getId();
        Log.d(TAG, "in getKudosReviewerInfo, url = " + url);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                User user = new User();

                try {
                    /* set user info about the person who gave the kudos */
                    user.setId(response.getString(JsonConstants.JSON_USER_ID));
                    user.setFirstName(response.getString(JsonConstants.JSON_USER_FIRST_NAME));
                    user.setLastName(response.getString(JsonConstants.JSON_USER_LAST_NAME));
                    user.setTitle(response.getString(JsonConstants.JSON_USER_TITLE));
                    user.setEmail(response.getString(JsonConstants.JSON_USER_EMAIL));
                    user.setDescription(response.getString(JsonConstants.JSON_USER_DESCRIPTION));

                    kudo.setReviewer(user);

                    kudos.set(counter, kudo);

                    /* if this was the final kudo in the list, activate the callback
                        function to update the view in the kudos tab
                     */
                    if (counter == size-1) {
                        EventBus.getDefault().post(new GetKudosInfoEvent(kudos));
                    }
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

        // avoid data caching on the device, which can cause 500 errors
        req.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(req);
    }
}
