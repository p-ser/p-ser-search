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
public class Facility {
    @Id
    private Long id;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute)
    private LocalDateTime createdAt;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute)
    private LocalDateTime updatedAt;

    @Field(type = FieldType.Long)
    private Long hotelId;

    @Field(type = FieldType.Boolean)
    private Boolean parkingLot;

    @Field(type = FieldType.Boolean)
    private Boolean barbecue;

    @Field(type = FieldType.Boolean)
    private Boolean wifi;

    @Field(type = FieldType.Boolean)
    private Boolean sauna;

    @Field(type = FieldType.Boolean)
    private Boolean swimmingPool;

    @Field(type = FieldType.Boolean)
    private Boolean restaurant;

    @Field(type = FieldType.Boolean)
    private Boolean roofTop;

    @Field(type = FieldType.Boolean)
    private Boolean fitness;

    @Field(type = FieldType.Boolean)
    private Boolean dryer;

    @Field(type = FieldType.Boolean)
    private Boolean breakfast;

    @Field(type = FieldType.Boolean)
    private Boolean smokingArea;

    @Field(type = FieldType.Boolean)
    private Boolean allTimeDesk;

    @Field(type = FieldType.Boolean)
    private Boolean luggageStorage;

    @Field(type = FieldType.Boolean)
    private Boolean snackBar;

    @Field(type = FieldType.Boolean)
    private Boolean petFriendly;
}
