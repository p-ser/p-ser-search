package com.pser.search.config.kafka.consumer;

import com.pser.search.dto.ReservationDto;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@Configuration
@RequiredArgsConstructor
public class ReservationDtoValueConsumerConfig {
    private final Environment env;

    @Bean
    public ConsumerFactory<String, ReservationDto> reservationDtoValueConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("kafka.bootstrap-servers"));
        config.put(ConsumerConfig.GROUP_ID_CONFIG, env.getProperty("kafka.consumer-group-id"));
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ReservationDtoDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReservationDto> reservationDtoValueListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ReservationDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(reservationDtoValueConsumerFactory());

        return factory;
    }
}
