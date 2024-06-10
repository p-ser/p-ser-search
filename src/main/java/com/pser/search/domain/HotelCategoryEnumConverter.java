package com.pser.search.domain;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

@Getter
public class HotelCategoryEnumConverter {
    @WritingConverter
    public static class ToIntegerConverter implements Converter<HotelCategoryEnum, Integer> {
        @Override
        public Integer convert(HotelCategoryEnum source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public static class ToEnumConverter implements Converter<Integer, HotelCategoryEnum> {
        @Override
        public HotelCategoryEnum convert(@NonNull Integer source) {
            return HotelCategoryEnum.getByValue(source);
        }
    }
}
