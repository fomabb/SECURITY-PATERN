package com.iase24.crazy_task_tracker_api.businessapi.controller;

import com.iase24.crazy_task_tracker_api.businessapi.dto.request.CreateNewsTwoLanguageRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.request.LikeByUserIdAndNewsIdRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.*;
import com.iase24.crazy_task_tracker_api.businessapi.service.NewsService;
import com.iase24.crazy_task_tracker_api.dto.exception.CommonExceptionResponse;
import com.iase24.crazy_task_tracker_api.entity.News;
import com.iase24.crazy_task_tracker_api.entity.NewsDocumentSearch;
import com.iase24.crazy_task_tracker_api.entity.NewsTranslation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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

    @Operation(summary = "Создать новости на разных языках (en/ru)",
            description = """
                    Новости создаются списком на двух языках, поля не могут быть пустыми.
                    """,
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешное создание новостей.",
                            content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = CreateNewsTwoLanguageRequest.class)))
                            }),
                    @ApiResponse(responseCode = "404", description = "Новость не добавлена.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                            })
            })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<NewsCreateDataResponse> createNewsForTwoLanguage(
            @RequestBody @Valid List<CreateNewsTwoLanguageRequest> request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(newsService.createNews(request));
    }

    @Operation(summary = "Показывает лист новостей на разных языках (en/ru)",
            description = """
                    Новости выводятся списком на языках (en/ru) в зависимости, какой язык выбирается.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Лист выведен.",
                            content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = NewsDataResponse.class)))
                            }),
                    @ApiResponse(responseCode = "404", description = "Не правильный запрос.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                            })
            })
    @GetMapping("/{lang}/all")
    public ResponseEntity<List<NewsDataResponse>> getAllNews(@PathVariable("lang") String lang) {
        return ResponseEntity.ok(newsService.getAllNews(lang));
    }

    @Operation(summary = "Выводит новость по ID на разных языках (en/ru)",
            description = """
                    Новость выводятся в одном экземпляре на языках (en/ru) в зависимости, какой язык выбирается.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Новость выведена.",
                            content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = NewsDataResponse.class)))
                            }),
                    @ApiResponse(responseCode = "404", description = "Новость не найдена по id.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                            })
            })
    @GetMapping("/{lang}/{id}")
    public ResponseEntity<NewsDataResponse> getNewsById(
            @PathVariable("lang") String lang,
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(newsService.getNewsById(lang, id));
    }

    @GetMapping("/{lang}/all-news")
    public ResponseEntity<List<NewsDataResponse>> newGetAllNews(@PathVariable("lang") String lang) {
        return ResponseEntity.ok(newsService.newGetAllNews(lang));
    }

    @GetMapping("/{lang}/new-news/{id}")
    public ResponseEntity<NewsDataResponse> newNewsGetById(
            @PathVariable("lang") String lang,
            @PathVariable("id") Long newsId
    ) {
        return ResponseEntity.ok(newsService.newNewsGetById(newsId, lang));
    }

    @PostMapping("/create-news")
    public ResponseEntity<NewsTranslateCreateDataResponse> newCreateNews(@RequestBody News news) {
        return ResponseEntity.ok(newsService.newCreateNews(news));
    }

    @PostMapping("/{newsId}/translation")
    public ResponseEntity<NewsTranslation> addTranslation(@PathVariable("newsId") Long newsId,
                                                          @RequestBody NewsTranslation translation) {
        NewsTranslation addedTranslation = newsService.addTranslation(newsId, translation);
        return ResponseEntity.ok(addedTranslation);
    }

    @PostMapping("/add-language-to-news")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AddNewLanguageResponse> addNewLanguageToNews() {
        return ResponseEntity.status(HttpStatus.CREATED).body(newsService.addNewLanguageToNews());
    }

//===========================Section Post and like======================================================================

    @GetMapping("/{lang}/show-all-likes")
    public ResponseEntity<List<CountLikesResponse>> getAllNewsWithLikes(@PathVariable("lang") String lang) {
        List<CountLikesResponse> countLikesResponses = newsService.getAllNewsWithLikes(lang);
        return ResponseEntity.ok(countLikesResponses);
    }

    @GetMapping("/{lang}/show-like/by/{newsId}")
    public ResponseEntity<CountLikesResponse> getLikesByNewsId(
            @PathVariable("lang") String lang,
            @PathVariable("newsId") Long newsId) {
        return ResponseEntity.ok(newsService.getNewsByIdWithLikes(lang, newsId));
    }

    @PostMapping("/{lang}/like")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CountLikesResponse> likeNews(
            @PathVariable("lang") String lang,
            @RequestBody LikeByUserIdAndNewsIdRequest request
    ) {
        log.info("Поступил запрос на добавление лайка по ID: {} новости и по ID: {} пользователя",
                request.getNewsId(), request.getUserId());
        newsService.addLike(request);
        log.info("Пользователь получил сведения о добавлении/удалении лайка.");
        return ResponseEntity.ok(newsService.getContentWithLikeForClick(lang, request.getNewsId()));
    }

//===========================Section Elastic Search=====================================================================

    @GetMapping("/{lang}/search")
    public ResponseEntity<List<NewsDocumentSearch>> fullTextSearch(
            @PathVariable("lang") String lang,
            @RequestParam("query") String query
    ) {
        return ResponseEntity.ok(newsService.search(lang, query));
    }

    @GetMapping("/index-translations")
    public String indexTranslations() {
        newsService.indexTranslations();
        return "Translations indexed successfully.";
    }
}
