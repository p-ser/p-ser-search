package com.pser.search.domain;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

@Getter
public class AuctionStatusEnumConverter {
    @WritingConverter
    public static class ToIntegerConverter implements Converter<AuctionStatusEnum, Integer> {
        @Override
        public Integer convert(AuctionStatusEnum source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public static class ToEnumConverter implements Converter<Integer, AuctionStatusEnum> {
        @Override
        public AuctionStatusEnum convert(@NonNull Integer source) {
            return AuctionStatusEnum.getByValue(source);
        }
    }
}
