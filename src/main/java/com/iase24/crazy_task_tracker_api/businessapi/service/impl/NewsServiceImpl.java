package com.iase24.crazy_task_tracker_api.businessapi.service.impl;

import com.iase24.crazy_task_tracker_api.businessapi.dto.request.CreateNewsTwoLanguageRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.NewsCreateDataResponse;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.NewsDataResponse;
import com.iase24.crazy_task_tracker_api.businessapi.repository.NewsEnRepository;
import com.iase24.crazy_task_tracker_api.businessapi.repository.NewsRuRepository;
import com.iase24.crazy_task_tracker_api.businessapi.service.NewsService;
import com.iase24.crazy_task_tracker_api.entity.NewsEn;
import com.iase24.crazy_task_tracker_api.entity.NewsRu;
import com.iase24.crazy_task_tracker_api.exceptionhandler.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class NewsServiceImpl implements NewsService {

    private final NewsRuRepository ruRepository;
    private final NewsEnRepository enRepository;

    @Override
    @Transactional
    public NewsCreateDataResponse createNews(CreateNewsTwoLanguageRequest request) {
        NewsRu newsRu = NewsRu.builder().title(request.getTitleRu()).news(request.getInfoNewsRu()).build();
        NewsEn newsEn = NewsEn.builder().title(request.getTitleEn()).news(request.getInfoNewsEn()).build();
        ruRepository.save(newsRu);
        enRepository.save(newsEn);
        return new NewsCreateDataResponse(newsRu.getId(), newsEn.getId());
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
}