package com.example.catalyst.ata_test.util;

/**
 * Created by dsloane on 5/5/2016.
 */
public class JsonConstants {
    /*
        list of string constants used in retrieving json data from server
     */
    public static final String JSON_EMBEDDED = "_embedded";
    public static final String JSON_RESULT = "result";

    // strings related to profile object
    public static final String JSON_USERS = "users";
    public static final String JSON_USER = "user";
    public static final String JSON_KUDOS = "kudos";
    public static final String JSON_REVIEWS = "reviews";
    public static final String JSON_TEAMS = "teams";
    public static final String JSON_TEAM = "team";

    // strings related to user object
    public static final String JSON_USER_ID = "id";
    public static final String JSON_USER_FIRST_NAME = "firstName";
    public static final String JSON_USER_LAST_NAME = "lastName";
    public static final String JSON_USER_EMAIL = "email";
    public static final String JSON_USER_TITLE = "title";
    public static final String JSON_USER_DESCRIPTION = "profileDescription";
    public static final String JSON_USER_VERSION = "version";
    public static final String JSON_USER_AVATAR = "avatar";
    public static final String JSON_USER_ACTIVE = "isActive";
    public static final String JSON_USER_START_DATE = "startDate";
    public static final String JSON_USER_END_DATE = "endDate";

    // strings related to team object
    public static final String JSON_TEAM_ID = "id";
    public static final String JSON_TEAM_NAME = "name";
    public static final String JSON_TEAM_DESCRIPTION = "description";
    public static final String JSON_TEAM_ACTIVE = "isActive";
    public static final String JSON_TEAM_MEMBERLIST = "memberList";
    public static final String JSON_TEAM_MEMBER = "user";

    // strings related to kudos object
    public static final String JSON_KUDOS_REVIEWER = "reviewer";
    public static final String JSON_KUDOS_REVIEWED_ID = "reviewedId";
    public static final String JSON_KUDOS_COMMENT = "comment";
    public static final String JSON_KUDOS_SUBMITTED_DATE = "submittedDate";




}
