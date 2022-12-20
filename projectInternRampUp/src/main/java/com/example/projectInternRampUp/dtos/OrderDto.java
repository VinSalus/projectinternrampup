package com.example.projectInternRampUp.dtos;

import com.example.projectInternRampUp.entities.Order;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
public class OrderDto implements Serializable {
    //This Dto handles what informations are displayed on a get all orders request.
    private static final long serialVersionUID = 1L;
    private Integer id;

    private Instant instant;

    public OrderDto() {
        super();
        // TODO Auto-generated constructor stub
    }

    public OrderDto(Order obj) {
        super();
        this.id = obj.getId();
        this.instant = obj.getInstant();
    }
}