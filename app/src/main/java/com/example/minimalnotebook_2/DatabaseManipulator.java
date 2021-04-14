/**
 * Minimal Notebook - A prototype notebook system.
 *
 * Mobile Applications Development - SET08114
 * 10/04/21
 *
 * Murray F Hastie
 * 40285583
 * Software Engineering (BENG) - Year 2
 */

package com.example.minimalnotebook_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Database Class
 * Allows information to be stored to device internal storage
 * Data will persist if app is closed
 */
public class DatabaseManipulator extends SQLiteOpenHelper
{
    /**
     * Declare database information
     * */
    private static final String TAG = "DatabaseManipulator";
    private static final String DATABASE_NAME = "notes_database";
    private static int DATABASE_VERSION = 1;
    private static String TABLE_NAME = "my_notes";
    private static final String COL0 = "ID";
    private static final String COL1 = "title";
    private static final String COL2 = "content";

    /**
     * Define database context
     * @param context
     */
    public DatabaseManipulator(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Create database table
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createTable = "CREATE TABLE "
                + TABLE_NAME
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL1
                + " TEXT, "
                + COL2
                +" TEXT)";

        db.execSQL(createTable);
    }

    /**
     * Upgrade database version
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        DATABASE_VERSION = newVersion;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Insert data into database
     * @param title
     * @param content
     * @return
     */
    public boolean addData(String title, String content)
    {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Creates a map of new values
        ContentValues values = new ContentValues();
        values.put(COL1, title);
        values.put(COL2, content);

        Log.d(TAG, "addNote: Adding" + title + " to " + TABLE_NAME);

        // Insert the new row
        long result = db.insert(TABLE_NAME, null, values);

        // Check if data has been installed correctly
        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Returns everything from the database
     * @return
     */
    public Cursor getData()
    {
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    /**
     * Returns the ID of the row where title matches
     * @param title
     * @return
     */
    public Cursor getID(String title)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT " + COL0 + " FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = '" + title + "'", null);
        return data;
    }

    /**
     * Returns the content of the row where title matches
     * @param title
     * @return
     */
    public Cursor getContent(String title)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT " + COL2 + " FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = '" + title + "'", null);
        return data;
    }

    /**
     * Updates the title field
     * @param newTitle
     * @param id
     * @param oldTitle
     */
    public void updateTitle(int id, String oldTitle, String newTitle)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL1 +
                " = '" + newTitle + "' WHERE " + COL0 + " = '" + id + "'" +
                " AND " + COL1 + " = '" + oldTitle + "'";

        Log.d(TAG, "updateTitle: Title set to: " + newTitle);
        db.execSQL(query);
    }

    /**
     * Updates the content field
     * @param id
     * @param oldContent
     * @param newContent
     */
    public void updateContent(int id, String oldContent, String newContent)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + newContent + "' WHERE " + COL0 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + oldContent + "'";

        Log.d(TAG, "updateContent: Content set to: " + newContent);
        db.execSQL(query);
    }

    /**
     * Delete from database
     * @param id
     * @param title
     */
    public void deleteNote(int id, String title)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL0 + " = '" + id + "'" +
                " AND " + COL1 + " = '" + title + "'";
        Log.d(TAG, "deleteTitle: Note: " + title + " has been removed from the database.");
        db.execSQL(query);
    }
}
