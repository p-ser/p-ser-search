package com.pser.search.config;

import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.pser.search.Util;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {
    @Value("${spring.data.elasticsearch.url}")
    private String url;

    @Value("${spring.data.elasticsearch.username}")
    private String username;

    @Value("${spring.data.elasticsearch.password}")
    private String password;

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder()
                .serializers(new LocalDateSerializer(Util.DATE), new LocalDateTimeSerializer(Util.DATE_HOUR_MINUTE), new LocalTimeSerializer(Util.HOUR_MINUTE))
                .build();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

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

    @Override
    @NonNull
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(url)
                .usingSsl(disableSslVerification(), allHostsValid())
                .withBasicAuth(username, password)
                .build();
    }

    @Bean
    public JsonpMapper jsonpMapper(ObjectMapper objectMapper) {
        return new JacksonJsonpMapper(objectMapper);
    }
}
