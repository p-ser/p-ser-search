package com.pser.search.domain;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;

@Getter
public class ReservationStatusEnumConverter {
    public static class ToIntegerConverter implements Converter<ReservationStatusEnum, Integer> {
        @Override
        public Integer convert(ReservationStatusEnum source) {
            return source.getValue();
        }
    }

    public static class ToEnumConverter implements Converter<Integer, ReservationStatusEnum> {
        @Override
        public ReservationStatusEnum convert(@NonNull Integer source) {
            return ReservationStatusEnum.getByValue(source);
        }
    }
}
