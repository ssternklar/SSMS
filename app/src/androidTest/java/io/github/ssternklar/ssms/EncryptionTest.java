package io.github.ssternklar.ssms;

import android.test.AndroidTestCase;

/**
 * Created by Sam on 6/28/2015.
 */
public class EncryptionTest extends AndroidTestCase {

    public void test() throws Throwable
    {
        String raw = "Hello, \"\" and welcome to SSMS!";
        String key = "1234567890-=!@#$%^&*()_+`~QWERTYUIOP{}|qwertyuiop[]\\ASDFGHJKL:?asdfghjkl;'ZXCVBNM<>?zxcvbnm,./";
        String encrypted = SSMSEncryptHelper.Encrypt(raw, key);
        assertFalse("Encryption is failing!", raw.equals(encrypted));
        String decrypted = SSMSEncryptHelper.Decrypt(encrypted, key);
        assertTrue("Decryption fails!", decrypted.equals(raw));
    }

}
