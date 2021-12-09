package com.yuanbaogo.datacenter.remote;

public class RequestConfiguration {
    private boolean mShowProgress;

    private RequestConfiguration() {
    }

    public boolean isShowProgress() {
        return this.mShowProgress;
    }

    public void setShowProgress(boolean mShowProgress) {
        this.mShowProgress = mShowProgress;
    }

    public static class Builder {
        private boolean mShowProgress;

        public Builder() {
        }

        public RequestConfiguration build() {
            RequestConfiguration configuration = new RequestConfiguration();
            configuration.setShowProgress(this.mShowProgress);
            return configuration;
        }

        public RequestConfiguration.Builder setShowProgress(boolean mShowProgress) {
            this.mShowProgress = mShowProgress;
            return this;
        }
    }
}
