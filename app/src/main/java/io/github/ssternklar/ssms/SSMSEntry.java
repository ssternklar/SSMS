package io.github.ssternklar.ssms;


import android.provider.BaseColumns;

public final class SSMSEntry implements BaseColumns
{
    public final static String TABLE_NAME="ssms";
    public final static String COLUMN_ID ="_id";
    public final static String COLUMN_USER_NAME = "user_name";
    public final static String COLUMN_PHONE_NUMBER = "phone_number";
    public final static String COLUMN_ENCRYPT_KEY="encrypt_key";
}
