package com.example.needcalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class DBcheck extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = ".db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "check_table_name";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_PLACE = "place";
    public static final String COLUMN_MEMO = "memo";
    public static final String COLUMN_IS_CHECKED = "isChecked";



    private static final String DATABASE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_PLACE + " TEXT, " +
                    COLUMN_MEMO + " TEXT, " +
                    COLUMN_IS_CHECKED + " INTEGER" + // 쉼표 추가
                    ");";


    public DBcheck(Context context) {
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


    public long insertData(String title, String place, String memo, boolean isChecked) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_PLACE, place);
        values.put(COLUMN_MEMO, memo);
        values.put(COLUMN_IS_CHECKED, isChecked ? 1 : 0);

        return db.insert(TABLE_NAME, null, values);
    }


    public long updateData(String title, String place, String memo, boolean isChecked) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_PLACE, place);
        values.put(COLUMN_MEMO, memo);

        values.put(COLUMN_IS_CHECKED, isChecked ? 1 : 0);

        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(title)});
    }


    public long deleteData(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = DBcheck.COLUMN_ID + " = ?";
        String[] whereArgs = {String.valueOf(id)};
        int deletedRows = db.delete(DBcheck.TABLE_NAME, whereClause, whereArgs);



        if (deletedRows > 0) {
            return id;
        } else {
            return -1;
        }

    }

    public List<ListCheck> getAllItems() {
        List<ListCheck> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_PLACE, COLUMN_MEMO, COLUMN_IS_CHECKED};

        String orderBy = COLUMN_IS_CHECKED + " DESC"; // isChecked 값이 1인 것을 우선 정렬
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, orderBy);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
            String place = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PLACE));
            String memo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MEMO));
            int isChecked = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_CHECKED));

            ListCheck item = new ListCheck(id, title, place, memo, isChecked);
            items.add(item);


            if (isChecked == 1) {

                item.setBackgroundColor(Color.GREEN);
            }

        }

        cursor.close();
        return items;
    }


}
