package com.iase24.crazy_task_tracker_api.security.controller;

import com.iase24.crazy_task_tracker_api.security.service.UserServiceSecurity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/example")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class ExampleController {
    private final UserServiceSecurity service;

    @Operation(summary = "Доступен только авторизованным пользователям")
    @GetMapping
    public String example() {
        return "Hello, world!";
    }

    @Operation(summary = "Доступен только авторизованным пользователям с ролью ADMIN")
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String exampleAdmin() {
        return "Hello, admin!";
    }

    @GetMapping("/get-admin")
    @Operation(summary = "Получить роль ADMIN (для демонстрации)")
    public void getAdmin() {
        service.getAdmin();
    }
}
