package com.github.tasubo.jgmp;

final class Parametizer {
    private final String paramValue;

    private Parametizer(String string) {
        paramValue = string;
    }

    public Parametizer(Object... params) {
        if (params.length % 2 != 0) {
            throw new IllegalArgumentException("Param and value count should match");
        }

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < params.length; i += 2) {
            Object paramName = params[i];
            Object paramValue = params[i + 1];

            if (paramName == null || paramValue == null) {
                continue;
            }

            builder.append("&")
                    .append(paramName)
                    .append("=")
                    .append(encode(paramValue));
        }

        paramValue = builder.toString();
    }

    public Parametizer and(Parametizer parametizer) {
        String localParams = this.getText();
        String foreignParams = parametizer.getText();

        String[] split = foreignParams.split("=");
        for (String part : split) {
            if (!part.startsWith("&")) {
                continue;
            }

            if (localParams.contains(part)) {
                throw new IllegalArgumentException("Cannot join same params - revisit parts you are combining");
            }
        }

        return new Parametizer(localParams + foreignParams);
    }

    private static String encode(Object param) {

        return URLParamEncoder.encode(param.toString());

    }

    public String getText() {
        return paramValue;
    }

}
