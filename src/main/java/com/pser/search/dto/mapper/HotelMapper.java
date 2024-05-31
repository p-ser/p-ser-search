package com.pser.search.dto.mapper;

import com.pser.search.domain.Amenity;
import com.pser.search.domain.Facility;
import com.pser.search.domain.Hotel;
import com.pser.search.domain.Room;
import com.pser.search.dto.AmenityDto;
import com.pser.search.dto.FacilityDto;
import com.pser.search.dto.HotelDto;
import com.pser.search.dto.RoomDto;
import com.pser.search.dto.response.AmenityResponse;
import com.pser.search.dto.response.FacilityResponse;
import com.pser.search.dto.response.HotelResponse;
import com.pser.search.dto.response.RoomResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface HotelMapper {

    HotelResponse toResponse(Hotel hotel);

    FacilityResponse toResponse(Facility facility);

    RoomResponse toResponse(Room room);

    AmenityResponse toResponse(Amenity amenity);

    Hotel toDocument(HotelDto hotel);

    Facility toDocument(FacilityDto facility);

    Room toDocument(RoomDto room);

    Amenity toDocument(AmenityDto amenity);
}
