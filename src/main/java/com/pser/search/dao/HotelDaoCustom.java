package com.pser.search.dao;

import com.pser.search.domain.Hotel;
import com.pser.search.dto.request.HotelSearchRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface HotelDaoCustom {
    Slice<Hotel> search(HotelSearchRequest request, Pageable pageable);
}
