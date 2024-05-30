package com.pser.search.application;

import com.pser.search.dao.RoomDao;
import com.pser.search.domain.Room;
import com.pser.search.dto.RoomDto;
import com.pser.search.dto.mapper.HotelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomDao roomDao;
    private final HotelMapper hotelMapper;

    public void saveOrUpdate(RoomDto roomDto) {
        Room room = hotelMapper.toDocument(roomDto);
        roomDao.saveOrUpdate(room);
    }

    public void delete(RoomDto roomDto) {
        Room room = hotelMapper.toDocument(roomDto);
        roomDao.delete(room);
    }
}
