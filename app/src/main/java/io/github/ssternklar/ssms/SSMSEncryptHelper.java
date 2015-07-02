package io.github.ssternklar.ssms;

public final class SSMSEncryptHelper {

    public static String Encrypt(String toEncrypt, String key)
    {
        char[] chars = toEncrypt.toCharArray();
        char[] newChars = new char[chars.length];
        for(int i = 0; i < chars.length; i++)
        {
            if(chars[i] > 31 && chars[i] < 240)
            {
                newChars[i] = key.charAt((int)chars[i] - 32);
            }
            else
            {
                newChars[i] = chars[i];
            }
        }
        return new String(newChars);
    }
    public static String Decrypt(String toDecrypt, String key)
    {
        char[] chars = toDecrypt.toCharArray();
        char[] newChars = new char[chars.length];
        for(int i = 0; i < chars.length; i++)
        {
            if(chars[i] > 31 && chars[i] < 240)
            {
                char c = '?';
                for(int j = 0; j < key.length(); j++)
                {
                    if(key.charAt(j) == chars[i])
                    {
                        c = (char)(j + 32);
                        break;
                    }
                }

                newChars[i] = c;
            }
            else if(chars[i] == 240)
            {
                newChars[i] = '"';
            }
            else
            {
                newChars[i] = chars[i];
            }
        }

        return new String(newChars);
    }

}
