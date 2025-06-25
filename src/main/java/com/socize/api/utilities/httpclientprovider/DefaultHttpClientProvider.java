package com.socize.api.utilities.httpclientprovider;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultHttpClientProvider implements HttpClientProvider {
    private final CloseableHttpClient client;

    private static final int connectionTimeout = 5000;
    private static final int socketTimeout = 5000;
    private static final Logger logger = LoggerFactory.getLogger(DefaultHttpClientProvider.class);

    private DefaultHttpClientProvider() {
        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(connectionTimeout)
            .setSocketTimeout(socketTimeout)
            .build();
        
        client = HttpClients.custom()
            .setDefaultRequestConfig(requestConfig)
            .build();
        
        
        Thread httpClientClosingTask = new Thread(() -> {

            try {

                if(client != null) {
                    client.close();
                }

                logger.info("Http client successfully closed.");

            } catch (Exception e) {
                logger.error("Failed to close http client.", e);
            }
            
        });

        Runtime.getRuntime().addShutdownHook(httpClientClosingTask);
    }

    private static class SingletonInstanceHolder {
        private static final DefaultHttpClientProvider INSTANCE = new DefaultHttpClientProvider();
    }

    public static DefaultHttpClientProvider getInstance() {
        return SingletonInstanceHolder.INSTANCE;
    }

    @Override
    public CloseableHttpClient getClient() {
        return client;
    }
    
}
