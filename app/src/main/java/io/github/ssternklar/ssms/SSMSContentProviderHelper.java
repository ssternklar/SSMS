package io.github.ssternklar.ssms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import java.net.URI;

public final class SSMSContentProviderHelper {

    public static String getPhoneNumber(Context context, String name)
    {
        Cursor c = context.getContentResolver().query(SSMSContract.BASE_URI.buildUpon().appendPath(SSMSContract.COLUMN_PHONE_NUMBER + "/" + name).build(), new String[]{SSMSContract.COLUMN_PHONE_NUMBER}, SSMSContract.COLUMN_USER_NAME + "=\"" + name + "\"", null, null);
        if(!c.moveToFirst())
        {
            c.close();
            return "";
        }
        c.close();
        return c.getString(0);
    }

    public static String getEncryptKey(Context context, String name)
    {
        Cursor c = context.getContentResolver().query(SSMSContract.BASE_URI.buildUpon().appendPath(SSMSContract.COLUMN_ENCRYPT_KEY + "/" + name).build(), new String[]{SSMSContract.COLUMN_ENCRYPT_KEY}, SSMSContract.COLUMN_USER_NAME + "=\"" + name + "\"", null, null);
        if(!c.moveToFirst())
        {
            c.close();
            return "";
        }
        c.close();
        return c.getString(0);
    }

    public static Uri insertNewPerson(Context context, String name, String number, String key)
    {
        ContentValues values = new ContentValues();
        values.put(SSMSContract.COLUMN_PHONE_NUMBER, number);
        values.put(SSMSContract.COLUMN_ENCRYPT_KEY, key);

        return context.getContentResolver().insert(SSMSContract.BASE_URI.buildUpon().appendPath(name).build(), values);
    }

    public static void setPhoneNumber(Context context, String name, String number)
    {
        ContentValues values = new ContentValues();
        values.put(SSMSContract.COLUMN_PHONE_NUMBER, number);
        context.getContentResolver().update(SSMSContract.BASE_URI.buildUpon().appendPath(SSMSContract.COLUMN_PHONE_NUMBER + "/" + name).build(), values, null, null);
    }

    public static void setEncryptKey(Context context, String name, String key)
    {
        ContentValues values = new ContentValues();
        values.put(SSMSContract.COLUMN_ENCRYPT_KEY, key);
        context.getContentResolver().update(SSMSContract.BASE_URI.buildUpon().appendPath(SSMSContract.COLUMN_ENCRYPT_KEY + "/" + name).build(), values, null, null);
    }

    public static void updateAllPossible(Context context, String name, String number, String key)
    {
        ContentValues values = new ContentValues();
        values.put(SSMSContract.COLUMN_PHONE_NUMBER, number);
        values.put(SSMSContract.COLUMN_ENCRYPT_KEY, key);
        context.getContentResolver().update(SSMSContract.BASE_URI.buildUpon().appendPath(name).build(), values, null, null);
    }

    public static void deleteRecord(Context context, String name)
    {
        context.getContentResolver().delete(SSMSContract.BASE_URI.buildUpon().appendPath(name).build(), null, null);
    }
}
