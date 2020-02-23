package com.devnovikov.keepapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Objects;

public class DatabaseHelper extends SQLiteOpenHelper {

    DatabaseHelper(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    void insertInfo(String title, String subTitle, String image, String addTimeStamp, String updateTimeStamp) {

        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constants.C_TITLE, title);
            values.put(Constants.C_SUBTITLE, subTitle);
            values.put(Constants.C_IMAGE, image);
            values.put(Constants.C_ADD_TIMESTAMP, addTimeStamp);
            values.put(Constants.C_UPDATE_TIMESTAMP, updateTimeStamp);

            long id = db.insert(Constants.TABLE_NAME, null, values);
        } catch (Exception ex) {
            Log.d("DatabaseHelper", ex.getMessage());
        } finally {
            Objects.requireNonNull(db).close();
        }
    }

    void updateInfo(String id, String title, String subTitle, String image, String addTimeStamp, String updateTimeStamp) {

        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constants.C_TITLE, title);
            values.put(Constants.C_SUBTITLE, subTitle);
            values.put(Constants.C_IMAGE, image);
            values.put(Constants.C_ADD_TIMESTAMP, addTimeStamp);
            values.put(Constants.C_UPDATE_TIMESTAMP, updateTimeStamp);

            db.update(Constants.TABLE_NAME, values, Constants.C_ID + " = ?", new String[]{id});
        } catch (Exception ex) {
            Log.d("DatabaseHelper", ex.getMessage());
        } finally {
            Objects.requireNonNull(db).close();
        }
    }

    void deleteInfo(String id) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            db.delete(Constants.TABLE_NAME, Constants.C_ID + " = ? ", new String[]{id});
        } catch (Exception ex) {
            Log.d("DatabaseHelper", ex.getMessage());
        } finally {
            Objects.requireNonNull(db).close();
        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }

    ArrayList<Note> getAllData(String orderBy) {
        ArrayList<Note> arrayList = null;
        SQLiteDatabase db = null;

        try {
            arrayList = new ArrayList<>();

            //query for select all info in database
            String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY " + orderBy;

            db = this.getWritableDatabase();
            Cursor cursor = Objects.requireNonNull(db).rawQuery(selectQuery, null);

            //when we select all info from database get the data from columns

            if (cursor.moveToNext()) {
                do {
                    Note note = new Note(
                            "" + cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                            "" + cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)),
                            "" + cursor.getString(cursor.getColumnIndex(Constants.C_TITLE)),
                            "" + cursor.getString(cursor.getColumnIndex(Constants.C_SUBTITLE)),
                            "" + cursor.getString(cursor.getColumnIndex(Constants.C_ADD_TIMESTAMP)),
                            "" + cursor.getString(cursor.getColumnIndex(Constants.C_UPDATE_TIMESTAMP))
                    );

                    Objects.requireNonNull(arrayList).add(note);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.d("DatabaseHelper", ex.getMessage());
        } finally {
            Objects.requireNonNull(db).close();
        }


        return arrayList;
    }
}
