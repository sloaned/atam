package com.example.catalyst.ata_test.network;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.CookieManager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.catalyst.ata_test.AppController;
import com.example.catalyst.ata_test.activities.LoginActivity;
import com.example.catalyst.ata_test.events.GetCurrentUserEvent;
import com.example.catalyst.ata_test.events.InitialSearchEvent;
import com.example.catalyst.ata_test.events.ProfileEvent;
import com.example.catalyst.ata_test.events.SearchEvent;
import com.example.catalyst.ata_test.events.UpdateTeamsEvent;
import com.example.catalyst.ata_test.events.ViewTeamEvent;
import com.example.catalyst.ata_test.models.Kudo;
import com.example.catalyst.ata_test.models.Profile;
import com.example.catalyst.ata_test.models.Review;
import com.example.catalyst.ata_test.models.SearchResult;
import com.example.catalyst.ata_test.models.Team;
import com.example.catalyst.ata_test.models.User;
import com.example.catalyst.ata_test.util.JsonConstants;
import com.example.catalyst.ata_test.util.NetworkConstants;
import com.example.catalyst.ata_test.util.SharedPreferencesConstants;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by dsloane on 5/2/2016.
 */
public class ApiCaller {

    public static final String TAG = ApiCaller.class.getSimpleName();

    private static final String DATA_URL = NetworkConstants.ATAM_BASE + "/";   // change to your own computer name
    private static final String API_URL = NetworkConstants.ATAM_BASE;

    private ArrayList<User> teamMembers = new ArrayList<User>();
    private ArrayList<Kudo> kudos = new ArrayList<Kudo>();

    private Context mContext;

    private SharedPreferences prefs;
    private SharedPreferences.Editor mEditor;

    public ApiCaller(Context context) {
        mContext = context;

        prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        mEditor = prefs.edit();
    }

    public void logout() {

        //TODO: Oauth and ATA don't have logout funcionality. This code will need to be updated once
        //they update their code to allow a legitimate logout.

        //To facilitate loging out, the session ID with ATAM is being destroyed client side.
        //This effectively logs the user out.


        String url = API_URL + "/logout";

        StringRequest logoutRequest = new StringRequest(url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                CookieManager.getInstance().removeAllCookie();

                //TODO: Remove this line of debug code when app hits version 1.0
                System.out.println("Logout was successful!");
                mEditor.putString(SharedPreferencesConstants.JSESSIONID, null).apply();
                mEditor.putString(SharedPreferencesConstants.USER_ID, null).apply();

                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CookieManager.getInstance().removeAllCookie();

                //TODO: Remove this line of debug code when app hits version 1.0
                System.out.println("Error occured with Volley, logging user out anyways.");

                prefs.edit().putString(SharedPreferencesConstants.JSESSIONID, null).apply();
                mEditor.putString(SharedPreferencesConstants.USER_ID, null).apply();


                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();

                String cookie = prefs.getString(SharedPreferencesConstants.JSESSIONID, null);

                headers.put("Cookie:", cookie);

                return headers;
            }
        };
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
                    //  JSONObject embedded = response.getJSONObject(JsonConstants.JSON_EMBEDDED);
                    JSONArray userList = response.getJSONArray(JsonConstants.JSON_RESULT);

                    //TODO: Remove this line of debug code when app hits version 1.0
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
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();

                String cookie = prefs.getString(SharedPreferencesConstants.JSESSIONID, null);

                headers.put("Cookie:", cookie);

