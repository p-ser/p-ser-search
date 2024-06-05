package com.pser.search.dto;

import com.pser.search.domain.AuctionStatusEnum;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionDto {
    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long reservationId;

    private String merchantUid;

    private String impUid;

    private Long winnerId;

    private Integer price;

    private Integer endPrice;

    private LocalDateTime endAt;

    private Integer depositPrice;

    private AuctionStatusEnum status;
}
