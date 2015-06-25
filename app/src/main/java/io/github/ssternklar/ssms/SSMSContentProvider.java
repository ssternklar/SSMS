package io.github.ssternklar.ssms;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class SSMSContentProvider extends ContentProvider {

    static final int ENTIRE_ENTRY = 0;
    static final int PHONE = 1;
    static final int KEY = 2;

    UriMatcher matcher = buildUriMatcher();
    SSMSDbOpenHelper openHelper;

    static UriMatcher buildUriMatcher()
    {
        //Set stuff up
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        String authority = SSMSContract.CONTENT_AUTHORITY;

        //Add our URI paths
        matcher.addURI(authority, "/*", ENTIRE_ENTRY);
        matcher.addURI(authority, "/" + SSMSContract.COLUMN_ENCRYPT_KEY + "/*", KEY);
        matcher.addURI(authority, "/" + SSMSContract.COLUMN_PHONE_NUMBER + "/*", PHONE);

        return matcher;
    }

    public static Uri getEntryUri(String name)
    {
        return Uri.withAppendedPath(SSMSContract.BASE_URI, name);
    }

    public static Uri getNumberUri(String name)
    {
        return Uri.withAppendedPath(SSMSContract.BASE_URI, SSMSContract.COLUMN_PHONE_NUMBER + "/" + name);
    }

    public static Uri getEncryptKeyUri(String name)
    {
        return Uri.withAppendedPath(SSMSContract.BASE_URI, SSMSContract.COLUMN_ENCRYPT_KEY + "/" + name);
    }

    @Override
    public boolean onCreate()
    {
        openHelper = new SSMSDbOpenHelper(getContext());
        return  true;
    }

    @Override
    public String getType(Uri uri)
    {
        int match = matcher.match(uri);
        switch (match)
        {
            case ENTIRE_ENTRY:
                return SSMSContract.CONTENT_TYPE_DIR;
            case KEY:
            case PHONE:
                return SSMSContract.CONTENT_TYPE_ITEM;
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri.toString());
        }
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs)
    {
        return 0;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues)
    {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        int match = matcher.match(uri);

        String name = contentValues.getAsString(SSMSContract.COLUMN_USER_NAME);
        String number = contentValues.getAsString(SSMSContract.COLUMN_PHONE_NUMBER);
        String key = contentValues.getAsString(SSMSContract.COLUMN_ENCRYPT_KEY);
        switch (match)
        {
            case ENTIRE_ENTRY:
                if(!SSMSDbHelper.getEncryptKeyFromName(db, name).equals(""))
                {
                    SSMSDbHelper.updateAllPossible(db, name, number, key);
                }
                else
                {
                    SSMSDbHelper.insertNewPerson(db, name, number, key);
                }
                notifyAll();
                return getEntryUri(name);
            default:
                throw new UnsupportedOperationException("We do not support adding people without a full entry");
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        int match = matcher.match(uri);
        switch(match)
        {
            case ENTIRE_ENTRY:
                //TODO: Finish ths thing
            default:
                throw new UnsupportedOperationException("We do not support deleting records in such a manner");
        }
    }
}
