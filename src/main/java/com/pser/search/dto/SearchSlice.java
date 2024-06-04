package com.pser.search.dto;

import java.util.List;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;

@Getter
public class SearchSlice<T> extends SliceImpl<T> {
    private final Double nextScore;
    private final Long nextId;

    @Builder
    public SearchSlice(List<T> content, Pageable pageable, boolean hasNext, Double nextScore, Long nextId) {
        super(content, pageable, hasNext);
        this.nextScore = nextScore;
        this.nextId = nextId;
    }

    @Override
    @NonNull
    public <U> SearchSlice<U> map(@NonNull Function<? super T, ? extends U> converter) {
        return new SearchSlice<>(getConvertedContent(converter), getPageable(), hasNext(), nextScore, nextId);
    }
}
