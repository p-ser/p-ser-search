package com.pser.search.kafka.consumer;

import com.pser.search.application.HotelService;
import com.pser.search.config.kafka.KafkaTopics;
import com.pser.search.dto.HotelDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelWriteConsumer {
    private final HotelService hotelService;

    @RetryableTopic(kafkaTemplate = "hotelDtoValueKafkaTemplate", attempts = "5", retryTopicSuffix = "-retry-${kafka.consumer-group-id}")
    @KafkaListener(topics = {KafkaTopics.HOTEL_CREATED,
            KafkaTopics.HOTEL_UPDATED}, groupId = "${kafka.consumer-group-id}", containerFactory = "hotelDtoValueListenerContainerFactory")
    public void onCreatedOrUpdated(HotelDto hotelDto) {
        hotelService.saveOrUpdate(hotelDto);
    }

    @RetryableTopic(kafkaTemplate = "hotelDtoValueKafkaTemplate", attempts = "5", retryTopicSuffix = "-retry-${kafka.consumer-group-id}")
    @KafkaListener(topics = {
            KafkaTopics.HOTEL_DELETED}, groupId = "${kafka.consumer-group-id}", containerFactory = "hotelDtoValueListenerContainerFactory")
    public void onDeleted(HotelDto hotelDto) {
        hotelService.delete(hotelDto);
    }
}
