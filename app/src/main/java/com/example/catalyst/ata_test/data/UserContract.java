package com.example.catalyst.ata_test.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by dsloane on 4/22/2016.
 */
public class UserContract {

    public static final String CONTENT_AUTHORITY = "com.example.catalyst.ata_test";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_TASK = "task";

    public static final String DATABASE_NAME = "ata.db";

    public static final class UserEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASK).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TASK;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TASK;

        public static Uri buildTaskUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String TABLE_NAME = "user";

        public static final String COLUMN_USER_FIRST_NAME = "first_name";

        public static final String COLUMN_USER_LAST_NAME = "last_name";

        public static final String COLUMN_USER_DESCRIPTION = "description";

        public static final String COLUMN_USER_ROLE = "role";

    }
}
