package group12.seng301.textbooktrade;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.EventLogTags;
import android.util.Log;

import group12.seng301.textbooktrade.objects.Book;
import group12.seng301.textbooktrade.objects.User;

public class TextbooksDatabaseHelper extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "TextbookTradeDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_BOOKS = "books";
    private static final String TABLE_USERS = "users";

    // Post Table Columns
    private static final String KEY_BOOK_ID = "id";
    private static final String KEY_BOOK_USER_ID_FK = "userId";
    private static final String KEY_BOOK_NAME = "text";
    private static final String KEY_BOOK_AUTHOR = "author";
    private static final String KEY_BOOK_USEFULLNESS = "usefullness";
    private static final String KEY_BOOK_CONDITION = "condition";
    private static final String KEY_BOOK_IMAGE_URL = "imageUrl";

    // User Table Columns
    private static final String KEY_USER_ID = "id";
    private static final String KEY_USER_NAME = "fullName";
    private static final String KEY_USER_EMAIL = "email";
    private static final String KEY_USER_MAJOR = "major";
    private static final String KEY_USER_BOOKS = "books";


    private static TextbooksDatabaseHelper instance;

    private static final String TAG = "TextbooksDatabaseHelper";

    public static synchronized TextbooksDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new TextbooksDatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private TextbooksDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);

    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOK_TABLE = "CREATE TABLE " + TABLE_BOOKS +
                "(" +
                KEY_BOOK_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_BOOK_USER_ID_FK + " INTEGER REFERENCES " + TABLE_USERS + "," + // Define a foreign key
                KEY_BOOK_NAME + " TEXT," +
                KEY_BOOK_AUTHOR + " TEXT," +
                KEY_BOOK_USEFULLNESS + " INTEGER," +
                KEY_BOOK_CONDITION + " INTEGER," +
                KEY_BOOK_IMAGE_URL + " TEXT" +
                ")";

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" +
                KEY_USER_ID + " INTEGER PRIMARY KEY," +
                KEY_USER_NAME + " TEXT," +
                KEY_USER_EMAIL + " TEXT," +
                KEY_USER_MAJOR + " TEXT," +
                KEY_USER_BOOKS + " TEXT" + // TODO: Reference here. or other implementation
                ")";

        db.execSQL(CREATE_BOOK_TABLE);
        db.execSQL(CREATE_USERS_TABLE);
    }


    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            onCreate(db);
        }
    }

    // Insert a book into the database
    public void addPost(Book book) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            // Update user and return the id.
            long userId = addOrUpdateUser(book.getUser());

            ContentValues values = new ContentValues();
            values.put(KEY_BOOK_USER_ID_FK, userId);
            values.put(KEY_BOOK_NAME, book.getName());
            values.put(KEY_BOOK_AUTHOR, book.getAuthor());
            values.put(KEY_BOOK_USEFULLNESS, book.getUsefullness());
            values.put(KEY_BOOK_CONDITION, book.getCondition());
            values.put(KEY_BOOK_IMAGE_URL, book.getImageURL());


            db.insertOrThrow(TABLE_BOOKS, null, values);
            db.setTransactionSuccessful(); // Mark as successful so changes are kept
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    // Insert or update a user in the database
    // Since SQLite doesn't support "upsert" we need to fall back on an attempt to UPDATE (in case the
    // user already exists) optionally followed by an INSERT (in case the user does not already exist).
    // Unfortunately, there is a bug with the insertOnConflict method
    // (https://code.google.com/p/android/issues/detail?id=13045) so we need to fall back to the more
    // verbose option of querying for the user's primary key if we did an update.
    public long addOrUpdateUser(User user) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        long userId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_USER_NAME, user.getFullname());
            values.put(KEY_USER_EMAIL, user.getEmail());
            values.put(KEY_USER_MAJOR, user.getMajor().toString());
            values.put(KEY_USER_BOOKS, user.getEmail()); //Todo: books

            // First try to update the user in case the user already exists in the database
            // This assumes userNames are unique
            int rows = db.update(TABLE_USERS, values, KEY_USER_NAME + "= ?", new String[]{user.getFullname()});

            // Check if update succeeded
            if (rows == 1) {
                // Get the primary key of the user we just updated
                String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                        KEY_USER_ID, TABLE_USERS, KEY_USER_NAME);
                Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(user.getFullname())});
                try {
                    if (cursor.moveToFirst()) {
                        userId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } else {
                // user with this userName did not already exist, so insert new user
                userId = db.insertOrThrow(TABLE_USERS, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return userId;
    }
}
