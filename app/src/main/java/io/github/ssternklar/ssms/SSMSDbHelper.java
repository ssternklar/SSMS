package io.github.ssternklar.ssms;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public final class SSMSDbHelper {
    public static String getPhoneNumberFromName(SQLiteDatabase db, String name)
    {
        Cursor c = db.rawQuery("SELECT " + SSMSEntry.COLUMN_PHONE_NUMBER + " FROM " + SSMSEntry.TABLE_NAME + " WHERE " + SSMSEntry.COLUMN_USER_NAME + "=\"" + name+"\"", null);
        if(!c.moveToFirst()) return "";
        return c.getString(0);
    }

    public static String getEncryptKeyFromName(SQLiteDatabase db, String name)
    {
        Cursor c = db.rawQuery("SELECT " + SSMSEntry.COLUMN_ENCRYPT_KEY + " FROM " + SSMSEntry.TABLE_NAME + " WHERE " + SSMSEntry.COLUMN_USER_NAME + "='" + name+"'", null);
        if(!c.moveToFirst()) return "";
        return c.getString(0);
    }

    public static long insertNewPerson(SQLiteDatabase db, String name, String number, String key)
    {
        ContentValues values = new ContentValues();
        values.put(SSMSEntry.COLUMN_USER_NAME, name);
        values.put(SSMSEntry.COLUMN_PHONE_NUMBER, number);
        values.put(SSMSEntry.COLUMN_ENCRYPT_KEY, number);
        return db.insert(SSMSEntry.TABLE_NAME, null, values);
    }

    public static void setPhoneNumberAtName(SQLiteDatabase db, String name, String number)
    {
        db.execSQL("UPDATE " + SSMSEntry.TABLE_NAME + " SET " + SSMSEntry.COLUMN_PHONE_NUMBER + "=\"" + number + "\" WHERE " + SSMSEntry.COLUMN_USER_NAME + "=\"" + name + "\"");
    }

    public static void setEncryptKeyAtName(SQLiteDatabase db, String name, String key)
    {
        db.execSQL("UPDATE " + SSMSEntry.TABLE_NAME + " SET " + SSMSEntry.COLUMN_ENCRYPT_KEY + "=\"" + key + "\" WHERE " + SSMSEntry.COLUMN_USER_NAME + "=\"" + name + "\"");
    }
}
