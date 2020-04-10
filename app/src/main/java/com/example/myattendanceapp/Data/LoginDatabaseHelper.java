package com.example.myattendanceapp.Data;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LoginDatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME ="register.db";
    public static final String TABLE_NAME ="usersignup";
    public static final String COL_1 ="ID";
    public static final String COL_2 ="name";
    public static final String COL_3 ="email";
    public static final String COL_4 ="password";

    public LoginDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE usersignup (ID INTEGER PRIMARY  KEY AUTOINCREMENT, name TEXT, email TEXT, password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addUser(String name,String user, String password){

        if(checkUser(user, password))
        {
            return false;
        }
        else {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("email", user);
            contentValues.put("password", password);
            long res = db.insert(TABLE_NAME, null, contentValues);
            db.close();
            if(!(res > 0))
            {
                return false;
            }
        }
        return  true;
    }

    public boolean checkUser(String email, String password){
        String[] columns = { COL_1 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_3 + "=?" + " and " + COL_4 + "=?";
        String[] selectionArgs = { email, password };
        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return  true;
        else
            return  false;
    }

    public String getUser (String email){

        String[] columns = { COL_2 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_3 + "=?";
        String[] selectionArgs = { email};
        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        cursor.moveToFirst();
        String name  = cursor.getString(cursor.getColumnIndex(COL_2));
        cursor.close();
        db.close();

        return name;
    }
}