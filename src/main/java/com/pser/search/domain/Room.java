package com.pser.search.domain;

import com.pser.search.config.ESConstants;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setting(settingPath = "elasticsearch/setting.json")
public class Room implements BaseDocument {
    @Id
    private Long id;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute)
    private LocalDateTime createdAt;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute)
    private LocalDateTime updatedAt;

    @Field(type = FieldType.Long)
    private Long hotelId;

    @Field(type = FieldType.Text, analyzer = ESConstants.ANALYZER_KOREAN)
    private String name;

    @Field(type = FieldType.Text, analyzer = ESConstants.ANALYZER_KOREAN)
    private String description;

    @Field(type = FieldType.Text, analyzer = ESConstants.ANALYZER_KOREAN)
    private String precaution;

    @Field(type = FieldType.Integer)
    private Integer price;

    @Field(type = FieldType.Date, format = DateFormat.hour_minute)
    private LocalTime checkIn;

    @Field(type = FieldType.Date, format = DateFormat.hour_minute)
    private LocalTime checkOut;

    @Field(type = FieldType.Integer)
    private Integer standardCapacity;

    @Field(type = FieldType.Integer)
    private Integer maxCapacity;

    @Field(type = FieldType.Integer)
    private Integer totalRooms;

    @Field(type = FieldType.Nested)
    private Amenity amenity;

    @Field(type = FieldType.Keyword)
    private List<String> roomImages;
}
