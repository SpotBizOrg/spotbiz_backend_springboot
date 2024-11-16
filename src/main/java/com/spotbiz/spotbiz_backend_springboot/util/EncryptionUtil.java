package com.spotbiz.spotbiz_backend_springboot.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class EncryptionUtil {

    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = ALPHABET.length();

    private static final Map<String, String> codeToIdMap = new HashMap<>();

    private static final Map<String, String> idToCodeMap = new HashMap<>();

    public static String encodeCouponId(String couponId) throws NoSuchAlgorithmException {
        if (idToCodeMap.containsKey(couponId)) {
            return idToCodeMap.get(couponId);
        }

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(couponId.getBytes());

        BigInteger hashNumber = new BigInteger(1, hash);

        StringBuilder encoded = new StringBuilder();
        while (hashNumber.compareTo(BigInteger.ZERO) > 0) {
            int remainder = hashNumber.mod(BigInteger.valueOf(BASE)).intValue();
            hashNumber = hashNumber.divide(BigInteger.valueOf(BASE));
            encoded.append(ALPHABET.charAt(remainder));
        }

        String result = encoded.reverse().toString();
        String finalCode = result.length() > 6 ? result.substring(0, 6) : result;

        codeToIdMap.put(finalCode, couponId);
        idToCodeMap.put(couponId, finalCode);

        return finalCode;
    }

    public static String decodeCouponId(String couponCode) {
        return codeToIdMap.get(couponCode);
    }

}