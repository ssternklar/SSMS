package io.github.ssternklar.ssms;


import android.content.ContentResolver;
import android.net.Uri;

public final class SSMSContract
{

    public final static String CONTENT_AUTHORITY = "io.github.ssternklar.ssms.provider";
    public final static Uri BASE_URI = Uri.parse("content://" + CONTENT_AUTHORITY + "/");

    public final static String CONTENT_TYPE_DIR =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/";
    public final static String CONTENT_TYPE_ITEM =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/ssms";

    public final static String TABLE_NAME="ssms";
    public final static String COLUMN_ID ="_id";
    public final static String COLUMN_USER_NAME = "user_name";
    public final static String COLUMN_PHONE_NUMBER = "phone_number";
    public final static String COLUMN_ENCRYPT_KEY="encrypt_key";
}
