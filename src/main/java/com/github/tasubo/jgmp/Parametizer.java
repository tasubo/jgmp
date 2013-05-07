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

    public Parametizer and(Parametizer parametizer, String... paramsToSkip) {
        String localParams = this.getText();
        String foreignParams = parametizer.getText();

        String[] pairs = foreignParams.substring(1).split("&");
        if (pairs.length % 2 != 0) {
            throw new IllegalArgumentException("All params should have values");
        }

        StringBuilder builder = new StringBuilder();

        for (int j = 0; j < pairs.length; j++) {
            String[] pair = pairs[j].split("=");
            String paramName = pair[0];
            String paramValue = pair[1];

            if (isInSkipList(paramName, paramsToSkip)) {
                continue;
            }

            if (localParams.contains(paramName)) {
                throw new IllegalArgumentException("Cannot join same params - revisit parts you are combining");
            }

            builder.append("&")
                    .append(paramName)
                    .append("=")
                    .append(paramValue);

        }

        return new Parametizer(localParams + builder.toString());
    }

    private boolean isInSkipList(String paramName, String[] paramsToSkip) {
        for (int i = 0; i < paramsToSkip.length; i++) {
            if (paramName.contains(paramsToSkip[i])) {
                return true;
            }
        }

        return false;
    }

    private static String encode(Object param) {

        return URLParamEncoder.encode(param.toString());

    }

    public String getText() {
        return paramValue;
    }

}
