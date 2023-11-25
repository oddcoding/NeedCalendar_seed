package com.example.needcalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "YourDatabaseName.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_NAME = "your_table_name";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_PLACE = "place";
    public static final String COLUMN_MEMO = "memo";

    public static final String COLUMN_STARTDATE = "startDate";
    public static final String COLUMN_ENDDATE = "endDate";



    private static final String DATABASE_CREATE =
            "CREATE TABLE your_table_name (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title TEXT," +
                    "place TEXT," +
                    "memo TEXT," +
                    "startDate TEXT," +
                    "endDate TEXT" +
                    ");";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    /**
     * 수정
     */
    public long insertData(String title, String place, String memo, String startDate, String endDate) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_PLACE, place);
        values.put(COLUMN_MEMO, memo);
        values.put(COLUMN_STARTDATE, startDate);    // 추가
        values.put(COLUMN_ENDDATE, endDate);        // 추가

        // 데이터를 데이터베이스에 추가하고 결과를 반환합니다.
        return db.insert(TABLE_NAME, null, values);
    }

    //데이터 수정
    public long updateData(String title, String place, String memo) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_PLACE, place);
        values.put(COLUMN_MEMO, memo);

        // 데이터를 특정 ID 값에 해당하는 레코드에만 업데이트합니다.
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(title)});
    }

    //DELETE문(할일 목록을 제거 한다.)
    public long deleteData(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = DatabaseHelper.COLUMN_ID + " = ?";
        String[] whereArgs = {String.valueOf(id)};
        int deletedRows = db.delete(DatabaseHelper.TABLE_NAME, whereClause, whereArgs);


        // 삭제된 항목의 ID를 반환합니다.
        if (deletedRows > 0) {
            return id;
        } else {
            return -1;
        }

    }


    public List<ListItem> getAllItems() {
        List<ListItem> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_PLACE, COLUMN_MEMO, COLUMN_STARTDATE, COLUMN_ENDDATE};

        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        Log.d("CursorDebug", "COLUMN_ID Index: " + cursor.getColumnIndex(COLUMN_ID));
        Log.d("CursorDebug", "COLUMN_TITLE Index: " + cursor.getColumnIndex(COLUMN_TITLE));
        Log.d("CursorDebug", "COLUMN_PLACE Index: " + cursor.getColumnIndex(COLUMN_PLACE));
        Log.d("CursorDebug", "COLUMN_MEMO Index: " + cursor.getColumnIndex(COLUMN_MEMO));


        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
            String place = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PLACE));
            String memo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MEMO));
            String startDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STARTDATE));
            String endDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ENDDATE));

            ListItem item = new ListItem(id, title, place, memo, startDate, endDate);
            items.add(item);
        }

        cursor.close();
        return items;
    }

}