package com.pser.search.config;

import com.pser.search.domain.AuctionStatusEnumConverter;
import com.pser.search.domain.HotelCategoryEnumConverter;
import com.pser.search.domain.ReservationStatusEnumConverter;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class ElasticsearchConfig extends ElasticsearchConfiguration {
    @Value("${spring.data.elasticsearch.url}")
    private String url;

    public static SSLContext disableSslVerification() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
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
    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
        return new ElasticsearchCustomConversions(
                Arrays.asList(
                        new HotelCategoryEnumConverter.ToEnumConverter(),
                        new HotelCategoryEnumConverter.ToIntegerConverter(),
                        new AuctionStatusEnumConverter.ToEnumConverter(),
                        new AuctionStatusEnumConverter.ToIntegerConverter(),
                        new ReservationStatusEnumConverter.ToEnumConverter(),
                        new ReservationStatusEnumConverter.ToIntegerConverter()
                )
        );
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public ElasticsearchContainer esContainer() {
        DockerImageName dockerImageName = DockerImageName.parse("docker.elastic.co/elasticsearch/elasticsearch:8.11.1");
        ElasticsearchContainer esContainer = new ElasticsearchContainer(dockerImageName);
        esContainer.withPassword("elastic");
        esContainer.withCommand("bash", "-c",
                "bin/elasticsearch-plugin install analysis-nori && docker-entrypoint.sh eswrapper");
        return esContainer;
    }

    @Override
    @NonNull
    public ClientConfiguration clientConfiguration() {
        ElasticsearchContainer container = esContainer();
        return ClientConfiguration.builder()
                .connectedTo(container.getHttpHostAddress())
                .usingSsl(disableSslVerification(), allHostsValid())
                .withBasicAuth("elastic", "elastic")
                .build();
    }
}
