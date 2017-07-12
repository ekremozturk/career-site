package com.ekrem.jsf.jdbc;

import com.github.scribejava.core.builder.api.DefaultApi20;

public class LinkedInAPI extends DefaultApi20 {

    protected LinkedInAPI() {
    }

    private static class InstanceHolder {
        private static final LinkedInAPI INSTANCE = new LinkedInAPI();
    }

    public static LinkedInAPI instance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return "https://www.linkedin.com/oauth/v2/accessToken";
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return "https://www.linkedin.com/oauth/v2/authorization";
    }
}