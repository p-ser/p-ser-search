package com.pser.search.dao;

import com.pser.search.domain.Hotel;
import com.pser.search.dto.SearchSlice;
import com.pser.search.dto.request.HotelSearchRequest;
import org.springframework.data.domain.Pageable;

public interface HotelDaoCustom {
    SearchSlice<Hotel> search(HotelSearchRequest request, Pageable pageable);
}
