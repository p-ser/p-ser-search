package com.pser.search.dao;

import com.pser.search.domain.Auction;
import com.pser.search.dto.SearchSlice;
import com.pser.search.dto.request.AuctionSearchRequest;
import org.springframework.data.domain.Pageable;

public interface AuctionDaoCustom {
    SearchSlice<Auction> search(AuctionSearchRequest request, Pageable pageable);
}
