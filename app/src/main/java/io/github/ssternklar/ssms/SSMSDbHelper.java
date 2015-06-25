package io.github.ssternklar.ssms;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public final class SSMSDbHelper {
    public static String getPhoneNumberFromName(SQLiteDatabase db, String name)
    {
        Cursor c = db.rawQuery("SELECT " + SSMSContract.COLUMN_PHONE_NUMBER + " FROM " + SSMSContract.TABLE_NAME + " WHERE " + SSMSContract.COLUMN_USER_NAME + "=\"" + name+"\"", null);
        if(!c.moveToFirst()) return "";
        return c.getString(0);
    }

    public static String getEncryptKeyFromName(SQLiteDatabase db, String name)
    {
        Cursor c = db.rawQuery("SELECT " + SSMSContract.COLUMN_ENCRYPT_KEY + " FROM " + SSMSContract.TABLE_NAME + " WHERE " + SSMSContract.COLUMN_USER_NAME + "='" + name+"'", null);
        if(!c.moveToFirst()) return "";
        return c.getString(0);
    }

    public static long insertNewPerson(SQLiteDatabase db, String name, String number, String key)
    {
        ContentValues values = new ContentValues();
        values.put(SSMSContract.COLUMN_USER_NAME, name);
        values.put(SSMSContract.COLUMN_PHONE_NUMBER, number);
        values.put(SSMSContract.COLUMN_ENCRYPT_KEY, number);
        return db.insert(SSMSContract.TABLE_NAME, null, values);
    }

    public static void setPhoneNumberAtName(SQLiteDatabase db, String name, String number)
    {
        db.execSQL("UPDATE " + SSMSContract.TABLE_NAME + " SET " + SSMSContract.COLUMN_PHONE_NUMBER + "=\"" + number + "\" WHERE " + SSMSContract.COLUMN_USER_NAME + "=\"" + name + "\"");
    }

    public static void setEncryptKeyAtName(SQLiteDatabase db, String name, String key)
    {
        db.execSQL("UPDATE " + SSMSContract.TABLE_NAME + " SET " + SSMSContract.COLUMN_ENCRYPT_KEY + "=\"" + key + "\" WHERE " + SSMSContract.COLUMN_USER_NAME + "=\"" + name + "\"");
    }

    public static void updateAllPossible(SQLiteDatabase db, String name, String number, String key)
    {
        setPhoneNumberAtName(db, name, number);
        setEncryptKeyAtName(db, name, key);
    }
}
