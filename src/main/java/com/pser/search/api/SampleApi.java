package com.pser.search.api;

import com.pser.search.domain.NestedSample;
import com.pser.search.domain.Sample;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/samples")
@RequiredArgsConstructor
public class SampleApi {
    private final ElasticsearchOperations operations;

    @GetMapping
    public List<Sample> getAll() {
//        Builder bool = QueryBuilders.bool();
//        bool.should(QueryBuilders.wildcard(WildcardQuery.of()));

//        Criteria criteria = new Criteria("title")
//                .matches("a1b").fuzzy("AUTO");
//
//        SearchHits<Sample> search = operations.search(new CriteriaQuery(criteria), Sample.class);
        return operations.search(Query.findAll(), Sample.class).stream().map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    @PostMapping
    public void post() {
        Sample sample = Sample.builder()
                .id(1L)
                .createdAt(LocalDateTime.now())
                .time(LocalTime.of(2, 30))
                .title("안녕하세요 나는 샘플")
                .category("게시글")
                .nestedSamples(List.of(NestedSample.builder().build()))
                .tags(List.of("aa", "bb"))
                .build();
        Sample sample2 = Sample.builder()
                .id(2L)
                .title("샘플입니다")
                .category("글")
                .tags(List.of("aa", "cc"))
                .build();
        Sample sample3 = Sample.builder()
                .id(3L)
                .title("공지 샘플 게시글입니다")
                .category("공지글")
                .tags(List.of("dd", "ee"))
                .build();
        Sample sample4 = Sample.builder()
                .id(4L)
                .title("공지로 올라갔나요")
                .category("공지게시글")
                .tags(List.of("ff", "gg"))
                .build();
        operations.save(sample, sample2, sample3, sample4);
    }
}
