package com.github.tasubo.jgmp;

public class SystemInfo implements Decorating {

    private final Parametizer parametizer;

    private SystemInfo(Parametizer parametizer) {
        this.parametizer = parametizer;
    }

    @Override
    public Sendable with(Sendable sendable) {
        return new CombinedSendable(parametizer).with(sendable);
    }

    public static SystemInfoBuilder with() {
        return new SystemInfoBuilder();
    }

    public static class SystemInfoBuilder {

        private Integer srx;
        private Integer sry;
        private Integer vpx;
        private Integer vpy;
        private String encoding;
        private String language;
        private String javaEnabled;
        private String flashVersion;
        private Integer bits;

        private SystemInfoBuilder() {
        }

        public SystemInfoBuilder screenResolution(int x, int y) {
            this.srx = x;
            this.sry = y;
            return this;
        }

        public SystemInfoBuilder viewport(int x, int y) {
            this.vpx = x;
            this.vpy = y;
            return this;
        }

        public SystemInfoBuilder documentEncoding(String encoding) {
            this.encoding = encoding;
            return this;
        }

        public SystemInfoBuilder colorBits(int bits) {
            this.bits = bits;
            return this;
        }

        public SystemInfoBuilder userLanguage(String language) {
            this.language = language;
            return this;
        }

        public SystemInfoBuilder javaEnabled() {
            this.javaEnabled = "1";
            return this;
        }

        public SystemInfoBuilder flashVersion(String version) {
            this.flashVersion = version;
            return this;
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
