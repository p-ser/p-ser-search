package com.pser.search.application;

import com.pser.search.dao.HotelDao;
import com.pser.search.domain.Hotel;
import com.pser.search.dto.HotelDto;
import com.pser.search.dto.mapper.HotelMapper;
import com.pser.search.dto.request.HotelSearchRequest;
import com.pser.search.dto.response.HotelResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelDao hotelDao;
    private final HotelMapper hotelMapper;

    public Slice<HotelResponse> search(HotelSearchRequest request, Pageable pageable) {
        Slice<Hotel> result = hotelDao.search(request, pageable);
        return result.map(hotelMapper::toResponse);
    }

    public void saveOrUpdate(HotelDto hotelDto) {
        Hotel document = hotelMapper.toDocument(hotelDto);
        hotelDao.save(document);
    }

    public void delete(HotelDto hotelDto) {
        hotelDao.deleteById(hotelDto.getId());
    }
}
