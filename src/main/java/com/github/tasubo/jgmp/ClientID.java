package com.github.tasubo.jgmp;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public final class ClientID implements Decorating {

    private final String clientID;

    private ClientID(String clientID) {
        this.clientID = new Parametizer("cid", clientID).getText();
    }

    /**
     * Useful to create deterministic UUIDs that are based on MD5.
     *
     * @param seeds You can supply here user IP or/and User-Agent to identify them
     * @return
     */
    public static ClientID seeded(String... seeds) {
        String text = "";
        for (String string : seeds) {
            if (string == null) {
                string = "";
            }
            text += string;
        }

        String hash = hash(text);
        String result = hash.substring(0, 8)
                + "-" + hash.substring(8, 12)
                + "-" + hash.substring(12, 16)
                + "-" + hash.substring(16, 20)
                + "-" + hash.substring(20, 32);
        return new ClientID(result);
    }

    public static ClientID random() {
        String randomUuid = UUID.randomUUID().toString();
        return new ClientID(randomUuid);
    }

    @Override
    public String getPart() {
        return clientID;
    }

    public static ClientID fromString(String clientId) {
        Ensure.isUuid(clientId);
        return new ClientID(clientId);
    }


    private static String hash(String string) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            String text = string;

            md.update(text.getBytes("UTF-8"));
            byte[] digest = md.digest();
            
            return toHex(digest);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private static final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();

    private static String toHex(byte[] data) {
        char[] chars = new char[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            chars[i * 2] = HEX_DIGITS[(data[i] >> 4) & 0xf];
            chars[i * 2 + 1] = HEX_DIGITS[data[i] & 0xf];
        }
        return new String(chars);
    }
}
