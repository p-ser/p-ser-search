package com.pser.search.dao;

import com.pser.search.domain.Reservation;
import com.pser.search.dto.SearchSlice;
import com.pser.search.dto.request.ReservationSearchRequest;
import org.springframework.data.domain.Pageable;

public interface ReservationDaoCustom {
    SearchSlice<Reservation> search(ReservationSearchRequest request, Pageable pageable);
}
