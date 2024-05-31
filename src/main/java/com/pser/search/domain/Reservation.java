package com.pser.search.domain;

import com.pser.search.config.ESConstants;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setting(settingPath = "elasticsearch/setting.json")
@Document(indexName = ESConstants.DOC_RESERVATION)
public class Reservation {
    @Id
    private Long id;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute)
    private LocalDateTime createdAt;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute)
    private LocalDateTime updatedAt;

    @Field(type = FieldType.Nested)
    private Room room;

    @Field(type = FieldType.Long)
    private Long userId;

    @Field(type = FieldType.Keyword)
    private String merchantUid;

    @Field(type = FieldType.Keyword)
    private String impUid;

    @Field(type = FieldType.Integer)
    private Integer price;

    @Field(type = FieldType.Date, format = DateFormat.date)
    private LocalDate startAt;

    @Field(type = FieldType.Date, format = DateFormat.date)
    private LocalDate endAt;

    @Field(type = FieldType.Integer)
    private Integer visitorCount;

    @Field(type = FieldType.Integer)
    private Integer adultCount;

    @Field(type = FieldType.Integer)
    private Integer childCount;

    @Field(type = FieldType.Integer)
    private ReservationStatusEnum status;
}
