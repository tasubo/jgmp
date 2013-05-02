package com.github.tasubo.jgmp;

public class Campaign implements Decorating {

    private String name;
    private String source;
    private String displayAdsId;
    private String medium;
    private String keyword;
    private String content;
    private String id;
    private String adWordsId;
    private CombinedSendable combinedSendable;

    private Campaign() {
    }

    public Sendable with(Sendable sendable) {
        combinedSendable = new CombinedSendable("cn", name, "cs", source, "cm", medium, "ck", keyword, "cc", content,
                "ci", id, "gclid", adWordsId, "dclid", displayAdsId);
        return combinedSendable.with(sendable);
    }

    public static CampaignBuilder named(String name) {
        return new CampaignBuilder(name);
    }

    public static class CampaignBuilder {

        private String name;
        private String source;
        private String displayAdsId;
        private String medium;
        private String keyword;
        private String content;
        private String id;
        private String adWordsId;

        private CampaignBuilder(String name) {
            this.name = name;
        }

        public CampaignBuilder source(String source) {
            this.source = source;
            return this;
        }

        public CampaignBuilder medium(String medium) {
            this.medium = medium;
            return this;
        }

        public CampaignBuilder keyword(String keyword) {
            this.keyword = keyword;
            return this;
        }

        public CampaignBuilder content(String content) {
            this.content = content;
            return this;
        }

        public CampaignBuilder id(String id) {
            this.id = id;
            return this;
        }

        public CampaignBuilder adWordsId(String adWordsId) {
            this.adWordsId = adWordsId;
            return this;
        }

        public CampaignBuilder displayAdsId(String displayAdsId) {
            this.displayAdsId = displayAdsId;
            return this;
        }

        public Campaign create() {
            Campaign campaign = new Campaign();
            campaign.name = name;
            campaign.id = id;
            campaign.source = source;
            campaign.displayAdsId = displayAdsId;
            campaign.medium = medium;
            campaign.keyword = keyword;
            campaign.content = content;
            campaign.adWordsId = adWordsId;
            return campaign;
        }
    }
}
