package com.example.ui_do_an.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ui_do_an.Note;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="APPNOTE";
    private static final int DATABASE_VERSION =1;
    //1. bang note
    private static final String TABLE_NOTES="NOTE";
    private static final String COLUMN_NOTE_ID="ID";
    private static final String COLUMN_NOTE_TITLE="TITLE";
    private static final String COLUMN_NOTE_DATE="DATE";
    private static final String COLUMN_NOTE_CONTENT="CONTENT";
    //2. bang user
    private static final String TABLE_USER="USER";
    private static final String COLUMN_USER_NAME="NAME";
    private static final String COLUMN_USER_PASSWD="PASSWD";
    private static final String COLUMN_USER_EMAIL="EMAIL";
    //3. bang account
    private static final String TABLE_ACCOUNT ="ACCOUNT";
    private static final String COLUMN_ACCOUNT_NAME="NAME";
    private static final String COLUMN_ACCOUNT_EMAIL ="EMAIL";
    private static final String COLUMN_ACCOUNT_BIO="BIO";
    private static final String COLUMN_ACCOUNT_ID="ID";
    //4. bang notepage
    private static final String TABLE_NOTEPAGE="NOTEPAGE";
    private static final String COLUMN_NOTEP_ACCOUNT_ID="ID";
    private static final String COLUMN_NOTEP_TOTAL="TOTAL";
    private static final String COLUMN_NOTEP_NOTE_ID="NOTE_ID";
    //5. bang group
    //private static final String TABLE_GROUP="GROUP";
    //private static final String COLUMN_GROUP_ACCOUNT_ID="ACCOUNT_ID";
    //private static final String COLUMN_GROUP_NAME="GROUP_NAME";
    //private static final String COLUMN_GROUP_ID="GROUP_ID";
    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //1. bang note
        String createtablenote ="CREATE TABLE NOTE"+ TABLE_NOTES+"("+
        COLUMN_NOTE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COLUMN_NOTE_TITLE+" TEXT, "+
        COLUMN_NOTE_CONTENT+" TEXT, "+
        COLUMN_NOTE_DATE+" DATETIME DEFAULT CURRENT_TIMESTAMP"+")";

        //2.bang user
        String createtableuser = "CREATE TABLE USER"+ TABLE_USER+"("+
        COLUMN_USER_EMAIL+" TEXT PRIMARY KEY, "+
        COLUMN_USER_NAME+" TEXT, "+
        COLUMN_USER_PASSWD+" TEXT"+")";

        //3.bang accoount
        String createtableaccount ="CREATE TABLE ACCOUNT"+ TABLE_ACCOUNT+"("+
        COLUMN_ACCOUNT_ID+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+//tự động id
        COLUMN_ACCOUNT_NAME+" TEXT, "+
        COLUMN_ACCOUNT_EMAIL+" TEXT, "+
        COLUMN_ACCOUNT_BIO+" TEXT "+")";

        //4.bang notepage
        String createtablenotepage="CREATE TABLE NOTEPAGE"+TABLE_NOTEPAGE+"("+
        COLUMN_NOTEP_NOTE_ID+" INTEGER PRIMARY KEY, "+ COLUMN_NOTEP_ACCOUNT_ID+" TEXT, "+
        COLUMN_NOTEP_TOTAL+" INTEGER "+")";

        //5.bang group
        /*String createtablegroup="CREATE TABLE GROUP"+TABLE_GROUP+"("+
        COLUMN_GROUP_ID+"INTEGER PRIMARY KEY,"+
        COLUMN_GROUP_NAME+"TEXT,"+
        COLUMN_GROUP_ACCOUNT_ID+"INTEGER"+")";*/

        db.execSQL(createtablenote);
        db.execSQL(createtableuser);
        db.execSQL(createtableaccount);
        db.execSQL(createtablenotepage);
        //db.execSQL(createtablegroup);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //xử lý khi cập nhật phiên bản database
        if(newVersion == oldVersion) return;
        String drop_table= String.format("DROP TABLE IF EXIST %s", TABLE_NOTES);
        db.execSQL(drop_table);
        onCreate(db);
    }

    /*
    // Adding new note from full note*/
    public void addNote(Note note) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TITLE, note.getNote_title());
        values.put(COLUMN_NOTE_DATE, note.getNote_day());
        values.put(COLUMN_NOTE_CONTENT, note.getNote_content());

        db.insert(TABLE_NOTES, null, values);
        db.close();
    }
    // Getting single note from id*/
    public Note getNote(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTES, null, COLUMN_NOTE_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if(cursor!=null)
            cursor.moveToFirst();
        Note note = new Note(cursor.getInt(0), cursor.getString(0), cursor.getString(0),cursor.getString(0));
        return note;
    }//id: note_id

    // Getting All Note
    public List<Note> getAllNotes() {
        List<Note> noteList = new ArrayList<>();
        String query = "SELECT * FROM " +TABLE_NOTES;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() ==false){
            Note note = new Note(cursor.getInt(0),
                    cursor.getString(0),
                    cursor.getString(0),
                    cursor.getString(0));
            noteList.add(note);
            cursor.moveToNext();
        }
        return noteList;
    }
    /*

    // Updating single note from full note
    public int updateContact(Contact contact) {}
    // Deleting single note from full note
    public void deleteContact(Contact contact) {}
    */
}
