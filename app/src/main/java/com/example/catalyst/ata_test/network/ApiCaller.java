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
import com.example.catalyst.ata_test.events.AddKudoEvent;
import com.example.catalyst.ata_test.events.BioChangeEvent;
import com.example.catalyst.ata_test.events.GetCurrentUserEvent;
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
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * Created by dsloane on 5/2/2016.
 */
public class ApiCaller {

    // used for logging statements
    public static final String TAG = ApiCaller.class.getSimpleName();

    private static final String API_URL = NetworkConstants.ATAM_BASE; // change to your own computer name

    // the calling activity
    private Context mContext;

    // local storage instances used to retrieve and edit logged in user's id and jsession id
    private SharedPreferences prefs;
    private SharedPreferences.Editor mEditor;

    public ApiCaller(Context context) {
        mContext = context;

        prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        mEditor = prefs.edit();
    }

    /*
        logout of the app and redirect to the login screen
     */
    public void logout() {

        //TODO: Oauth and ATA don't have logout funcionality. This code will need to be updated once
        //they update their code to allow a legitimate logout.

        //To facilitate logging out, the session ID with ATAM is being destroyed client side.
        //This effectively logs the user out.
        String url = API_URL + "/logout";

        StringRequest logoutRequest = new StringRequest(url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                // remove cookies so that we go back to the login page without skipping straight to the authorize page
                CookieManager.getInstance().removeAllCookie();

                //TODO: Remove this line of debug code when app hits version 1.0
                System.out.println("Logout was successful!");

                // delete the jsessionId locally
                mEditor.remove(SharedPreferencesConstants.JSESSIONID).apply();
                // delete the logged in user
                mEditor.remove(SharedPreferencesConstants.USER_ID).apply();

                // create a new intent to go back to the login page
                Intent intent = new Intent(mContext, LoginActivity.class);
                // start that intent
                mContext.startActivity(intent);

            }
        }, new Response.ErrorListener() {
            // logout locally even if response from server is an error
            @Override
            public void onErrorResponse(VolleyError error) {
                // remove cookies so we go straight to login page instead of authorization page
                CookieManager.getInstance().removeAllCookie();

                //TODO: Remove this line of debug code when app hits version 1.0
                System.out.println("Error occured with Volley, logging user out anyways.");

                // delete local data about logged in user
                prefs.edit().remove(SharedPreferencesConstants.JSESSIONID).apply();
                mEditor.remove(SharedPreferencesConstants.USER_ID).apply();

                // create new login activity intent and start the activity
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);

            }
        }) {
            // add jsession id to header
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaders();
            }
        };
        AppController.getInstance().addToRequestQueue(logoutRequest);
    }

    /*
        get the user object of the currently logged in user
     */
    public void getCurrentUser() {
        String url = API_URL + "/users/current";

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject user = response.getJSONObject(JsonConstants.JSON_RESULT);

                    // get the user's user id
                    String userId = user.getString(JsonConstants.JSON_USER_ID);

                    // save the user id locally for future use
                    mEditor.putString(SharedPreferencesConstants.USER_ID, userId).apply();

                    // callback eventbus function to return to the login fragment and open the dashboard
                    EventBus.getDefault().post(new GetCurrentUserEvent());

                } catch (JSONException e) {
                    Log.e(TAG, "Error: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // should probably have a different callback function in case of error
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
            // add jsession id to header
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaders();
            }
        };

        // avoid data caching on the device, which can cause 500 errors
        req.setShouldCache(false);
        // add the request to the request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    /*
        update the user in the database
        - currently only used when editing profile description/bio
     */
    public void updateUser(User user) {

        String url = API_URL + "/users/" + user.getId();

        // create new Gson object for translating user object into JSON
        Gson gson = new Gson();
        // translate user into JSON string
        String gsonUser = gson.toJson(user);
        JSONObject userObject = null;
        // translate JSON string into JSON object
        try {
            userObject = new JSONObject(gsonUser);
        } catch (JSONException e) {
            // should have another callback function here to display error message to user
            Log.e(TAG, "Error: could not create JSONObject from user object");
        }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, url, userObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // EventBus callback which returns to profile page
                EventBus.getDefault().post(new BioChangeEvent());
            }
        }, new Response.ErrorListener() {
            // should have another callback function here to display error message to user
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
            // add jsession id to header
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaders();
            }

        };

        // avoid data caching on the device, which can cause 500 errors
        req.setShouldCache(false);
        // add the request to the request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    /*
        get full profile object for a given user
     */
    public void getProfile(String id) {
        String url = API_URL + "/users/" + id + "/profile";

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: Remove this line of debug code when app hits version 1.0
                Log.d(TAG, response.toString());
                // create new profile object
                Profile profile = new Profile();
                try {
                    JSONObject jsonProfile = response.getJSONObject(JsonConstants.JSON_RESULT);
                    JSONObject jsonUser = jsonProfile.getJSONObject(JsonConstants.JSON_USER);
                    JSONArray jsonKudos = jsonProfile.getJSONArray(JsonConstants.JSON_KUDOS);
                    JSONArray jsonTeams = jsonProfile.getJSONArray(JsonConstants.JSON_TEAMS);
                    JSONArray jsonReviews = jsonProfile.getJSONArray(JsonConstants.JSON_REVIEWS);

                    User user = new User();
                    // set the user info
                    user.setId(jsonUser.getString(JsonConstants.JSON_USER_ID));
                    user.setFirstName(jsonUser.getString(JsonConstants.JSON_USER_FIRST_NAME));
                    user.setLastName(jsonUser.getString(JsonConstants.JSON_USER_LAST_NAME));
                    user.setTitle(jsonUser.getString(JsonConstants.JSON_USER_TITLE));
                    user.setEmail(jsonUser.getString(JsonConstants.JSON_USER_EMAIL));
                    user.setProfileDescription(jsonUser.getString(JsonConstants.JSON_USER_DESCRIPTION));
                    user.setAvatar(jsonUser.getString(JsonConstants.JSON_USER_AVATAR));
                    user.setActive(jsonUser.getBoolean(JsonConstants.JSON_USER_ACTIVE));

                    String startDateString = jsonUser.getString(JsonConstants.JSON_USER_START_DATE);
                    String endDateString;
                    if (!jsonUser.getString(JsonConstants.JSON_USER_END_DATE).equals("null")) {
                        endDateString = jsonUser.getString(JsonConstants.JSON_USER_END_DATE);
                    } else {
                        endDateString = null;
                    }

                    user.setStartDate(startDateString);
                    user.setEndDate(endDateString);
                    user.setVersion(jsonUser.getInt(JsonConstants.JSON_USER_VERSION));

                    ArrayList<Kudo> kudos = new ArrayList<Kudo>();
                    for (int i = 0; i < jsonKudos.length(); i++) {
                        JSONObject jsonKudo = jsonKudos.getJSONObject(i);
                        Kudo kudo = new Kudo();

                        // set the kudos information for each kudos
                        User reviewer = new User();
                        kudo.setKudo(jsonKudo.getString(JsonConstants.JSON_KUDOS_COMMENT));
                        kudo.setSubmittedDate(jsonKudo.getString(JsonConstants.JSON_KUDOS_SUBMITTED_DATE));

                        JSONObject jsonReviewer = jsonKudo.getJSONObject(JsonConstants.JSON_KUDOS_REVIEWER);
                        // set the user information for the kudos giver
                        reviewer.setId(jsonReviewer.getString(JsonConstants.JSON_USER_ID));
                        reviewer.setFirstName(jsonReviewer.getString(JsonConstants.JSON_USER_FIRST_NAME));
                        reviewer.setLastName(jsonReviewer.getString(JsonConstants.JSON_USER_LAST_NAME));
                        reviewer.setTitle(jsonReviewer.getString(JsonConstants.JSON_USER_TITLE));

                        kudo.setReviewer(reviewer);

                        kudo.setReviewed(jsonKudo.getString(JsonConstants.JSON_KUDOS_REVIEWED_ID));

                        // add the kudo to the kudos list
                        kudos.add(kudo);

                    }

                    ArrayList<Team> teams = new ArrayList<Team>();
                    for (int i = 0; i < jsonTeams.length(); i++) {
                        JSONObject jsonTeam = jsonTeams.getJSONObject(i);
                        Team team = new Team();
                        // set info for each team
                        team.setName(jsonTeam.getString(JsonConstants.JSON_TEAM_NAME));
                        team.setDescription(jsonTeam.getString(JsonConstants.JSON_TEAM_DESCRIPTION));
                        team.setId(jsonTeam.getString(JsonConstants.JSON_TEAM_ID));
                        // add the team to the teams list
                        teams.add(team);
                    }

                    ArrayList<Review> reviews = new ArrayList<Review>();
                    for (int i = 0; i < jsonReviews.length(); i++) {
                        JSONObject jsonReview = jsonReviews.getJSONObject(i);
                        Review review = new Review();
                        //TODO: deal with the review information when getting user's profile

                        reviews.add(review);
                    }
                    // add the user info to the profile
                    profile.setUser(user);
                    // add the user's kudos to the profile
                    profile.setKudos(kudos);
                    // add the user's teams to the profile
                    profile.setTeams(teams);
                    // add the user's reviews to the profile
                    profile.setReviews(reviews);

                    // EventBus callback function to display the profile information
                    EventBus.getDefault().post(new ProfileEvent(profile));

                } catch (JSONException e) {
                    // should have another callback function here to display error message to user
                    Log.e(TAG, "Error: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            // should have another callback function here to display error message to user
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
            // add jsession id to header
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaders();
            }
        };

        // avoid data caching on the device, which can cause 500 errors
        req.setShouldCache(false);
        // add the request to the request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    /*
        get list of all teams a given user is on
     */
    public void getTeamsByUser(String id) {
        String url = API_URL + "/teams/user/" + id;

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // create new array list to hold the teams
                ArrayList<Team> teams = new ArrayList<Team>();
                try {
                    JSONArray teamsList = response.getJSONArray(JsonConstants.JSON_RESULT);

                    for (int i = 0; i < teamsList.length(); i++) {
                        JSONObject jsonTeam = teamsList.getJSONObject(i);
                        Team team = new Team();
                        // set basic team information
                        team.setName(jsonTeam.getString(JsonConstants.JSON_TEAM_NAME));
                        team.setDescription(jsonTeam.getString(JsonConstants.JSON_TEAM_DESCRIPTION));
                        team.setId(jsonTeam.getString(JsonConstants.JSON_TEAM_ID));
                        // add team to teams list
                        teams.add(team);
                    }
                } catch (JSONException e) {
                    // should have callback function here to display error to user
                    Log.e(TAG, "Error: " + e.getMessage());
                }

                EventBus.getDefault().post(new UpdateTeamsEvent(teams));
            }
        }, new Response.ErrorListener() {
            // should have callback function here to display error to user
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
            // add jsession id to header
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaders();
            }
        };

        // avoid data caching on the device, which can cause 500 errors
        req.setShouldCache(false);
        // add the request to the request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    /*
        get full team object for a given team, complete with team members
     */
    public void getTeamById(String id) {
        String url = API_URL + "/teams/" + id;

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // create a new team object
                Team team = new Team();
                try {
                    JSONObject jsonTeam = response.getJSONObject(JsonConstants.JSON_RESULT);

                    // set the team information
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

                            User user = new User();
                            // add user info for each member on the team
                            user.setId(userObject.getString(JsonConstants.JSON_USER_ID));
                            user.setFirstName(userObject.getString(JsonConstants.JSON_USER_FIRST_NAME));
                            user.setLastName(userObject.getString(JsonConstants.JSON_USER_LAST_NAME));
                            user.setTitle(userObject.getString(JsonConstants.JSON_USER_TITLE));
                            user.setEmail(userObject.getString(JsonConstants.JSON_USER_EMAIL));
                            user.setProfileDescription(userObject.getString(JsonConstants.JSON_USER_DESCRIPTION));
                            // add user to member list
                            members.add(user);
                        }
                        // add list of members to team
                        team.setUserList(members);
                    }
                } catch (JSONException e) {
                    // should have another function here to display error to user
                    Log.e(TAG, "Error: " + e.getMessage());
                }

                /* activate callback function to view a team activity */
                EventBus.getDefault().post(new ViewTeamEvent(team));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // should have another function here to display error to user
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null) {
                    Log.e("Volley", "Error. HTTP Status Code:" + networkResponse.statusCode);
                    Log.e("Volley", "" + networkResponse.data);
                }
            }
        }) {
            // add jsession id to header
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaders();
            }
        };
        // avoid data caching on the device, which can cause 500 errors
        req.setShouldCache(false);
        // add the request to the request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    /*
        network call to search for users and teams
     */
    public void search(String searchTerm) {
        String url = API_URL + "/search/" + searchTerm;

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // an array list to hold search results
                ArrayList<SearchResult> results = new ArrayList<SearchResult>();
                try {
                    // get an array of results
                    JSONArray jsonResults = response.getJSONArray(JsonConstants.JSON_RESULT);

                    for (int i = 0; i < jsonResults.length(); i++) {
                        // create a new search result object
                        SearchResult result = new SearchResult();

                        JSONObject jsonResult = jsonResults.getJSONObject(i);

                        /*
                            search result contains either full user or full team.
                            Whichever isn't fully returned is null.
                            If user isn't null, fill result object with that user
                         */
                        if (jsonResult.get(JsonConstants.JSON_USER) != JSONObject.NULL) {
                            JSONObject jsonUser = jsonResult.getJSONObject(JsonConstants.JSON_USER);

                            User user = new User();

                            // set the user information
                            user.setId(jsonUser.getString(JsonConstants.JSON_USER_ID));
                            user.setFirstName(jsonUser.getString(JsonConstants.JSON_USER_FIRST_NAME));
                            user.setLastName(jsonUser.getString(JsonConstants.JSON_USER_LAST_NAME));
                            user.setTitle(jsonUser.getString(JsonConstants.JSON_USER_TITLE));
                            user.setEmail(jsonUser.getString(JsonConstants.JSON_USER_EMAIL));
                            user.setProfileDescription(jsonUser.getString(JsonConstants.JSON_USER_DESCRIPTION));

                            // set the user to the search result object
                            result.setUser(user);
                        } // otherwise if the team isn't null, fill the search result with the team object
                        else if (jsonResult.get(JsonConstants.JSON_TEAM) != JSONObject.NULL) {

                            JSONObject jsonTeam = jsonResult.getJSONObject(JsonConstants.JSON_TEAM);

                            Team team = new Team();

                            // set the team information
                            team.setName(jsonTeam.getString(JsonConstants.JSON_TEAM_NAME));
                            team.setDescription(jsonTeam.getString(JsonConstants.JSON_TEAM_DESCRIPTION));
                            team.setId(jsonTeam.getString(JsonConstants.JSON_TEAM_ID));
                            // set the team to the search result object
                            result.setTeam(team);
                        }
                        // add result to list of search results
                        results.add(result);
                    }
                } catch (JSONException e) {
                    // should probably have another callback function here to display error message to user
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
            // add jsession id to header
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaders();
            }
        };
        // avoid data caching on the device, which can cause 500 errors
        req.setShouldCache(false);
        // add the request to the request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    /*
        add a new kudo to the database
     */
    public void postKudo(Kudo kudo) {
        String url = API_URL + "/kudos";

        // create Gson object to translate the kudo object into a JSONObject for network transport
        Gson gson = new Gson();
        // translate kudo object to JSON string
        String gsonKudo = gson.toJson(kudo);
        JSONObject kudoObject = null;
        // translate JSON string into JSONObject
        try {
            kudoObject = new JSONObject(gsonKudo);
        } catch (JSONException e) {
            // should probably have another callback function here to display error message
            Log.e(TAG, "Error, could not create new JSONObject out of kudo");
        }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, kudoObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // start EventBus callback function on success
                EventBus.getDefault().post(new AddKudoEvent());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // should probably have another callback function in case of error
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
            // add jsession id to header
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaders();
            }
        };
        // avoid data caching on the device, which can cause 500 errors
        req.setShouldCache(false);
        // add the request to the request queue
        AppController.getInstance().addToRequestQueue(req);
    }


    public HashMap<String, String> setHeaders() {
        HashMap<String, String> headers = new HashMap<String, String>();
        String cookie = prefs.getString(SharedPreferencesConstants.JSESSIONID, null);
        headers.put("Cookie:", cookie);
        return headers;
    }


/*
    public Date convertMillisecondsToFormattedDate(String milliseconds) {
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(milliseconds));
        return format.parse(calendar.getTime());
    }  */

}