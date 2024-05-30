package com.pser.search.dao;

import com.pser.search.domain.Hotel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelDao extends ElasticsearchRepository<Hotel, Long>, HotelDaoCustom {
}
