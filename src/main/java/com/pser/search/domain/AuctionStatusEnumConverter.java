package com.pser.search.domain;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;

@Getter
public class AuctionStatusEnumConverter {
    public static class ToIntegerConverter implements Converter<AuctionStatusEnum, Integer> {
        @Override
        public Integer convert(AuctionStatusEnum source) {
            return source.getValue();
        }
    }

    public static class ToEnumConverter implements Converter<Integer, AuctionStatusEnum> {
        @Override
        public AuctionStatusEnum convert(@NonNull Integer source) {
            return AuctionStatusEnum.getByValue(source);
        }
    }
}
