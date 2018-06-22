package com.exama.exam.utils;

import android.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CredentialsUtils {
    public final static String ts = "1";
    public final static String public_key = "ae97102077b1811aba0b9d276b226b9f";
    private final static String private_key = "ee47579f7d0d21bffaae33e6d68b4fc202b14b01";

    public static String getHash(){
        String hash = ts+private_key+public_key;
        Log.i("CredentialsUtils",hash);
        return md5(hash);
    }

    private static String md5(String s) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest)
                hexString.append(Integer.toHexString(0xFF & aMessageDigest));

            return hexString.toString();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}