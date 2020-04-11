package com.example.myattendanceapp.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AttendanceDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "attendance.db";
    public static final String TABLE_NAME ="attendance";
    public static final String COL_1 ="ID";
    public static final String COL_2 ="name";
    public static final String COL_3 ="email";
    public static final String COL_4 ="date";
    public static final String COL_5 ="status";

    SQLiteDatabase db;
    public AttendanceDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE attendance (ID INTEGER PRIMARY  KEY AUTOINCREMENT, name TEXT, email TEXT, date TEXT, status TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean markAttendance(String name,String email, String date, String status){

        if(checkAttendance(email, date))
        {
            return false;
        }
        else {
             db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("email", email);
            contentValues.put("date", date);
            contentValues.put("status", status);
            long res = db.insert(TABLE_NAME, null, contentValues);
            db.close();
            if(!(res > 0))
            {
                return false;
            }
        }
        return  true;
    }

    public boolean checkAttendance(String email, String date){
        String[] columns = { COL_1 };
         db = getReadableDatabase();

        String selection = COL_3 + "=?" + " and " + COL_4 + "=?";
        String[] selectionArgs = { email, date };
        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return  true;
        else
            return  false;
    }

    public ArrayList<Attendance_List> getAllAttendance(String date)
    {
        ArrayList<Attendance_List> lists = new ArrayList<Attendance_List>();
        String[] columnstoget = {COL_2, COL_3, COL_5};
        db = getReadableDatabase();

        String selection = COL_4 + "=?";
        String selctionArgs[] = {date};

        Cursor cursor = db.query(TABLE_NAME,columnstoget,selection,selctionArgs,null,null,COL_5);
            if(cursor!=null)
            {
                cursor.moveToFirst();
                while (!cursor.isAfterLast())
                {
                    Attendance_List attendance_list = new Attendance_List();

                    attendance_list.setName(cursor.getString(cursor.getColumnIndex(COL_2)));

                    attendance_list.email = cursor.getString(cursor.getColumnIndex(COL_3));

                    attendance_list.status = cursor.getString(cursor.getColumnIndex(COL_5));

                    lists.add(attendance_list);
                    cursor.moveToNext();
                }
            }

        cursor.close();
        db.close();
        if(lists.size()>=0) {
            return lists;
        }
        else
        {
            lists.clear();
        }
        return lists;
    }
}

