package com.pser.search.application;

import com.pser.search.dao.AuctionDao;
import com.pser.search.dao.ReservationDao;
import com.pser.search.domain.Auction;
import com.pser.search.domain.Reservation;
import com.pser.search.dto.AuctionDto;
import com.pser.search.dto.SearchSlice;
import com.pser.search.dto.mapper.AuctionMapper;
import com.pser.search.dto.request.AuctionSearchRequest;
import com.pser.search.dto.response.AuctionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuctionService {
    private final AuctionDao auctionDao;
    private final ReservationDao reservationDao;
    private final AuctionMapper auctionMapper;

    public SearchSlice<AuctionResponse> search(AuctionSearchRequest request, Pageable pageable) {
        SearchSlice<Auction> result = auctionDao.search(request, pageable);
        return result.map(auctionMapper::toResponse);
    }

    public AuctionResponse getById(long auctionId) {
        Auction auction = auctionDao.findById(auctionId)
                .orElseThrow();
        return auctionMapper.toResponse(auction);
    }

    public void saveOrUpdate(AuctionDto auctionDto) {
        Reservation reservation = reservationDao.findById(auctionDto.getReservationId())
                .orElseThrow();
        Auction auction = auctionMapper.toDocument(auctionDto);
        auction.setReservation(reservation);
        auctionDao.save(auction);
    }
}
