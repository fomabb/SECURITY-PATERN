package com.iase24.crazy_task_tracker_api.businessapi.controller;

import com.iase24.crazy_task_tracker_api.businessapi.dto.request.CreateNewsTwoLanguageRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.NewsCreateDataResponse;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.NewsDataResponse;
import com.iase24.crazy_task_tracker_api.businessapi.service.NewsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "Новостной API", description = "Интерфейс для бизнес логики новостей")
public class NewsController {

    private final NewsService newsService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<NewsCreateDataResponse> createNewsForTwoLanguage(@RequestBody @Valid CreateNewsTwoLanguageRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(newsService.createNews(request));
    }

    @GetMapping("/{lang}/all")
    public ResponseEntity<List<NewsDataResponse>> getAllNews(@PathVariable("lang") String lang) {
        return ResponseEntity.ok(newsService.getAllNews(lang));
    }

    @GetMapping("/{lang}/{id}")
    public ResponseEntity<NewsDataResponse> getNewsById(@PathVariable("lang") String lang, @PathVariable("id") Long id) {
        return ResponseEntity.ok(newsService.getNewsById(lang, id));
    }
}
