package com.pser.search.kafka.consumer;

import com.pser.search.application.ReservationService;
import com.pser.search.config.kafka.KafkaTopics;
import com.pser.search.dto.ReservationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationWriteConsumer {
    private final ReservationService reservationService;

    @RetryableTopic(kafkaTemplate = "reservationDtoValueKafkaTemplate", attempts = "5")
    @KafkaListener(topics = {KafkaTopics.RESERVATION_CREATED,
            KafkaTopics.RESERVATION_UPDATED}, groupId = "${kafka.consumer-group-id}", containerFactory = "reservationDtoValueListenerContainerFactory")
    public void onCreatedOrUpdated(ReservationDto reservationDto) {
        reservationService.saveOrUpdate(reservationDto);
    }
}
