package com.pser.search.dto;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Setter
@Getter
public class SearchQuery {
    private String keyword;

    private Double scoreAfter = 1.0;

    private Long idAfter = 0L;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdAfter;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdBefore;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime updatedAfter;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime updatedBefore;

    public SearchQuery(String keyword, Double scoreAfter, Long idAfter, LocalDateTime createdAfter,
                       LocalDateTime createdBefore, LocalDateTime updatedAfter, LocalDateTime updatedBefore) {
        this.keyword = keyword;
        this.scoreAfter = Optional.ofNullable(scoreAfter).orElse(this.scoreAfter);
        this.idAfter = Optional.ofNullable(idAfter).orElse(this.idAfter);
        this.createdAfter = createdAfter;
        this.createdBefore = createdBefore;
        this.updatedAfter = updatedAfter;
        this.updatedBefore = updatedBefore;
    }
}
