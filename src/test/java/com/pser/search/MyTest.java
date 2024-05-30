package com.pser.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pser.search.domain.Hotel;
import com.pser.search.domain.HotelCategoryEnum;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class MyTest {
    @Test
    public void test() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Hotel ho = Hotel.builder()
                .build();
        ho.setCategory(HotelCategoryEnum.HOTEL);
        byte[] bytes = objectMapper.writeValueAsBytes(ho);
        Hotel hotel = objectMapper.readValue(bytes, Hotel.class);
    }
}
