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
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setting(settingPath = "elasticsearch/setting.json")
@Document(indexName = "samples")
public class Sample {
    @Id
    private Long id;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute)
    private LocalDateTime createdAt;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute)
    private LocalDateTime updatedAt;

    @Field(type = FieldType.Date, format = DateFormat.hour_minute)
    private LocalTime time;

    @Field(type = FieldType.Text, analyzer = ESConstants.ANALYZER_KOREAN)
    private String title;

    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Nested)
    private List<NestedSample> nestedSamples;

    @Field(type = FieldType.Keyword)
    private List<String> tags;
}
