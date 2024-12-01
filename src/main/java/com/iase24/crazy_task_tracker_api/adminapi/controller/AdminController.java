package com.iase24.crazy_task_tracker_api.adminapi.controller;

import com.iase24.crazy_task_tracker_api.adminapi.facade.UserFacade;
import com.iase24.crazy_task_tracker_api.dto.request.UserUUIDRequest;
import com.iase24.crazy_task_tracker_api.dto.response.DeleteUserResponse;
import com.iase24.crazy_task_tracker_api.dto.response.GetUserResponse;
import com.iase24.crazy_task_tracker_api.entity.ArchiveDeletedUser;
import com.iase24.crazy_task_tracker_api.adminapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "Администраторский API", description = "Интерфейс для бизнес логики")
public class AdminController {

    private final UserFacade userFacade;
    private final UserService userService;

    @Operation(
            summary = "Показать список всех пользователей",
            description = "Показать список всех пользователей",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Invalid", responseCode = "500")
            }
    )
    @GetMapping
    public ResponseEntity<List<GetUserResponse>> getAllUser() {
        log.info("Запрос на просмотр списка всех пользователей");
        return ResponseEntity.ok(userFacade.getAllUser());
    }

    @Operation(
            summary = "Найти пользователя по ID",
            description = "Найти пользователя по ID",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Invalid", responseCode = "500")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable("id") UserUUIDRequest userId) {
        log.info("Запрошен просмотр информации о пользователе с ID: {} ", userId.getUserId());
        return ResponseEntity.ok().body(userFacade.getUserById(UUID.fromString(userId.getUserId())));
    }

    @Operation(
            summary = "Удалить пользователя по его ID",
            description = "Удалить пользователя по его ID",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Invalid", responseCode = "500")
            }
    )
    @DeleteMapping
    public ResponseEntity<DeleteUserResponse> deleteByUserId(@RequestHeader("X-User-Id") UUID uuid) {
        log.info("Запрос на удаление пользователя по ID: {}", uuid);
        return ResponseEntity.ok(userFacade.deleteByUserId(uuid));
    }

    @Operation(
            summary = "Показать архив удаленных пользователей",
            description = "Показать архив удаленных пользователей",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Invalid", responseCode = "500")
            }
    )
    @GetMapping("/archive")
    public ResponseEntity<List<ArchiveDeletedUser>> getArchive() {
        log.info("Запрос на получение списка удаленных пользователей из архива");
        return ResponseEntity.ok(userService.getAllArchives());
    }

    @Operation(
            summary = "Найти удаленного пользователя по тексту сообщения в базе данных",
            description = "Найти удаленного пользователя по тексту сообщения в базе данных",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Invalid", responseCode = "500")
            }
    )
    @GetMapping("/search/archive")
    public ResponseEntity<List<ArchiveDeletedUser>> searchMessage(@RequestParam("text") String text) {
        log.info("Запрос на поиск удаленного пользователя в архиве");
        return ResponseEntity.ok(userService.getMessageBySearch(text));
    }
}
