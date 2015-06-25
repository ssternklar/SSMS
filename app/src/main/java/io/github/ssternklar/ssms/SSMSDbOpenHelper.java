package io.github.ssternklar.ssms;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SSMSDbOpenHelper extends SQLiteOpenHelper {
    private final static int DB_VERSION = 1;

    public SSMSDbOpenHelper(Context context)
    {
        super(context, "ssms.db", null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String SQL_CREATE_TABLE = "CREATE TABLE " + SSMSEntry.TABLE_NAME + "(" +
                SSMSEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                SSMSEntry.COLUMN_USER_NAME + " NVARCHAR(4000) UNIQUE NOT NULL, " +
                //This one can be null, after all we don't want a default phone number, right?
                SSMSEntry.COLUMN_PHONE_NUMBER + " NVARCHAR(15) NOT NULL, " +
                SSMSEntry.COLUMN_ENCRYPT_KEY + " NVARCHAR(800) NOT NULL " +
                " );";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int old, int newer)
    {
        //Proper handling of this is left as as exercise to the programmer.
        //Future people, if you are ever looking at this, I deeply apologize.
        //I do not have enough database knowledge to know how to gracefully handle this
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SSMSEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
