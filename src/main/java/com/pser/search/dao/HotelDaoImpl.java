package com.pser.search.dao;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import com.pser.search.domain.Hotel;
import com.pser.search.dto.SearchQuery;
import com.pser.search.dto.request.HotelSearchRequest;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;

@RequiredArgsConstructor
public class HotelDaoImpl implements HotelDaoCustom {
    private final ElasticsearchOperations operations;

    @Override
    public Slice<Hotel> search(HotelSearchRequest request, Pageable pageable) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(builder -> builder.bool(buildSearchQuery(request)))
                .build();
        List<Hotel> result = operations.search(query, Hotel.class)
                .map(SearchHit::getContent)
                .stream()
                .collect(Collectors.toList());
        return new SliceImpl<>(result, pageable, true);
    }

    private BoolQuery buildSearchQuery(SearchQuery request) {

//        return QueryBuilders.bool().must(
//                builder ->
//                        builder.match(builder1 -> builder1.field("title"))
//        );
        return null;
    }
}
