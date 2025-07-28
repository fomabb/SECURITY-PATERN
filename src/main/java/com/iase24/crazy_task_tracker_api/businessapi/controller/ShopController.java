package com.iase24.crazy_task_tracker_api.businessapi.controller;

import com.iase24.crazy_task_tracker_api.businessapi.dto.ShopDto;
import com.iase24.crazy_task_tracker_api.businessapi.dto.ShopRouteInfoDto;
import com.iase24.crazy_task_tracker_api.businessapi.dto.request.ShopAddRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.LocationClientResponse;
import com.iase24.crazy_task_tracker_api.businessapi.projection.ShopProjection;
import com.iase24.crazy_task_tracker_api.businessapi.service.ClientService;
import com.iase24.crazy_task_tracker_api.businessapi.service.RoutingService.RoutingMode;
import com.iase24.crazy_task_tracker_api.businessapi.service.ShopService;
import com.iase24.crazy_task_tracker_api.dto.exception.CommonExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

import static com.iase24.crazy_task_tracker_api.filter.IpLoggingFilter.getClientIP;

@RestController
@RequestMapping("/api/v1/offices")
@RequiredArgsConstructor
@Tag(name = "Офисы на карте")
public class ShopController {

    private final ShopService shopService;
    private final ClientService clientService;

