package com.pser.search.domain;

import com.pser.search.config.ESConstants;
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
@Document(indexName = ESConstants.DOC_AUCTION)
public class Auction implements BaseDocument {
    @Id
    private Long id;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute)
    private LocalDateTime createdAt;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute)
    private LocalDateTime updatedAt;

    @Field(type = FieldType.Nested)
    private Reservation reservation;

    @Field(type = FieldType.Keyword)
    private String merchantUid;

    @Field(type = FieldType.Keyword)
    private String impUid;

    @Field(type = FieldType.Long)
    private Long winnerId;

    @Field(type = FieldType.Integer)
    private Integer price;

    @Field(type = FieldType.Integer)
    private Integer reservationPrice;

    @Field(type = FieldType.Integer)
    private Integer endPrice;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute)
    private LocalDateTime endAt;

    @Field(type = FieldType.Integer)
    private Integer depositPrice;

    @Field(type = FieldType.Integer)
    private AuctionStatusEnum status;
}
