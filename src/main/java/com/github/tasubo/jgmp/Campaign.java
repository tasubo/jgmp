package com.github.tasubo.jgmp;

public final class Campaign implements Decorating {

    private final CombinedSendable combinedSendable;

    private Campaign(CampaignBuilder b) {
        combinedSendable = new CombinedSendable("cn", b.name,
                "cs", b.source,
                "cm", b.medium,
                "ck", b.keyword,
                "cc", b.content,
                "ci", b.id,
                "gclid", b.adWordsId,
                "dclid", b.displayAdsId);
    }

    public Sendable with(Sendable sendable) {
        return combinedSendable.with(sendable);
    }

    public static CampaignBuilder named(String name) {
        return new CampaignBuilder(name);
    }

    public static final class CampaignBuilder {

        private final String name;
        private final String source;
        private final String displayAdsId;
        private final String medium;
        private final String keyword;
        private final String content;
        private final String id;
        private final String adWordsId;

        private CampaignBuilder(String name) {
            this.name = name;
            this.source = null;
            this.displayAdsId = null;
            this.medium = null;
            this.keyword = null;
            this.content = null;
            this.id = null;
            this.adWordsId = null;
        }

        public CampaignBuilder(String name, String source, String displayAdsId, String medium,
                               String keyword, String content, String id, String adWordsId) {
            this.name = name;
            this.source = source;
            this.displayAdsId = displayAdsId;
            this.medium = medium;
            this.keyword = keyword;
            this.content = content;
            this.id = id;
            this.adWordsId = adWordsId;
        }

        public CampaignBuilder source(String source) {
            return new CampaignBuilder(name, source, displayAdsId, medium, keyword, content, id, adWordsId);
        }

        public CampaignBuilder medium(String medium) {
            return new CampaignBuilder(name, source, displayAdsId, medium, keyword, content, id, adWordsId);
        }

        public CampaignBuilder keyword(String keyword) {
            return new CampaignBuilder(name, source, displayAdsId, medium, keyword, content, id, adWordsId);
        }

        public CampaignBuilder content(String content) {
            return new CampaignBuilder(name, source, displayAdsId, medium, keyword, content, id, adWordsId);
        }

        public CampaignBuilder id(String id) {
            return new CampaignBuilder(name, source, displayAdsId, medium, keyword, content, id, adWordsId);
        }

        public CampaignBuilder adWordsId(String adWordsId) {
            return new CampaignBuilder(name, source, displayAdsId, medium, keyword, content, id, adWordsId);
        }

        public CampaignBuilder displayAdsId(String displayAdsId) {
            return new CampaignBuilder(name, source, displayAdsId, medium, keyword, content, id, adWordsId);
        }

        public Campaign create() {
            Campaign campaign = new Campaign(this);
            return campaign;
        }
    }
}
