package io.github.ssternklar.ssms;

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
    }
}