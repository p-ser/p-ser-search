package com.pser.search.dao;

import com.pser.search.config.ESConstants;
import com.pser.search.domain.Room;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.ScriptType;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomDao {
    private final ElasticsearchOperations operations;

    public void saveOrUpdate(Room room) {
        Map<String, Object> params = Map.of("room", room);

        UpdateQuery updateQuery = UpdateQuery.builder(room.getHotelId().toString())
                .withScript(
                        "ctx._source.rooms.removeIf(room -> room.id == params.room.id);ctx._source.rooms.add(params.room)")
                .withScriptType(ScriptType.INLINE)
                .withParams(params)
                .build();
        operations.update(updateQuery, IndexCoordinates.of(ESConstants.DOC_HOTEL));
    }

    public void delete(Room room) {
        Map<String, Object> params = Map.of("room", room);

        UpdateQuery updateQuery = UpdateQuery.builder(room.getHotelId().toString())
                .withScript(
                        "ctx._source.rooms.removeIf(room -> room.id == params.room.id)")
                .withScriptType(ScriptType.INLINE)
                .withParams(params)
                .build();
        operations.update(updateQuery, IndexCoordinates.of(ESConstants.DOC_HOTEL));
    }
}