                return headers;
            }
        };

        // avoid data caching on the device, which can cause 500 errors
        req.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(req);
    }

    public void getCurrentUser() {
        String url = DATA_URL + "/users/current";

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    JSONObject user = response.getJSONObject(JsonConstants.JSON_RESULT);
                    String userId = user.getString(JsonConstants.JSON_USER_ID);
                    mEditor.putString(SharedPreferencesConstants.USER_ID, userId).apply();

                    EventBus.getDefault().post(new GetCurrentUserEvent());

                } catch (JSONException e) {
                    Log.e(TAG, "Error: " + e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();

                String cookie = prefs.getString(SharedPreferencesConstants.JSESSIONID, null);

                headers.put("Cookie:", cookie);

                return headers;
            }
        };

        // avoid data caching on the device, which can cause 500 errors
        req.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(req);
    }

    public void getProfile(String id) {
        String url = DATA_URL + "/users/" + id + "/profile";


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: Remove this line of debug code when app hits version 1.0
                Log.d(TAG, response.toString());

                Profile profile = new Profile();
                try {
                    JSONObject jsonProfile = response.getJSONObject(JsonConstants.JSON_RESULT);
                    JSONObject jsonUser = jsonProfile.getJSONObject(JsonConstants.JSON_USER);
                    JSONArray jsonKudos = jsonProfile.getJSONArray(JsonConstants.JSON_KUDOS);
                    JSONArray jsonTeams = jsonProfile.getJSONArray(JsonConstants.JSON_TEAMS);
                    JSONArray jsonReviews = jsonProfile.getJSONArray(JsonConstants.JSON_REVIEWS);

                    User user = new User();
                    user.setId(jsonUser.getString(JsonConstants.JSON_USER_ID));
                    user.setFirstName(jsonUser.getString(JsonConstants.JSON_USER_FIRST_NAME));
                    user.setLastName(jsonUser.getString(JsonConstants.JSON_USER_LAST_NAME));
                    user.setTitle(jsonUser.getString(JsonConstants.JSON_USER_TITLE));
                    user.setEmail(jsonUser.getString(JsonConstants.JSON_USER_EMAIL));
                    user.setDescription(jsonUser.getString(JsonConstants.JSON_USER_DESCRIPTION));

                    ArrayList<Kudo> kudos = new ArrayList<Kudo>();
                    for (int i = 0; i < jsonKudos.length(); i++) {
                        JSONObject jsonKudo = jsonKudos.getJSONObject(i);
                        Kudo kudo = new Kudo();
                        User reviewer = new User();
                        User reviewed = new User();
                        kudo.setKudo(jsonKudo.getString(JsonConstants.JSON_KUDOS_COMMENT));
                        kudo.setSubmittedDate(jsonKudo.getString(JsonConstants.JSON_KUDOS_SUBMITTED_DATE));

                        JSONObject jsonReviewer = jsonKudo.getJSONObject(JsonConstants.JSON_KUDOS_REVIEWER);

                        reviewer.setId(jsonReviewer.getString(JsonConstants.JSON_USER_ID));
                        reviewer.setFirstName(jsonReviewer.getString(JsonConstants.JSON_USER_FIRST_NAME));
                        reviewer.setLastName(jsonReviewer.getString(JsonConstants.JSON_USER_LAST_NAME));
                        reviewer.setTitle(jsonReviewer.getString(JsonConstants.JSON_USER_TITLE));

                        kudo.setReviewer(reviewer);

                        reviewed.setId(jsonKudo.getString(JsonConstants.JSON_KUDOS_REVIEWED_ID));
                        kudo.setReviewed(reviewed);

                        kudos.add(kudo);

                    }

                    ArrayList<Team> teams = new ArrayList<Team>();
                    for (int i = 0; i < jsonTeams.length(); i++) {
                        JSONObject jsonTeam = jsonTeams.getJSONObject(i);
                        Team team = new Team();
                        team.setName(jsonTeam.getString(JsonConstants.JSON_TEAM_NAME));
                        team.setDescription(jsonTeam.getString(JsonConstants.JSON_TEAM_DESCRIPTION));
                        team.setId(jsonTeam.getString(JsonConstants.JSON_TEAM_ID));
                        teams.add(team);
                    }

                    ArrayList<Review> reviews = new ArrayList<Review>();
                    for (int i = 0; i < jsonReviews.length(); i++) {
                        JSONObject jsonReview = jsonReviews.getJSONObject(i);
                        Review review = new Review();


                        reviews.add(review);
                    }

                    profile.setUser(user);
                    profile.setKudos(kudos);
                    profile.setTeams(teams);
                    profile.setReviews(reviews);

                    EventBus.getDefault().post(new ProfileEvent(profile));

                } catch (JSONException e) {
                    Log.e(TAG, "Error: " + e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();

                String cookie = prefs.getString(SharedPreferencesConstants.JSESSIONID, null);

                headers.put("Cookie:", cookie);

                return headers;
            }
        };

        // avoid data caching on the device, which can cause 500 errors
        req.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(req);
    }
