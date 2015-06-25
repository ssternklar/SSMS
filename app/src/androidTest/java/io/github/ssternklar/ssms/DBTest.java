package io.github.ssternklar.ssms;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Debug;
import android.test.AndroidTestCase;
import android.util.Log;

public class DBTest extends AndroidTestCase {
    @Override
    protected  void setUp() throws Exception{
        super.setUp();
        mContext.deleteDatabase("ssms.db");
    }

    public  void  test() throws Throwable
    {
        SQLiteDatabase db = new SSMSDbOpenHelper(this.mContext).getWritableDatabase();
        assertTrue("Error, The database isnt open yet!", db.isOpen() && !db.isReadOnly());
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        assertTrue("ERROR, Apparently I failed", c.moveToFirst());
        c = db.rawQuery("PRAGMA table_info(" + SSMSEntry.TABLE_NAME + ")",null);
        assertTrue("Oh no we can't query anything", c.moveToFirst());
        int numOfStuff = 0;
        do{
            numOfStuff++;

        }while(c.moveToNext());
        assertTrue("WE DON'T HAVE THE RIGHT NUMBER OF STUFF", numOfStuff == 4);
        long index = SSMSDbHelper.insertNewPerson(db, "John Smith", "1234567890", "noooooooo");
        SSMSDbHelper.setPhoneNumberAtName(db, "John Smith", "0987654321");
        assertTrue("Error, phone number set fail!", SSMSDbHelper.getPhoneNumberFromName(db, "John Smith").equals("0987654321"));

    }
}
