package com.pser.search.dao;

import com.pser.search.domain.Auction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionDao extends ElasticsearchRepository<Auction, Long>, AuctionDaoCustom {
}
