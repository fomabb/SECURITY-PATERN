package com.iase24.crazy_task_tracker_api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "Клиентский API", description = "Интерфейс для бизнес логики")
public class ClientController {


}
