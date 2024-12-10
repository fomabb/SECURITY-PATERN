package com.iase24.crazy_task_tracker_api.businessapi.service.impl;

import com.iase24.crazy_task_tracker_api.businessapi.dto.request.CreateNewsTwoLanguageRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.NewsCreateDataResponse;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.NewsDataResponse;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.NewsTranslateCreateDataResponse;
import com.iase24.crazy_task_tracker_api.businessapi.repository.NewsEnRepository;
import com.iase24.crazy_task_tracker_api.businessapi.repository.NewsRuRepository;
import com.iase24.crazy_task_tracker_api.businessapi.service.NewsService;
import com.iase24.crazy_task_tracker_api.entity.News;
import com.iase24.crazy_task_tracker_api.entity.NewsEn;
import com.iase24.crazy_task_tracker_api.entity.NewsRu;
import com.iase24.crazy_task_tracker_api.entity.NewsTranslation;
import com.iase24.crazy_task_tracker_api.exceptionhandler.exception.BusinessException;
import com.iase24.crazy_task_tracker_api.repository.NewsRepository;
import com.iase24.crazy_task_tracker_api.repository.NewsTranslationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class NewsServiceImpl implements NewsService {

    private final NewsRuRepository ruRepository;
    private final NewsEnRepository enRepository;
    private final NewsRepository newsRepository;
    private final NewsTranslationRepository translationRepository;

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
    public List<NewsDataResponse> testGetAllNews(String lang) {
        return translationRepository.findNewsTranslationByLanguage(lang)
                .stream().map(translation ->
                        new NewsDataResponse(translation.getNews().getId(), translation.getTitle(), translation.getInfo()))
                .toList();
    }

    @Override
    @Transactional
    public NewsTranslateCreateDataResponse testCreateNews(News request) {
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
        translation.setNews(newsRepository.findById(newsId).orElseThrow(() -> new RuntimeException("News not found")));
        return translationRepository.save(translation);
    }

    @Override
    public NewsDataResponse testGetById(Long newsId, String lang) {
        return translationRepository.findByNewsIdAndLanguage(newsId, lang)
                .map(translation ->
                        new NewsDataResponse(translation.getNews().getId(), translation.getTitle(), translation.getInfo()))
                .orElseThrow(() -> new EntityNotFoundException(""));
    }
}