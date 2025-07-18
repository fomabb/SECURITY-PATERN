package com.iase24.crazy_task_tracker_api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.awt.*;
import java.math.BigDecimal;

@Entity
@Table(name = "delivery_location")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Доступные зоны доставки")
public class DeliveryLocation extends AbstractEntity {

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "price_rub")
    private BigDecimal price;

    @Column(name = "fill")
    private String fill;

    @Column(name = "polygon", columnDefinition = "geometry(Polygon, 4326)")
    private Polygon polygon;
}
