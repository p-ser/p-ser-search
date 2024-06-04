package com.pser.search.domain;

import com.pser.search.config.ESConstants;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.annotations.Setting;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Data
@NoArgsConstructor
@Setting(settingPath = "elasticsearch/setting.json")
@Document(indexName = ESConstants.DOC_HOTEL)
public class Hotel implements BaseDocument {
    @Id
    private Long id;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute)
    private LocalDateTime createdAt;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute)
    private LocalDateTime updatedAt;

    @Field(type = FieldType.Text, analyzer = ESConstants.ANALYZER_KOREAN)
    private String name;

    @Field(type = FieldType.Integer)
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
    private Double latitude = 0.0;

    @Field(type = FieldType.Double)
    private Double longitude = 0.0;

    @GeoPointField
    private GeoPoint location = new GeoPoint(latitude, longitude);

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

    @Builder
    public Hotel(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String name, HotelCategoryEnum category,
                 String description, String notice, String province, String city, String district,
                 String detailedAddress,
                 Double latitude, Double longitude, String mainImage, String businessNumber,
                 String certUrl, String visitGuidance, Long userId, Facility facility, List<String> images,
                 List<Room> rooms) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.category = category;
        this.description = description;
        this.notice = notice;
        this.province = province;
        this.city = city;
        this.district = district;
        this.detailedAddress = detailedAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.mainImage = mainImage;
        this.businessNumber = businessNumber;
        this.certUrl = certUrl;
        this.visitGuidance = visitGuidance;
        this.userId = userId;
        this.facility = facility;
        this.images = images;
        this.rooms = rooms;

        setLocation(latitude, longitude);
    }

    public void setLocation(double latitude, double longitude) {
        this.location = new GeoPoint(latitude, longitude);
    }
}