    @Operation(
            summary = "Добавить новый офис для мерчендайзеров.",
            description = """
                    `Добавляет новый магазин для определения глоданных`
                    """,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShopAddRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "`Объект успешно добавлен`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema())
                    ),
                    @ApiResponse(responseCode = "400", description = "`Некорректный запрос`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @PostMapping("/add-shop")
    public ResponseEntity<ShopDto> addNewShop(@RequestBody @Valid ShopAddRequest request) {
        ShopDto created = shopService.addShop(request);

        return ResponseEntity.created(URI.create("/api/v1/offices/" + created.id())).body(created);
    }

    @Operation(
            summary = "Обнаружение всех объектов офиса от своих координат.",
            description = """
                    `Автоматически вычисляет свои координаты и показывает все объекты офиса`
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "`Список объектов офиса успешно возвращен`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ShopDto.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "`Пользователь не найден`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @GetMapping("/distance/all-shops-from-my-position")
    public ResponseEntity<List<ShopDto>> getAllShopsFromMyPosition(HttpServletRequest request) {

        String ipAddressClient = getClientIP(request);
        LocationClientResponse cityByIpClient = clientService.getCityByIpClient(null, ipAddressClient);

        double lat = Double.parseDouble(cityByIpClient.getLat());
        double lon = Double.parseDouble(cityByIpClient.getLon());

        List<ShopDto> result = shopService.getAllShopsOrderedByDistance(lat, lon)
                .stream()
                .map(this::convertToDto)
                .toList();

        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "Найти ближайший объект офиса.",
            description = """
                    `Необходимо вставить широту и долготу для обнаружения ближайшего объекта офиса`
                    """,
            parameters = {
                    @Parameter(name = "lat", required = true, description = "Широта по оси Y.", example = "52.198938"),
                    @Parameter(name = "lon", required = true, description = "Долгота по оси X.", example = "24.038436")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "`Объект офиса успешно возвращен`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ShopDto.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "`Пользователь не найден`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @GetMapping("/distance/closest")
    public ResponseEntity<ShopDto> getClosestShop(
            @RequestParam(value = "lat") double lat,
            @RequestParam(value = "lon") double lon) {

        ShopProjection shop = shopService.getClosestShop(lat, lon);
        return shop != null
                ? ResponseEntity.ok(convertToDto(shop))
                : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Найти все объект офиса.",
            description = """
                    `Необходимо вставить широту и долготу для обнаружения всех объектова офиса`
                    """,
            parameters = {
                    @Parameter(name = "lat", required = true, description = "Широта по оси Y.", example = "52.198938"),
                    @Parameter(name = "lon", required = true, description = "Долгота по оси X.", example = "24.038436")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "`Список объектов офиса успешно возвращен`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ShopDto.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "`Пользователь не найден`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @GetMapping("/distance/all")
    public ResponseEntity<List<ShopDto>> getAllShopsOrderedByDistance(
            @RequestParam double lat,
            @RequestParam double lon) {

        List<ShopDto> result = shopService.getAllShopsOrderedByDistance(lat, lon)
                .stream()
                .map(this::convertToDto)
                .toList();

        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "Найти объект офиса по его ID.",
            description = """
                    `Необходимо вставить ID объекта, а также свои широту и долготу для определения дистанции до объекта офиса`
                    """,
            parameters = {
                    @Parameter(name = "officeId", required = true, description = "ID объекта офиса", example = "7"),
                    @Parameter(name = "lat", required = true, description = "Широта по оси Y.", example = "52.198938"),
                    @Parameter(name = "lon", required = true, description = "Долгота по оси X.", example = "24.038436")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "`Объект офиса успешно возвращен`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ShopDto.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "`Пользователь не найден`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @GetMapping("/distance/{officeId}")
    public ResponseEntity<ShopDto> getShopDistanceById(
            @PathVariable("officeId") Long id,
            @RequestParam double lat,
            @RequestParam double lon
    ) {
        ShopProjection shop = shopService.getShopDistanceById(id, lat, lon);
        return shop != null
                ? ResponseEntity.ok(convertToDto(shop))
                : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Найти ближайший объект офиса с маршрутом.",
            description = """
                    `
                    Необходимо вставить свои широту и долготу для определения дистанции до объекта
                    офиса, а также можно указать параметр mode=DRIVING, WALKING или BICYCLE
                    `
                    """,
            parameters = {@Parameter(name = "lat", required = true, description = "Широта по оси Y.", example = "52.198938"),
                    @Parameter(name = "lon", required = true, description = "Долгота по оси X.", example = "24.038436"),
                    @Parameter(
                            name = "mode",
                            description = "Режим маршрутизации",
                            example = "DRIVING",
                            schema = @Schema(
                                    implementation = RoutingMode.class,
                                    allowableValues = {"DRIVING", "WALKING", "BICYCLE"}
                            )
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "`Объект офиса успешно возвращен`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ShopDto.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "`Пользователь не найден`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @GetMapping("/nearest-with-route")
    public ResponseEntity<ShopRouteInfoDto> getClosestShopWithRoute(
            @RequestParam @Min(-90) @Max(90) double lat,
            @RequestParam @Min(-180) @Max(180) double lon,
            @RequestParam(defaultValue = "DRIVING") RoutingMode mode
    ) {
        ShopRouteInfoDto result = shopService.getClosestShopWithRoute(lat, lon, mode);
        return result != null
                ? ResponseEntity.ok(result)
                : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Найти объект офиса по его ID с маршрутом.",
            description = """
                    `
                    Необходимо вставить ID объекта, а также свои широту и долготу для определения дистанции до объекта
                    офиса, а также можно указать параметр mode=DRIVING, WALKING или BICYCLE
                    `
                    """,
            parameters = {
                    @Parameter(name = "id", required = true, description = "ID объекта офиса", example = "7"),
                    @Parameter(name = "lat", required = true, description = "Широта по оси Y.", example = "52.198938"),
                    @Parameter(name = "lon", required = true, description = "Долгота по оси X.", example = "24.038436"),
                    @Parameter(
                            name = "mode",
                            description = "Режим маршрутизации",
                            example = "DRIVING",
                            schema = @Schema(
                                    implementation = RoutingMode.class,
                                    allowableValues = {"DRIVING", "WALKING", "BICYCLE"}
                            )
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "`Объект офиса успешно возвращен`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ShopDto.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "`Пользователь не найден`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @GetMapping("/nearest-with-route/{id}")
    public ResponseEntity<ShopRouteInfoDto> getShopWithRouteById(
            @PathVariable("id") Long id,
            @RequestParam @Min(-90) @Max(90) double lat,
            @RequestParam @Min(-180) @Max(180) double lon,
            @RequestParam(defaultValue = "DRIVING") RoutingMode mode
    ) {
        ShopRouteInfoDto result = shopService.getShopWithRouteById(id, lat, lon, mode);
        return result != null
                ? ResponseEntity.ok(result)
                : ResponseEntity.notFound().build();
    }

    /**
     * Преобразует ShopProjection в ShopDto
     *
     * @param projection проекция из БД
     * @return DTO для клиента
     */
    private ShopDto convertToDto(ShopProjection projection) {
        return new ShopDto(
                projection.getId(),
                projection.getName(),
                projection.getAddress(),
                projection.getWorkingHours(),
                projection.getDistance()
        );
    }
}
