package com.iase24.crazy_task_tracker_api.businessapi.service;

import com.iase24.crazy_task_tracker_api.businessapi.dto.request.AddNewLangRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.request.CreateNewsTwoLanguageRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.AddNewLanguageResponse;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.NewsCreateDataResponse;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.NewsDataResponse;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.NewsTranslateCreateDataResponse;
import com.iase24.crazy_task_tracker_api.entity.News;
import com.iase24.crazy_task_tracker_api.entity.NewsTranslation;
import jakarta.validation.Valid;

import java.util.List;

public interface NewsService {

    NewsCreateDataResponse createNews(@Valid List<CreateNewsTwoLanguageRequest> request);

    List<NewsDataResponse> getAllNews(String lang);

    NewsDataResponse getNewsById(String lang, Long id);

    List<NewsDataResponse> newGetAllNews(String lang);

    NewsTranslateCreateDataResponse newCreateNews(News news);

    NewsTranslation addTranslation(Long newsId, NewsTranslation translation);

    NewsDataResponse newNewsGetById(Long newsId, String lang);

    AddNewLanguageResponse addNewLanguageToNews();
}
