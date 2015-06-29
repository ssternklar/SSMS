package io.github.ssternklar.ssms;

public final class SSMSEncryptHelper {

    public String Encrypt(String toEncrypt, String key)
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
    public String Decrypt(String toDecrypt, String key)
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
                        c = key.charAt(j);
                        break;
                    }
                }

                newChars[i] = c;
            }
            else
            {
                newChars[i] = chars[i];
            }
        }

        return new String(newChars);
    }

}
