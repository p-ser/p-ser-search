package com.pser.search.domain;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;

@Getter
public class HotelCategoryEnumConverter {
    public static class ToIntegerConverter implements Converter<HotelCategoryEnum, Integer> {
        @Override
        public Integer convert(HotelCategoryEnum source) {
            return source.getValue();
        }
    }

    public static class ToEnumConverter implements Converter<Integer, HotelCategoryEnum> {
        @Override
        public HotelCategoryEnum convert(@NonNull Integer source) {
            return HotelCategoryEnum.getByValue(source);
        }
    }
}