/*
    public void getUserById(String id) {
        String url = DATA_URL + "users/" + id + "/";

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: Remove this line of debug code when app hits version 1.0
                Log.d(TAG, response.toString());

                User user = new User();
                try {
                    JSONObject jsonUser = response.getJSONObject(JsonConstants.JSON_RESULT);
                    user.setId(jsonUser.getString(JsonConstants.JSON_USER_ID));
                    user.setFirstName(jsonUser.getString(JsonConstants.JSON_USER_FIRST_NAME));
                    user.setLastName(jsonUser.getString(JsonConstants.JSON_USER_LAST_NAME));
                    user.setTitle(jsonUser.getString(JsonConstants.JSON_USER_TITLE));
                    user.setEmail(jsonUser.getString(JsonConstants.JSON_USER_EMAIL));
                    user.setDescription(jsonUser.getString(JsonConstants.JSON_USER_DESCRIPTION));

                   // EventBus.getDefault().post(new ProfileEvent(user));

                } catch (JSONException e) {
                    Log.e(TAG, "Error: " + e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();

                String cookie = prefs.getString(SharedPreferencesConstants.JSESSIONID, null);

                headers.put("Cookie:", cookie);

                return headers;
            }
        };

        // avoid data caching on the device, which can cause 500 errors
        req.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(req);
    } */

    public void getTeamsByUser(String id) {
        String url = DATA_URL + "teams/user/" + id;

        Log.d(TAG, "in getTeamsByUser, url = " + url);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                ArrayList<Team> teams = new ArrayList<Team>();
                try {
                    JSONArray teamsList = response.getJSONArray(JsonConstants.JSON_RESULT);

                    for (int i = 0; i < teamsList.length(); i++) {
                        JSONObject jsonTeam = teamsList.getJSONObject(i);
                        Team team = new Team();
                        team.setName(jsonTeam.getString(JsonConstants.JSON_TEAM_NAME));
                        team.setDescription(jsonTeam.getString(JsonConstants.JSON_TEAM_DESCRIPTION));
                        team.setId(jsonTeam.getString(JsonConstants.JSON_TEAM_ID));
                        teams.add(team);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Error: " + e.getMessage());
                }

                EventBus.getDefault().post(new UpdateTeamsEvent(teams));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();

                String cookie = prefs.getString(SharedPreferencesConstants.JSESSIONID, null);

                headers.put("Cookie:", cookie);

                return headers;
            }
        };

        // avoid data caching on the device, which can cause 500 errors
        req.setShouldCache(false);

        AppController.getInstance().addToRequestQueue(req);
    }

    public void getTeamById(String id) {
        String url = DATA_URL + "teams/" + id;

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: Remove this line of debug code when app hits version 1.0
                Log.d(TAG, response.toString());

                Team team = new Team();
                try {
                    JSONObject jsonTeam = response.getJSONObject(JsonConstants.JSON_RESULT);
                    team.setId(jsonTeam.getString(JsonConstants.JSON_TEAM_ID));
                    team.setName(jsonTeam.getString(JsonConstants.JSON_TEAM_NAME));
                    team.setDescription(jsonTeam.getString(JsonConstants.JSON_TEAM_DESCRIPTION));
                    team.setActive(jsonTeam.getBoolean(JsonConstants.JSON_TEAM_ACTIVE));
                    if (!jsonTeam.getString(JsonConstants.JSON_TEAM_MEMBERLIST).equals("null")) {
                        JSONArray memberList = jsonTeam.getJSONArray(JsonConstants.JSON_TEAM_MEMBERLIST);
                        ArrayList<User> members = new ArrayList<User>();
                        for (int i = 0; i < memberList.length(); i++) {
                            JSONObject member = memberList.getJSONObject(i);

                            JSONObject userObject = member.getJSONObject(JsonConstants.JSON_TEAM_MEMBER);

                            //TODO: Remove this line of debug code when app hits version 1.0
                            Log.d(TAG, "userObject = " + userObject.toString());

                            User user = new User();
                            user.setId(userObject.getString(JsonConstants.JSON_USER_ID));
                            user.setFirstName(userObject.getString(JsonConstants.JSON_USER_FIRST_NAME));
                            user.setLastName(userObject.getString(JsonConstants.JSON_USER_LAST_NAME));
                            user.setTitle(userObject.getString(JsonConstants.JSON_USER_TITLE));
                            user.setEmail(userObject.getString(JsonConstants.JSON_USER_EMAIL));
                            user.setDescription(userObject.getString(JsonConstants.JSON_USER_DESCRIPTION));


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
                    Log.e("Volley", "Error. HTTP Status Code:" + networkResponse.statusCode);
                    Log.e("Volley", "" + networkResponse.data);
                }

            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();

                String cookie = prefs.getString(SharedPreferencesConstants.JSESSIONID, null);

                headers.put("Cookie:", cookie);

                return headers;
            }
        };
        // avoid data caching on the device, which can cause 500 errors
        req.setShouldCache(false);


        AppController.getInstance().addToRequestQueue(req);
    }


    public void search(String searchTerm) {
        String url = DATA_URL + "search/" + searchTerm;

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: Remove this line of debug code when app hits version 1.0
                Log.d(TAG, response.toString());

                ArrayList<SearchResult> results = new ArrayList<SearchResult>();
                try {
                    JSONArray jsonResults = response.getJSONArray(JsonConstants.JSON_RESULT);

                    for (int i = 0; i < jsonResults.length(); i++) {
                        SearchResult result = new SearchResult();

                        JSONObject jsonResult = jsonResults.getJSONObject(i);
                        if (jsonResult.get(JsonConstants.JSON_USER) != JSONObject.NULL) {
                            JSONObject jsonUser = jsonResult.getJSONObject(JsonConstants.JSON_USER);

                            User user = new User();
                            user.setId(jsonUser.getString(JsonConstants.JSON_USER_ID));
                            user.setFirstName(jsonUser.getString(JsonConstants.JSON_USER_FIRST_NAME));
                            user.setLastName(jsonUser.getString(JsonConstants.JSON_USER_LAST_NAME));
                            user.setTitle(jsonUser.getString(JsonConstants.JSON_USER_TITLE));
                            user.setEmail(jsonUser.getString(JsonConstants.JSON_USER_EMAIL));
                            user.setDescription(jsonUser.getString(JsonConstants.JSON_USER_DESCRIPTION));

                            result.setUser(user);
                        } else if (jsonResult.get(JsonConstants.JSON_TEAM) != JSONObject.NULL) {

                            JSONObject jsonTeam = jsonResult.getJSONObject(JsonConstants.JSON_TEAM);

                            Team team = new Team();

                            team.setName(jsonTeam.getString(JsonConstants.JSON_TEAM_NAME));
                            team.setDescription(jsonTeam.getString(JsonConstants.JSON_TEAM_DESCRIPTION));
                            team.setId(jsonTeam.getString(JsonConstants.JSON_TEAM_ID));
                            result.setTeam(team);
                        }

                        results.add(result);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Error: " + e.getMessage());
                }

                /* activate callback function to view a team activity */
                EventBus.getDefault().post(new SearchEvent(results));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null) {
                    Log.e("Volley", "Error. HTTP Status Code:" + networkResponse.statusCode);
                    Log.e("Volley", "" + networkResponse.data);
                }
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();

                String cookie = prefs.getString(SharedPreferencesConstants.JSESSIONID, null);

                headers.put("Cookie:", cookie);

                return headers;
            }
        };
        // avoid data caching on the device, which can cause 500 errors
        req.setShouldCache(false);


        AppController.getInstance().addToRequestQueue(req);
    }

}
