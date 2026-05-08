package com.brickart.hotelmanagement;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Service {

    private Long id;
    private String name;
    private BigDecimal price;
    private LocalDateTime orderedAt;

    public Service(Long id, String name, BigDecimal price, LocalDateTime orderedAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.orderedAt = orderedAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDateTime getOrderedAt() {
        return orderedAt;
    }
}
