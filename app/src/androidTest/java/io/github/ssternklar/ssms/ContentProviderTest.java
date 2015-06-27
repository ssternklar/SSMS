package io.github.ssternklar.ssms;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

public class ContentProviderTest extends AndroidTestCase {

    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        mContext.deleteDatabase("ssms.db");
    }

    public void test() throws Throwable
    {
        //TODO: UNIT TEST THE CONTENT PROVIDER
        Context context = getContext();
        SSMSContentProviderHelper.insertNewPerson(context, "John", "1234567890", "151515");
        assertTrue("Something went wrong fetching the phone number from content provider!", SSMSContentProviderHelper.getPhoneNumber(context, "John").equals("1234567890"));
        assertTrue("Something went wrong fetching the encrypt key from content provider! ", SSMSContentProviderHelper.getEncryptKey(context, "John").equals("151515"));
        SSMSContentProviderHelper.insertNewPerson(context, "John", "0987654321", "515151");
        assertTrue("Somehow we subverted the ability to update our contacts!", SSMSContentProviderHelper.getPhoneNumber(context, "John").equals("0987654321"));
        SSMSContentProviderHelper.insertNewPerson(context, "Jane Doe", "1", "1");
        SQLiteDatabase db = new SSMSDbOpenHelper(context).getWritableDatabase();
        assertTrue("DB and Content Provider are not at full parity!", SSMSDbHelper.getEncryptKeyFromName(db, "Jane Doe").equals("1"));
        SSMSContentProviderHelper.updateAllPossible(context, "Jane Doe", "1800IMATEST", "ENCRYPTME");
        assertTrue("Update fails!", SSMSContentProviderHelper.getPhoneNumber(context, "Jane Doe").equals("1800IMATEST"));
        SSMSContentProviderHelper.deleteRecord(context, "Jane Doe");
        assertTrue("Delete fails!", SSMSContentProviderHelper.getPhoneNumber(context, "Jane Doe").equals(""));
        assertFalse("Delete deletes too much!", SSMSContentProviderHelper.getPhoneNumber(context, "John").equals(""));
    }
}