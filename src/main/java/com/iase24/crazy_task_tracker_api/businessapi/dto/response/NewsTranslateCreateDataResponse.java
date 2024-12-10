package com.iase24.crazy_task_tracker_api.businessapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsTranslateCreateDataResponse {

    private Long newsId;

    private Timestamp createdAt;
}
