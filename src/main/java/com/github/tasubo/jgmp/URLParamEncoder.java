package com.github.tasubo.jgmp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

class URLParamEncoder {

    public static String encode(String input) {
        try {
            String encode = URLEncoder.encode(input, "UTF-8");
            encode = encode.replace("+", "%20");
            return encode;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
