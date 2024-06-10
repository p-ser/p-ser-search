package com.pser.search.domain;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

@Getter
public class ReservationStatusEnumConverter {
    @WritingConverter
    public static class ToIntegerConverter implements Converter<ReservationStatusEnum, Integer> {
        @Override
        public Integer convert(ReservationStatusEnum source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public static class ToEnumConverter implements Converter<Integer, ReservationStatusEnum> {
        @Override
        public ReservationStatusEnum convert(@NonNull Integer source) {
            return ReservationStatusEnum.getByValue(source);
        }
    }
}
