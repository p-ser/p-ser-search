package com.pser.search.config;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
public class ElasticSearchConfig extends ElasticsearchConfiguration {
    @Value("${spring.data.elasticsearch.url}")
    private String url;

    @Value("${spring.data.elasticsearch.username}")
    private String username;

    @Value("${spring.data.elasticsearch.password}")
    private String password;

    public static SSLContext disableSslVerification() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            // trust manager 설치
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            return sc;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    public static HostnameVerifier allHostsValid() {

        HostnameVerifier allHostsValid = (hostname, session) -> true;

        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        return allHostsValid;

    }

    @Override
    @NonNull
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(url)
                .usingSsl(disableSslVerification(), allHostsValid())
                .withBasicAuth(username, password)
                .build();
    }
}
