package com.pser.search.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Amenity implements BaseDocument {
    @Id
    private Long id;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute)
    private LocalDateTime createdAt;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute)
    private LocalDateTime updatedAt;

    @Field(type = FieldType.Long)
    private Long roomId;

    @Field(type = FieldType.Boolean)
    private Boolean heatingSystem;

    @Field(type = FieldType.Boolean)
    private Boolean tv;

    @Field(type = FieldType.Boolean)
    private Boolean refrigerator;

    @Field(type = FieldType.Boolean)
    private Boolean airConditioner;

    @Field(type = FieldType.Boolean)
    private Boolean washer;

    @Field(type = FieldType.Boolean)
    private Boolean terrace;

    @Field(type = FieldType.Boolean)
    private Boolean coffeeMachine;

    @Field(type = FieldType.Boolean)
    private Boolean internet;

    @Field(type = FieldType.Boolean)
    private Boolean kitchen;

    @Field(type = FieldType.Boolean)
    private Boolean bathtub;

    @Field(type = FieldType.Boolean)
    private Boolean iron;

    @Field(type = FieldType.Boolean)
    private Boolean pool;

    @Field(type = FieldType.Boolean)
    private Boolean pet;

    @Field(type = FieldType.Boolean)
    private Boolean inAnnex;
}
