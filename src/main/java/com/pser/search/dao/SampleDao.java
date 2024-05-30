package com.pser.search.dao;

import com.pser.search.domain.Sample;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleDao extends ElasticsearchRepository<Sample, Long> {
}
