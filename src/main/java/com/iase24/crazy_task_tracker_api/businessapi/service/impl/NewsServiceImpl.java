package com.iase24.crazy_task_tracker_api.businessapi.service.impl;

import com.iase24.crazy_task_tracker_api.businessapi.dto.request.CreateNewsTwoLanguageRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.request.LikeByUserIdAndNewsIdRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.AddNewLanguageResponse;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.CountLikesResponse;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.NewsCreateDataResponse;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.NewsDataResponse;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.NewsTranslateCreateDataResponse;
import com.iase24.crazy_task_tracker_api.businessapi.repository.LikeRepository;
import com.iase24.crazy_task_tracker_api.businessapi.repository.NewsEnRepository;
import com.iase24.crazy_task_tracker_api.businessapi.repository.NewsRepository;
import com.iase24.crazy_task_tracker_api.businessapi.repository.NewsRuRepository;
import com.iase24.crazy_task_tracker_api.businessapi.repository.NewsSearchRepository;
import com.iase24.crazy_task_tracker_api.businessapi.repository.NewsTranslationRepository;
import com.iase24.crazy_task_tracker_api.businessapi.service.NewsService;
import com.iase24.crazy_task_tracker_api.entity.Like;
import com.iase24.crazy_task_tracker_api.entity.News;
import com.iase24.crazy_task_tracker_api.adminapi.searcher.NewsDocumentSearch;
import com.iase24.crazy_task_tracker_api.entity.NewsEn;
import com.iase24.crazy_task_tracker_api.entity.NewsRu;
import com.iase24.crazy_task_tracker_api.entity.NewsTranslation;
import com.iase24.crazy_task_tracker_api.exceptionhandler.exception.BusinessException;
import com.iase24.crazy_task_tracker_api.security.entity.User;
import com.iase24.crazy_task_tracker_api.security.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class NewsServiceImpl implements NewsService {

    private final NewsRuRepository ruRepository;
    private final NewsEnRepository enRepository;
    private final NewsRepository newsRepository;
    private final NewsTranslationRepository translationRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final NewsSearchRepository elasticSearchRepository;
    private final NewsTranslationRepository newsTranslationRepository;

    @Override
    @Transactional
    public NewsCreateDataResponse createNews(List<CreateNewsTwoLanguageRequest> request) {
        List<NewsRu> newsRu = new ArrayList<>();
        List<NewsEn> newsEn = new ArrayList<>();
        for (CreateNewsTwoLanguageRequest item : request) {
            newsRu.add(NewsRu.builder().title(item.getTitleRu()).news(item.getInfoNewsRu()).build());
            newsEn.add(NewsEn.builder().title(item.getTitleEn()).news(item.getInfoNewsEn()).build());
        }
        ruRepository.saveAll(newsRu);
        enRepository.saveAll(newsEn);
        return new NewsCreateDataResponse(newsRu.size());
    }

    @Override
    public List<NewsDataResponse> getAllNews(String lang) {
        if (lang.equals("ru")) {
            return ruRepository.findAll().stream()
                    .map(newsRu -> new NewsDataResponse(newsRu.getId(), newsRu.getTitle(), newsRu.getNews())).toList();
        } else if (lang.equals("en")) {
            return enRepository.findAll().stream()
                    .map(newsEn -> new NewsDataResponse(newsEn.getId(), newsEn.getTitle(), newsEn.getNews())).toList();
        } else {
            throw new BusinessException("This application does not support such the <%s> language".formatted(lang));
        }
    }

    @Override
    public NewsDataResponse getNewsById(String lang, Long id) {
        if (lang.equals("ru")) {
            return ruRepository.findById(id)
                    .map(newsRu -> new NewsDataResponse(newsRu.getId(), newsRu.getTitle(), newsRu.getNews()))
                    .orElseThrow(() -> new EntityNotFoundException("News with ID: %s not found".formatted(id)));
        } else if (lang.equals("en")) {
            return enRepository.findById(id)
                    .map(newsEn -> new NewsDataResponse(newsEn.getId(), newsEn.getTitle(), newsEn.getNews()))
                    .orElseThrow(() -> new EntityNotFoundException("News with ID: %s not found".formatted(id)));
        } else {
            throw new BusinessException("This application does not support such the <%s> language".formatted(lang));
        }
    }

    @Override
    public List<NewsDataResponse> newGetAllNews(String lang) {
        return translationRepository.findNewsTranslationByLanguage(lang)
                .stream().map(translation -> new NewsDataResponse(
                        translation.getNews().getId(), translation.getTitle(), translation.getInfo()
                )).toList();
    }

    @Override
    @Transactional
    public NewsTranslateCreateDataResponse newCreateNews(News request) {
        News news = new News();
        News savedNews = newsRepository.save(news);
        for (NewsTranslation translation : request.getTranslations()) {
            translationRepository.save(NewsTranslation.builder()
                    .news(savedNews)
                    .language(translation.getLanguage())
                    .title(translation.getTitle())
                    .info(translation.getInfo())
                    .build());
        }
        return new NewsTranslateCreateDataResponse(news.getId(), news.getDate());
    }

    @Override
    @Transactional
    public NewsTranslation addTranslation(Long newsId, NewsTranslation translation) {
        translation.setNews(newsRepository.findById(newsId).orElseThrow(() -> new EntityNotFoundException(
                "News with ID: %s not found".formatted(newsId))));
        return translationRepository.save(translation);
    }

    @Override
    public NewsDataResponse newNewsGetById(Long newsId, String lang) {
        return translationRepository.findByNewsIdAndLanguage(newsId, lang)
                .map(translation -> new NewsDataResponse(
                        translation.getNews().getId(), translation.getTitle(), translation.getInfo()
                )).orElseThrow(() -> new EntityNotFoundException(
                        "News, with ID: %s or language: %s, not found".formatted(newsId, lang)));
    }

    @Override
    @Transactional
    public AddNewLanguageResponse addNewLanguageToNews() {
        translationRepository.insertNewLanguageToNews();
        return new AddNewLanguageResponse();
    }

//===========================Section Post and like======================================================================

    @Override
    @Transactional
    public void addLike(LikeByUserIdAndNewsIdRequest request) {
        log.info("Начало поиска новости по ID: {}", request.getNewsId());
        News news = newsRepository.findById(request.getNewsId())
                .orElseThrow(() -> {
                    log.warn("Ошибка поиска новости по ID: {}", request.getNewsId());
                    return new EntityNotFoundException("News with ID: %s not found".formatted(request.getNewsId()));
                });
        log.info("Начало поиска пользователя по ID: {}", request.getUserId());
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> {
                    log.warn("Ошибка поиска пользователя по ID: {}", request.getUserId());
                    return new EntityNotFoundException("User with ID: %s not found".formatted(request.getUserId()));
                });
        if (likeRepository.findByNewsAndUser(news, user).isPresent()) {
            log.warn("Пользователь уже поставил лайк под этой новостью и он удаляется");
            removeLike(request.getNewsId(), request.getUserId());
        } else {
            Like like = Like.builder().news(news).user(user).reaction(true).build();
            log.info("Лайк сохранен в бзу данных");
            likeRepository.save(like);
        }
    }

    @Override
    public int countAllLikesByNewsId(Long newsId) {
        return likeRepository.countLikesByNewsIdAndReactionTrue(newsId);
    }

    @Override
    public List<CountLikesResponse> getAllNewsWithLikes(String lang) {
        return translationRepository.findNewsTranslationByLanguage(lang).stream()
                .map(news -> CountLikesResponse.builder()
                        .newsId(news.getNews().getId())
                        .title(news.getTitle())
                        .info(news.getInfo())
                        .countLikes(countAllLikesByNewsId(news.getNews().getId()))
                        .disLike(3)
                        .build()).toList();
    }

    @Override
    public CountLikesResponse getNewsByIdWithLikes(String lang, Long newsId) {
        return translationRepository.findByNewsIdAndLanguage(newsId, lang)
                .map(newsTranslation -> CountLikesResponse.builder()
                        .newsId(newsTranslation.getNews().getId())
                        .title(newsTranslation.getTitle())
                        .info(newsTranslation.getInfo())
                        .countLikes(countAllLikesByNewsId(newsTranslation.getNews().getId()))
                        .build())
                .orElseThrow(() -> new EntityNotFoundException(
                        "News, with ID: %s or language: %s, not found".formatted(newsId, lang)));
    }

    @Override
    public CountLikesResponse getContentWithLikeForClick(String lang, Long newsId) {
        return translationRepository.findByNewsIdAndLanguage(newsId, lang)
                .map(newsTranslation -> CountLikesResponse.builder()
                        .newsId(newsTranslation.getNews().getId())
                        .title(newsTranslation.getTitle())
                        .info(newsTranslation.getInfo())
                        .countLikes(countAllLikesByNewsId(newsTranslation.getNews().getId()))
                        .build())
                .orElseThrow(() -> new EntityNotFoundException(
                        "News, with ID: %s or language: %s, not found".formatted(newsId, lang)));
    }

    private void removeLike(Long newsId, UUID userId) {
        log.info("Начало поиска новости по ID: {} для проверки лайка", newsId);
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> {
                    log.warn("Ошибка поиска новости по ID: {} для проверки лайка", newsId);
                    return new EntityNotFoundException("News with ID: %s not found".formatted(newsId));
                });
        log.info("Начало поиска пользователя по ID: {} для проверки лайка", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("Ошибка поиска пользователя по ID: {} для проверки лайка", userId);
                    return new EntityNotFoundException("User with ID: %s not found".formatted(userId));
                });
        Like like = likeRepository.findByNewsAndUser(news, user)
                .orElseThrow(() -> new EntityNotFoundException("Like not found"));
        likeRepository.delete(like);
    }

//===========================Section Elastic Search=====================================================================

    @PostConstruct
    public void init() {
        indexTranslations();
    }

    @Scheduled(fixedRate = 3600000) // Каждые 1 час
    public void indexTranslationsPeriodically() {
        indexTranslations();
    }

    @Override
    public List<NewsDocumentSearch> search(String lang, String query) {
        return elasticSearchRepository.findByLanguageAndTitleContainingOrInfoContaining(lang, query, query);
    }

    @Override
    @Transactional
    public void indexTranslations() {
        List<NewsTranslation> translations = newsTranslationRepository.findAll();
        translations.stream()
                .map(translation -> NewsDocumentSearch.builder()
                        .id(translation.getId())
                        .title(translation.getTitle())
                        .info(translation.getInfo())
                        .language(translation.getLanguage())
                        .build())
                .forEach(elasticSearchRepository::save);
    }
}