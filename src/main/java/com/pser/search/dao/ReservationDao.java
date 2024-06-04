package com.pser.search.dao;

import com.pser.search.domain.Reservation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationDao extends ElasticsearchRepository<Reservation, Long>, ReservationDaoCustom {
}
