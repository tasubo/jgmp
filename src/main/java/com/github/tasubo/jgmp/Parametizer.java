package com.github.tasubo.jgmp;

class Parametizer {
    private final String paramValue;


    public Parametizer(Object... params) {
        if (params.length % 2 != 0) {
            throw new IllegalArgumentException("Param and value count should match");
        }

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < params.length; i += 2) {
            if (params[i] == null || params[i + 1] == null) {
                continue;
            }

            builder.append("&")
                    .append(params[i])
                    .append("=")
                    .append(encode(params[i + 1]));
        }

        paramValue = builder.toString();
    }

    private static String encode(Object param) {

        return URLParamEncoder.encode(param.toString());

    }

    public String getText() {
        return paramValue;
    }

}
