package com.example.catalyst.ata_test.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by dsloane on 4/25/2016.
 */
public class FeedbackContract {

    public static final String CONTENT_AUTHORITY = "com.example.catalyst.ata_test";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_ATA = "ata";

    public static final String DATABASE_NAME = "ata.db";

    public static final class FeedbackEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ATA).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ATA;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +
                PATH_ATA;

        public static Uri buildTaskUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String TABLE_NAME = "feedback";

        public static final String COLUMN_REVIEW_ID = "review_id";

        public static final String COLUMN_QUESTION_ID = "question_id";

        public static final String COLUMN_QUANTITATIVE_SCORE = "quantitative_score";

        public static final String COLUMN_QUALITATIVE_COMMENT = "comment";

    }

}
