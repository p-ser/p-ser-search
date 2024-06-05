package com.pser.search.dto.mapper;

import com.pser.search.domain.Amenity;
import com.pser.search.domain.Auction;
import com.pser.search.domain.Facility;
import com.pser.search.domain.Hotel;
import com.pser.search.domain.Reservation;
import com.pser.search.domain.Room;
import com.pser.search.dto.AuctionDto;
import com.pser.search.dto.response.AmenityResponse;
import com.pser.search.dto.response.AuctionResponse;
import com.pser.search.dto.response.FacilityResponse;
import com.pser.search.dto.response.HotelResponse;
import com.pser.search.dto.response.ReservationResponse;
import com.pser.search.dto.response.RoomResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface AuctionMapper {
    @Mapping(source = "reservation.id", target = "reservationId")
    AuctionResponse toResponse(Auction auction);

    ReservationResponse toResponse(Reservation reservation);

    HotelResponse toResponse(Hotel hotel);

    FacilityResponse toResponse(Facility facility);

    RoomResponse toResponse(Room room);

    AmenityResponse toResponse(Amenity amenity);


    @Mapping(target = "reservation", ignore = true)
    Auction toDocument(AuctionDto auction);
}
