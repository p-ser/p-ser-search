package com.pser.search.domain;

import com.pser.search.config.ESConstants;
import java.time.LocalDateTime;
import java.util.List;
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
@Document(indexName = ESConstants.DOC_HOTEL)
public class Hotel {
    @Id
    private Long id;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute)
    private LocalDateTime createdAt;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute)
    private LocalDateTime updatedAt;

    @Field(type = FieldType.Text, analyzer = ESConstants.ANALYZER_KOREAN)
    private String name;

    @Field(type = FieldType.Keyword)
    private HotelCategoryEnum category;

    @Field(type = FieldType.Text, analyzer = ESConstants.ANALYZER_KOREAN)
    private String description;

    @Field(type = FieldType.Text, analyzer = ESConstants.ANALYZER_KOREAN)
    private String notice;

    @Field(type = FieldType.Keyword)
    private String province;

    @Field(type = FieldType.Keyword)
    private String city;

    @Field(type = FieldType.Keyword)
    private String district;

    @Field(type = FieldType.Keyword)
    private String detailedAddress;

    @Field(type = FieldType.Double)
    private Double latitude;

    @Field(type = FieldType.Double)
    private Double longitude;

    @Field(type = FieldType.Keyword)
    private String mainImage;

    @Field(type = FieldType.Keyword)
    private String businessNumber;

    @Field(type = FieldType.Keyword)
    private String certUrl;

    @Field(type = FieldType.Keyword)
    private String visitGuidance;

    @Field(type = FieldType.Long)
    private Long userId;

    @Field(type = FieldType.Nested)
    private Facility facility;

    @Field(type = FieldType.Keyword)
    private List<String> images;

    @Field(type = FieldType.Nested)
    private List<Room> rooms;
}
