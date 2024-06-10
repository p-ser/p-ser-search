package com.pser.search.kafka.consumer;

import com.pser.search.application.RoomService;
import com.pser.search.config.kafka.KafkaTopics;
import com.pser.search.dto.RoomDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoomWriteConsumer {
    private final RoomService roomService;

    @RetryableTopic(kafkaTemplate = "roomDtoValueKafkaTemplate", attempts = "5", retryTopicSuffix = "-retry-${kafka.consumer-group-id}")
    @KafkaListener(topics = {KafkaTopics.ROOM_CREATED,
            KafkaTopics.ROOM_UPDATED}, groupId = "${kafka.consumer-group-id}", containerFactory = "roomDtoValueListenerContainerFactory")
    public void onCreatedOrUpdated(RoomDto roomDto) {
        roomService.saveOrUpdate(roomDto);
    }

    @RetryableTopic(kafkaTemplate = "roomDtoValueKafkaTemplate", attempts = "5", retryTopicSuffix = "-retry-${kafka.consumer-group-id}")
    @KafkaListener(topics = {
            KafkaTopics.ROOM_DELETED}, groupId = "${kafka.consumer-group-id}", containerFactory = "roomDtoValueListenerContainerFactory")
    public void onDeleted(RoomDto roomDto) {
        roomService.delete(roomDto);
    }
}
