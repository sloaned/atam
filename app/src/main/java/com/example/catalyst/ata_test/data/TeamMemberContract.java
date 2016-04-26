package com.example.catalyst.ata_test.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by dsloane on 4/22/2016.
 */
public class TeamMemberContract {


    public static final String CONTENT_AUTHORITY = "com.example.catalyst.ata_test";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_ATA = "ata";

    public static final String DATABASE_NAME = "ata.db";

    public static final class TeamMemberEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ATA).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ATA;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +
                PATH_ATA;

        public static Uri buildTaskUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String TABLE_NAME = "team_member";

        public static final String COLUMN_TEAM_ID = "team_id";

        public static final String COLUMN_USER_ID = "user_id";

        public static final String COLUMN_TEAM_MEMBER_ACTIVE = "active";

        public static final String COLUMN_TEAM_MEMBER_ADDED_ON = "added_on";

        public static final String COLUMN_TEAM_MEMBER_REMOVED_ON = "removed_on";

    }
}
