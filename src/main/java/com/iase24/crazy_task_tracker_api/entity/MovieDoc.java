package com.iase24.crazy_task_tracker_api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "movies_index")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDoc {

    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String movie;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String overview;
}
