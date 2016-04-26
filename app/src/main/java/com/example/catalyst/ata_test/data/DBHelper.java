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
        cv.put(UserContract.UserEntry.COLUMN_USER_DESCRIPTION, user.getDescription());
        cv.put(UserContract.UserEntry.COLUMN_USER_ROLE, user.getRole());

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
            user.setDescription(res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_USER_DESCRIPTION)));
            user.setRole(res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_USER_ROLE)));
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
            user.setDescription(res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_USER_DESCRIPTION)));
            user.setRole(res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_USER_ROLE)));

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
            team.setId(res.getInt(res.getColumnIndex(TeamContract.TeamEntry._ID)));
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

    public ArrayList<User> getCurrentTeamMembers(int teamId) {
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


    public void setSampleData() {
        User bob = new User(1, "Bob", "Jenkins", "A hero with mad tennis skillz", "Developer 1");
        User lucy = new User(2, "Lucy", "Hochneffer", "Fierce but vital", "Developer 2");
        User cassandra = new User(3, "Cassandra", "Loomberg", "Skilled at databases", "Developer 1");
        User travis = new User(4, "Travis", "Spindle", "Call me if you want to learn how to dance", "Tech Lead");
        User heather = new User(5, "Heather", "Carmichael", "once got a hole in one in mini golf", "Developer 1");
        User blaine = new User(6, "Blaine", "Edwards", "bold and brave", "Developer 1");

        addUser(bob);
        addUser(lucy);
        addUser(cassandra);
        addUser(travis);
        addUser(heather);
        addUser(blaine);

        ArrayList<User> lunchmeatTeam = new ArrayList<User>();
        lunchmeatTeam.add(bob);
        lunchmeatTeam.add(lucy);
        ArrayList<User> legoTeam = new ArrayList<User>();
        legoTeam.add(blaine);
        legoTeam.add(heather);
        legoTeam.add(lucy);
        ArrayList<User> waldorfTeam = new ArrayList<User>();
        waldorfTeam.add(cassandra);
        waldorfTeam.add(travis);
        waldorfTeam.add(blaine);
        waldorfTeam.add(bob);

        Team lunchmeat = new Team(1, "Lunchmeat", true, lunchmeatTeam, "We're here to make lunch.");
        Team lego = new Team(2, "Lego Team", true, legoTeam, "Making lego art");
        Team waldorf = new Team(3, "Waldorf", true, waldorfTeam, "Designing an improved Waldorf salad");

        addTeam(lunchmeat);
        addTeam(lego);
        addTeam(waldorf);

        Membership m1 = new Membership(1, 1, true);
        Membership m2 = new Membership(1, 2, true);
        Membership m3 = new Membership(2, 2, true);
        Membership m4 = new Membership(2, 5, true);
        Membership m5 = new Membership(2, 6, true);
        Membership m6 = new Membership(3, 1, true);
        Membership m7 = new Membership(3, 3, true);
        Membership m8 = new Membership(3, 4, true);
        Membership m9 = new Membership(3, 6, true);

        addTeamMember(m1);
        addTeamMember(m2);
        addTeamMember(m3);
        addTeamMember(m4);
        addTeamMember(m5);
        addTeamMember(m6);
        addTeamMember(m7);
        addTeamMember(m8);
        addTeamMember(m9);


        Question q1 = new Question(1, "How would you rate their overall communication skills?");
        Question q2 = new Question(2, "How would you rate their teamwork skills?");
        Question q3 = new Question(3, "How would you rate their street smarts?");
        Question q4 = new Question(4, "How would you rate their hair?");

        addQuestion(q1);
        addQuestion(q2);
        addQuestion(q3);
        addQuestion(q4);

        Feedback f1 = new Feedback(1, "4.0", "Quite good");
        Feedback f2 = new Feedback(2, "3.6", "Not bad");
        Feedback f3 = new Feedback(3, "1.8", "Embarrassing");
        Feedback f4 = new Feedback(4, "4.8", "the very finest");

        ArrayList<Feedback> fb = new ArrayList<Feedback>();
        fb.add(f1);
        fb.add(f2);
        fb.add(f3);
        fb.add(f4);

        Review r1 = new Review(bob, lucy, new Date(), 1, fb);
        Review r2 = new Review(lucy, bob, new Date(), 1, fb);
        Review r3 = new Review(blaine, lucy, new Date(), 2, fb);
        Review r4 = new Review(heather, lucy, new Date(), 2, fb);
        Review r5 = new Review(blaine, heather, new Date(), 2, fb);
        Review r6 = new Review(lucy, heather, new Date(), 2, fb);
        Review r7 = new Review(heather, blaine, new Date(), 2, fb);
        Review r8 = new Review(lucy, blaine, new Date(), 2, fb);
        Review r9 = new Review(bob, cassandra, new Date(), 3, fb);
        Review r10 = new Review(travis, cassandra, new Date(), 3, fb);
        Review r11 = new Review(blaine, cassandra, new Date(), 3, fb);
        Review r12 = new Review(cassandra, bob, new Date(), 3, fb);
        Review r13 = new Review(travis, bob, new Date(), 3, fb);
        Review r14 = new Review(blaine, bob, new Date(), 3, fb);
        Review r15 = new Review(bob, travis, new Date(), 3, fb);
        Review r16 = new Review(blaine, travis, new Date(), 3, fb);
        Review r17 = new Review(cassandra, travis, new Date(), 3, fb);
        Review r18 = new Review(cassandra, blaine, new Date(), 3, fb);
        Review r19 = new Review(bob, blaine, new Date(), 3, fb);
        Review r20 = new Review(travis, blaine, new Date(), 3, fb);

        addReview(r1);
        addReview(r2);
        addReview(r3);
        addReview(r4);
        addReview(r5);
        addReview(r6);
        addReview(r7);
        addReview(r8);
        addReview(r9);
        addReview(r10);
        addReview(r11);
        addReview(r12);
        addReview(r13);
        addReview(r14);
        addReview(r15);
        addReview(r16);
        addReview(r17);
        addReview(r18);
        addReview(r19);
        addReview(r20);





    }


}
