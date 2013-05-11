package com.github.tasubo.jgmp;

public final class SystemInfo implements Decorating {

    private final Parametizer parametizer;

    private SystemInfo(Parametizer parametizer) {
        this.parametizer = parametizer;
    }

    public static SystemInfoBuilder with() {
        return new SystemInfoBuilder();
    }

    @Override
    public String getPart() {
        return parametizer.getText();
    }

    public final static class SystemInfoBuilder {

        private final Integer srx;
        private final Integer sry;
        private final Integer vpx;
        private final Integer vpy;
        private final String encoding;
        private final String language;
        private final String javaEnabled;
        private final String flashVersion;
        private final Integer bits;

        private SystemInfoBuilder(Integer srx, Integer sry, Integer vpx, Integer vpy, String encoding,
                                 String language, String javaEnabled, String flashVersion, Integer bits) {
            this.srx = srx;
            this.sry = sry;
            this.vpx = vpx;
            this.vpy = vpy;
            this.encoding = encoding;
            this.language = language;
            this.javaEnabled = javaEnabled;
            this.flashVersion = flashVersion;
            this.bits = bits;
        }

        private SystemInfoBuilder() {
            srx = null;
            sry = null;
            vpx = null;
            vpy = null;
            encoding = null;
            language = null;
            javaEnabled = null;
            flashVersion = null;
            bits = null;
        }

        public SystemInfoBuilder screenResolution(int srx, int sry) {
            return new SystemInfoBuilder(srx, sry, vpx, vpy, encoding, language, javaEnabled, flashVersion, bits);
        }

        public SystemInfoBuilder viewport(int vpx, int vpy) {
            return new SystemInfoBuilder(srx, sry, vpx, vpy, encoding, language, javaEnabled, flashVersion, bits);
        }

        public SystemInfoBuilder documentEncoding(String encoding) {
            Ensure.length(20, encoding);
            return new SystemInfoBuilder(srx, sry, vpx, vpy, encoding, language, javaEnabled, flashVersion, bits);
        }

        public SystemInfoBuilder colorBits(int bits) {
            return new SystemInfoBuilder(srx, sry, vpx, vpy, encoding, language, javaEnabled, flashVersion, bits);
        }

        public SystemInfoBuilder userLanguage(String language) {
            Ensure.length(20, language);
            return new SystemInfoBuilder(srx, sry, vpx, vpy, encoding, language, javaEnabled, flashVersion, bits);
        }

        public SystemInfoBuilder javaEnabled() {
            String javaEnabled = "1";
            return new SystemInfoBuilder(srx, sry, vpx, vpy, encoding, language, javaEnabled, flashVersion, bits);
        }

        public SystemInfoBuilder flashVersion(String flashVersion) {
            Ensure.length(20, flashVersion);
            return new SystemInfoBuilder(srx, sry, vpx, vpy, encoding, language, javaEnabled, flashVersion, bits);
        }

        public SystemInfo create() {
            String depth = null;
            String screeenResolution = null;
            String viewPort = null;
            if (srx != null && sry != null) {
                screeenResolution = "" + srx + "x" + sry;
            }

            if (vpx != null && vpy != null) {
                viewPort = "" + vpx + "x" + vpy;
            }

            if (bits != null) {
                depth = bits + "-bits";
            }
            Parametizer parametizer1 = new Parametizer("sr", screeenResolution, "vp", viewPort,
                    "de", encoding, "sd", depth, "ul", language, "je", javaEnabled, "fl", flashVersion);
            SystemInfo systemInfo = new SystemInfo(parametizer1);

            return systemInfo;
        }
    }
}
