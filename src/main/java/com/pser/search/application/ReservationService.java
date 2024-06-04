package com.pser.search.application;

import com.pser.search.dao.ReservationDao;
import com.pser.search.domain.Reservation;
import com.pser.search.dto.ReservationDto;
import com.pser.search.dto.SearchSlice;
import com.pser.search.dto.mapper.ReservationMapper;
import com.pser.search.dto.request.ReservationSearchRequest;
import com.pser.search.dto.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ReservationMapper reservationMapper;

    public SearchSlice<ReservationResponse> search(ReservationSearchRequest request, Pageable pageable) {
        SearchSlice<Reservation> result = reservationDao.search(request, pageable);
        return result.map(reservationMapper::toResponse);
    }

    public ReservationResponse getById(long reservationId) {
        Reservation reservation = reservationDao.findById(reservationId)
                .orElseThrow();
        return reservationMapper.toResponse(reservation);
    }

    public void saveOrUpdate(ReservationDto reservationDto) {
        Reservation document = reservationMapper.toDocument(reservationDto);
        reservationDao.save(document);
    }
}
