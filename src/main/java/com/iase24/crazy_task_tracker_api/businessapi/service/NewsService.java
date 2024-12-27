package com.iase24.crazy_task_tracker_api.businessapi.service;

import com.iase24.crazy_task_tracker_api.businessapi.dto.request.CreateNewsTwoLanguageRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.request.LikeByUserIdAndNewsIdRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.*;
import com.iase24.crazy_task_tracker_api.entity.News;
import com.iase24.crazy_task_tracker_api.entity.NewsDocumentSearch;
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

    void addLike(LikeByUserIdAndNewsIdRequest request);

    int countAllLikesByNewsId(Long newsId);

    List<CountLikesResponse> getAllNewsWithLikes(String lang);

    CountLikesResponse getNewsByIdWithLikes(String lang, Long newsId);

    CountLikesResponse getContentWithLikeForClick(String  lang, Long newsId);

    List<NewsDocumentSearch> search(String lang, String query);

    List<SuggestionsFulltextSearchResponse> suggestionsFulltextSearch(String lang, String query);

    void indexTranslations();
}
