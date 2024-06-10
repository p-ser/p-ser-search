package com.pser.search.kafka.consumer;

import com.pser.search.application.AuctionService;
import com.pser.search.config.kafka.KafkaTopics;
import com.pser.search.dto.AuctionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuctionWriteConsumer {
    private final AuctionService auctionService;

    @RetryableTopic(kafkaTemplate = "auctionDtoValueKafkaTemplate", attempts = "5", retryTopicSuffix = "-retry-${kafka.consumer-group-id}")
    @KafkaListener(topics = {KafkaTopics.AUCTION_CREATED,
            KafkaTopics.AUCTION_UPDATED}, groupId = "${kafka.consumer-group-id}", containerFactory = "auctionDtoValueListenerContainerFactory")
    public void onCreatedOrUpdated(AuctionDto auctionDto) {
        auctionService.saveOrUpdate(auctionDto);
    }

    @RetryableTopic(kafkaTemplate = "auctionDtoValueKafkaTemplate", attempts = "5", retryTopicSuffix = "-retry-${kafka.consumer-group-id}")
    @KafkaListener(topics = {
            KafkaTopics.AUCTION_DELETED}, groupId = "${kafka.consumer-group-id}", containerFactory = "auctionDtoValueListenerContainerFactory")
    public void onDeleted(AuctionDto auctionDto) {
        auctionService.delete(auctionDto);
    }
}
