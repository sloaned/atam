package com.example.catalyst.ata_test.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.catalyst.ata_test.models.Feedback;
import com.example.catalyst.ata_test.models.Membership;
import com.example.catalyst.ata_test.models.Question;
import com.example.catalyst.ata_test.models.Review;
import com.example.catalyst.ata_test.models.Team;
import com.example.catalyst.ata_test.models.User;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by dsloane on 4/22/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private final String TAG = getClass().getSimpleName();

    private Context mContext;

    final String SQL_CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + UserContract.UserEntry.TABLE_NAME + " (" +
            UserContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            UserContract.UserEntry.COLUMN_USER_FIRST_NAME + " REAL, " +
            UserContract.UserEntry.COLUMN_USER_LAST_NAME + " REAL, " +
            UserContract.UserEntry.COLUMN_USER_DESCRIPTION + " REAL, " +
            UserContract.UserEntry.COLUMN_USER_ROLE + " REAL)";

    final String SQL_CREATE_TEAM_TABLE = "CREATE TABLE IF NOT EXISTS " + TeamContract.TeamEntry.TABLE_NAME + " (" +
            TeamContract.TeamEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TeamContract.TeamEntry.COLUMN_TEAM_NAME + " REAL, " +
            TeamContract.TeamEntry.COLUMN_TEAM_ACTIVE + " REAL, " +
            TeamContract.TeamEntry.COLUMN_TEAM_DESCRIPTION + " REAL)";

    final String SQL_CREATE_TEAM_MEMBER_TABLE = "CREATE TABLE IF NOT EXISTS " + TeamMemberContract.TeamMemberEntry.TABLE_NAME + " (" +
            TeamMemberContract.TeamMemberEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TeamMemberContract.TeamMemberEntry.COLUMN_USER_ID + " INTEGER, " +
            TeamMemberContract.TeamMemberEntry.COLUMN_TEAM_ID + " INTEGER, " +
            TeamMemberContract.TeamMemberEntry.COLUMN_TEAM_MEMBER_ADDED_ON + " REAL, " +
            TeamMemberContract.TeamMemberEntry.COLUMN_TEAM_MEMBER_REMOVED_ON + " REAL, " +
            TeamMemberContract.TeamMemberEntry.COLUMN_TEAM_MEMBER_ACTIVE + " REAL)";

    final String SQL_CREATE_QUESTION_TABLE = "CREATE TABLE IF NOT EXISTS " + QuestionContract.QuestionEntry.TABLE_NAME + " (" +
            QuestionContract.QuestionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            QuestionContract.QuestionEntry.COLUMN_QUESTION_LABEL + " REAL)";

    final String SQL_CREATE_FEEDBACK_TABLE = "CREATE TABLE IF NOT EXISTS " + FeedbackContract.FeedbackEntry.TABLE_NAME + " (" +
            FeedbackContract.FeedbackEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FeedbackContract.FeedbackEntry.COLUMN_QUESTION_ID + " INTEGER, " +
            FeedbackContract.FeedbackEntry.COLUMN_QUANTITATIVE_SCORE + " REAL, " +
            FeedbackContract.FeedbackEntry.COLUMN_QUALITATIVE_COMMENT + " REAL)";

    final String SQL_CREATE_REVIEW_TABLE = "CREATE TABLE IF NOT EXISTS " + ReviewContract.ReviewEntry.TABLE_NAME + " (" +
            ReviewContract.ReviewEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ReviewContract.ReviewEntry.COLUMN_REVIEWER_ID + " INTEGER, " +
            ReviewContract.ReviewEntry.COLUMN_REVIEWED_ID + " INTEGER, " +
            ReviewContract.ReviewEntry.COLUMN_TEAM_ID + " INTEGER, " +
            ReviewContract.ReviewEntry.COLUMN_SUBMITTED_DATE + " REAL)";

    final String SQL_CREATE_REVIEW_FEEDBACK_TABLE = "CREATE TABLE IF NOT EXISTS " + ReviewFeedbackContract.ReviewFeedbackEntry.TABLE_NAME + " (" +
            ReviewFeedbackContract.ReviewFeedbackEntry.COLUMN_FEEDBACK_ID + " INTEGER, " +
            ReviewFeedbackContract.ReviewFeedbackEntry.COLUMN_REVIEW_ID + " INTEGER)";

    public DBHelper(Context context) {
        super(context, UserContract.DATABASE_NAME, null, 1);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER_TABLE);
        db.execSQL(SQL_CREATE_TEAM_TABLE);
        db.execSQL(SQL_CREATE_TEAM_MEMBER_TABLE);
        db.execSQL(SQL_CREATE_FEEDBACK_TABLE);
        db.execSQL(SQL_CREATE_REVIEW_FEEDBACK_TABLE);
        db.execSQL(SQL_CREATE_REVIEW_TABLE);
        db.execSQL(SQL_CREATE_QUESTION_TABLE);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.UserEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TeamContract.TeamEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionContract.QuestionEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ReviewContract.ReviewEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FeedbackContract.FeedbackEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ReviewFeedbackContract.ReviewFeedbackEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TeamMemberContract.TeamMemberEntry.TABLE_NAME);
        onCreate(db);
    }

    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(UserContract.UserEntry.COLUMN_USER_FIRST_NAME, user.getFirstName());
        cv.put(UserContract.UserEntry.COLUMN_USER_LAST_NAME, user.getLastName());
        cv.put(UserContract.UserEntry.COLUMN_USER_DESCRIPTION, user.getProfileDescription());

        db.insert(UserContract.UserEntry.TABLE_NAME, null, cv);

        return true;
    }

    public boolean addTeam(Team team) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(TeamContract.TeamEntry.COLUMN_TEAM_NAME, team.getName());
        cv.put(TeamContract.TeamEntry.COLUMN_TEAM_ACTIVE, team.isActive());
        cv.put(TeamContract.TeamEntry.COLUMN_TEAM_DESCRIPTION, team.getDescription());

        db.insert(TeamContract.TeamEntry.TABLE_NAME, null, cv);

        return true;
    }

    public boolean addQuestion(Question question) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(QuestionContract.QuestionEntry.COLUMN_QUESTION_LABEL, question.getLabel());

        db.insert(QuestionContract.QuestionEntry.TABLE_NAME, null, cv);

        return true;
    }

    public boolean addTeamMember(Membership member) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(TeamMemberContract.TeamMemberEntry.COLUMN_TEAM_ID, member.getTeamId());
        cv.put(TeamMemberContract.TeamMemberEntry.COLUMN_USER_ID, member.getUserId());
        cv.put(TeamMemberContract.TeamMemberEntry.COLUMN_TEAM_MEMBER_ACTIVE, member.isActive());
       // cv.put(TeamMemberContract.TeamMemberEntry.COLUMN_TEAM_MEMBER_ADDED_ON, member.getAddedOn().getTime());
      //  cv.put(TeamMemberContract.TeamMemberEntry.COLUMN_TEAM_MEMBER_REMOVED_ON, member.getRemovedOn().getTime());

        db.insert(TeamMemberContract.TeamMemberEntry.TABLE_NAME, null, cv);
        return true;
    }

    public boolean addFeedback(Feedback feedback) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(FeedbackContract.FeedbackEntry.COLUMN_REVIEW_ID, feedback.getReviewId());
        cv.put(FeedbackContract.FeedbackEntry.COLUMN_QUESTION_ID, feedback.getQuestion_id());
        cv.put(FeedbackContract.FeedbackEntry.COLUMN_QUANTITATIVE_SCORE, feedback.getScore());
        cv.put(FeedbackContract.FeedbackEntry.COLUMN_QUALITATIVE_COMMENT, feedback.getComment());

        db.insert(FeedbackContract.FeedbackEntry.TABLE_NAME, null, cv);
        return true;
    }

    public boolean addReview(Review review) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(ReviewContract.ReviewEntry.COLUMN_REVIEWER_ID, review.getReviewer().getId());
        cv.put(ReviewContract.ReviewEntry.COLUMN_REVIEWED_ID, review.getReviewed().getId());
        cv.put(ReviewContract.ReviewEntry.COLUMN_TEAM_ID, review.getTeamId());
        cv.put(ReviewContract.ReviewEntry.COLUMN_SUBMITTED_DATE, review.getSubmittedDate().getTime());

        int id = (int) db.insert(ReviewContract.ReviewEntry.TABLE_NAME, null, cv);

        for (Feedback fb : review.getFeedback()) {
            addReviewFeedback(id, fb.getQuestion_id());
        }
        return true;
    }

    public boolean addReviewFeedback(int reviewId, int questionId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(ReviewFeedbackContract.ReviewFeedbackEntry.COLUMN_FEEDBACK_ID, questionId);
        cv.put(ReviewFeedbackContract.ReviewFeedbackEntry.COLUMN_REVIEW_ID, reviewId);

        db.insert(ReviewFeedbackContract.ReviewFeedbackEntry.TABLE_NAME, null, cv);
        return true;
    }

    public User getUserById(int id) {
        User user = new User();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + UserContract.UserEntry.TABLE_NAME + " WHERE " + UserContract.UserEntry._ID + " = " + id + "", null);
        if (res != null) {
            res.moveToFirst();
            user.setFirstName(res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_USER_FIRST_NAME)));
            user.setLastName(res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_USER_LAST_NAME)));
            user.setProfileDescription(res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_USER_DESCRIPTION)));
        }
        res.close();
        db.close();
        return user;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + UserContract.UserEntry.TABLE_NAME + "", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            User user = new User();
            user.setFirstName(res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_USER_FIRST_NAME)));
            user.setLastName(res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_USER_LAST_NAME)));
            user.setProfileDescription(res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_USER_DESCRIPTION)));

            users.add(user);

            res.moveToNext();
        }
        res.close();
        db.close();
        return users;
    }

    public ArrayList<Team> getTeams() {
        ArrayList<Team> teams = new ArrayList<Team>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TeamContract.TeamEntry.TABLE_NAME + "", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            Team team = new Team();
            team.setDescription(res.getString(res.getColumnIndex(TeamContract.TeamEntry.COLUMN_TEAM_DESCRIPTION)));
            team.setId(res.getString(res.getColumnIndex(TeamContract.TeamEntry._ID)));
            team.setName(res.getString(res.getColumnIndex(TeamContract.TeamEntry.COLUMN_TEAM_NAME)));
            if (res.getString(res.getColumnIndex(TeamContract.TeamEntry.COLUMN_TEAM_ACTIVE)) == "true") {
                team.setActive(true);
            } else {
                team.setActive(false);
            }

            team.setUserList(getCurrentTeamMembers(team.getId()));

            teams.add(team);

            res.moveToNext();
        }
        res.close();
        db.close();
        return teams;
    }

    public ArrayList<Question> getQuestions() {
        ArrayList<Question> questions = new ArrayList<Question>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + QuestionContract.QuestionEntry.TABLE_NAME + "", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            Question question = new Question();
            question.setId(res.getInt(res.getColumnIndex(QuestionContract.QuestionEntry._ID)));
            question.setLabel(res.getString(res.getColumnIndex(QuestionContract.QuestionEntry.COLUMN_QUESTION_LABEL)));

            questions.add(question);

            res.moveToNext();
        }
        res.close();
        db.close();
        return questions;
    }

    public ArrayList<Review> getReviewsByUser(int userId) {
        ArrayList<Review> reviews = new ArrayList<Review>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + ReviewContract.ReviewEntry.TABLE_NAME + " WHERE " + ReviewContract.ReviewEntry.COLUMN_REVIEWED_ID + " = " + userId + "", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            Review review = new Review();
            review.setReviewer(getUserById(res.getInt(res.getColumnIndex(ReviewContract.ReviewEntry.COLUMN_REVIEWER_ID))));
            review.setSubmittedDate(new Date(res.getString(res.getColumnIndex(ReviewContract.ReviewEntry.COLUMN_SUBMITTED_DATE))));
            review.setTeamId(res.getInt(res.getColumnIndex(ReviewContract.ReviewEntry.COLUMN_TEAM_ID)));
            review.setFeedback(getFeedbackByReview(res.getInt(res.getColumnIndex(ReviewContract.ReviewEntry._ID))));

            reviews.add(review);

            res.moveToNext();
        }
        res.close();
        db.close();
        return reviews;
    }

    public ArrayList<Feedback> getFeedbackByReview(int reviewId) {
        ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + FeedbackContract.FeedbackEntry.TABLE_NAME + " WHERE " + FeedbackContract.FeedbackEntry.COLUMN_REVIEW_ID + " = " + reviewId + "", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            Feedback feedback = new Feedback();
            feedback.setComment(res.getString(res.getColumnIndex(FeedbackContract.FeedbackEntry.COLUMN_QUALITATIVE_COMMENT)));
            feedback.setQuestion_id(res.getInt(res.getColumnIndex(FeedbackContract.FeedbackEntry.COLUMN_QUESTION_ID)));
            feedback.setScore(res.getString(res.getColumnIndex(FeedbackContract.FeedbackEntry.COLUMN_QUANTITATIVE_SCORE)));

            feedbacks.add(feedback);

            res.moveToNext();
        }
        res.close();
        db.close();
        return feedbacks;
    }

    public ArrayList<User> getCurrentTeamMembers(String teamId) {
        ArrayList<Integer> userIds = new ArrayList<Integer>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TeamMemberContract.TeamMemberEntry.TABLE_NAME + " WHERE " + TeamMemberContract.TeamMemberEntry.COLUMN_TEAM_ID + " = " + teamId + "", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            userIds.add(res.getInt(res.getColumnIndex(TeamMemberContract.TeamMemberEntry.COLUMN_USER_ID)));

            res.moveToNext();
        }

        ArrayList<User> users = new ArrayList<User>();
        for (Integer i : userIds) {
            User user = getUserById(i);
            users.add(user);
        }
        res.close();
        db.close();

        return users;
    }

    public ArrayList<User> searchUsers(String query) {
        ArrayList<User> results = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + UserContract.UserEntry.TABLE_NAME + " WHERE " + UserContract.UserEntry.COLUMN_USER_FIRST_NAME + " LIKE '%" + query + "%' OR " +
                UserContract.UserEntry.COLUMN_USER_LAST_NAME + " LIKE '%" + query + "%'", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            User user = new User();
            user.setFirstName(res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_USER_FIRST_NAME)));
            user.setLastName(res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_USER_LAST_NAME)));
            results.add(user);

            res.moveToNext();
        }
        res.close();
        db.close();

        return results;
    }

}
