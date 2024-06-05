package com.pser.search.dto.response;

import com.pser.search.dto.AuctionDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class AuctionResponse extends AuctionDto {
    private ReservationResponse reservation;
}
