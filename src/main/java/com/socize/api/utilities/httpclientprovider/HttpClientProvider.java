package com.socize.api.utilities.httpclientprovider;

import org.apache.http.impl.client.CloseableHttpClient;

public interface HttpClientProvider {

    /**
     * Gets the http client to use for making http requests.
     * 
     * @return the http client
     */
    CloseableHttpClient getClient();
}