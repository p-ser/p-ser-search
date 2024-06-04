package com.pser.search.dao;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import com.pser.search.domain.BaseDocument;
import com.pser.search.dto.SearchSlice;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchDao {
    private final ElasticsearchOperations operations;

    public <T extends BaseDocument> SearchSlice<T> search(SearchQueryArgument<T> searchQueryArgument) {
        Pageable pageable = searchQueryArgument.pageable();

        NativeQuery query = buildNativeSearchQuery(searchQueryArgument);
        SearchHits<T> searchHits = operations.search(query, searchQueryArgument.mappingClass());
        List<T> result = searchHits
                .map(SearchHit::getContent)
                .stream()
                .collect(Collectors.toList());
        Double nextScore = null;
        Long nextId = null;
        if (!result.isEmpty()) {
            SearchHit<T> lastItem = searchHits.getSearchHit(result.size() - 1);
            nextScore = (double) lastItem.getScore();
            nextId = lastItem.getContent().getId();
        }

        boolean hasNext = result.size() > pageable.getPageSize();
        if (hasNext) {
            result.remove(result.size() - 1);
        }
        return SearchSlice.<T>builder()
                .content(result)
                .pageable(pageable)
                .hasNext(hasNext)
                .nextScore(nextScore)
                .nextId(nextId)
                .build();
    }

    private <T> NativeQuery buildNativeSearchQuery(SearchQueryArgument<T> searchQueryArgument) {
        BoolQuery.Builder boolQueryBuilder = searchQueryArgument.boolQueryBuilder();
        List<Object> searchAfter = List.of(searchQueryArgument.scoreAfter, searchQueryArgument.idAfter);
        Pageable pageable = searchQueryArgument.pageable();

        NativeQueryBuilder nativeQueryBuilder = NativeQuery.builder()
                .withQuery(q -> q.bool(boolQueryBuilder.build()))
                .withMaxResults(pageable.getPageSize() + 1);

        nativeQueryBuilder.withSort(s ->
                s.field(f -> f
                        .field("_score")
                        .order(SortOrder.Desc)
                )
        );
        nativeQueryBuilder.withSort(s ->
                s.field(f -> f
                        .field("id")
                        .order(SortOrder.Asc)
                )
        );
        nativeQueryBuilder.withSearchAfter(searchAfter);

        return nativeQueryBuilder.build();
    }


    @Builder
    public record SearchQueryArgument<T>(
            BoolQuery.Builder boolQueryBuilder,
            Double scoreAfter,
            Long idAfter,
            Pageable pageable,
            Class<T> mappingClass
    ) {
    }
}
