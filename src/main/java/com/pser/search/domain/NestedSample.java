package com.pser.search.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Setting(settingPath = "elasticsearch/setting.json")
@Document(indexName = "nested_samples")
public class NestedSample {
    @Id
    private Long id;

    @Field(type = FieldType.Date)
    private LocalDateTime createdAt;

    @Field(type = FieldType.Date)
    private LocalDateTime updatedAt;

    @Field(type = FieldType.Text, analyzer = "korean")
    private String content;
}
