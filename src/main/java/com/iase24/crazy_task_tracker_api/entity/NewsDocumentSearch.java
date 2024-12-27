package com.iase24.crazy_task_tracker_api.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Document(indexName = "translations")
@Builder
public class NewsDocumentSearch {

    @Id
    private Long id;
    private Long news_id;

    @Field(type = FieldType.Text, name = "title", analyzer = "standard")
    private String title;

    @Field(type = FieldType.Text, name = "info", analyzer = "standard")
    private String info;

    @Field(type = FieldType.Text, name = "language", analyzer = "standard")
    private String language;
}
