package com.iase24.crazy_task_tracker_api.businessapi.controller;

import com.iase24.crazy_task_tracker_api.businessapi.dto.ShopDto;
import com.iase24.crazy_task_tracker_api.businessapi.dto.request.ShopAddRequest;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.LocationClientResponse;
import com.iase24.crazy_task_tracker_api.businessapi.projection.ShopProjection;
import com.iase24.crazy_task_tracker_api.businessapi.service.ClientService;
import com.iase24.crazy_task_tracker_api.businessapi.service.ShopService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/add-shop")
    public ResponseEntity<ShopDto> addNewShop(@RequestBody @Valid ShopAddRequest request) {
        ShopDto created = shopService.addShop(request);

        return ResponseEntity.created(URI.create("/api/v1/offices/" + created.id())).body(created);
    }

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

    @GetMapping("/distance/closest")
    public ResponseEntity<ShopDto> getClosestShop(
            @RequestParam(value = "lat") double lat,
            @RequestParam(value = "lon") double lon) {

        ShopProjection shop = shopService.getClosestShop(lat, lon);
        return shop != null
                ? ResponseEntity.ok(convertToDto(shop))
                : ResponseEntity.notFound().build();
    }

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
