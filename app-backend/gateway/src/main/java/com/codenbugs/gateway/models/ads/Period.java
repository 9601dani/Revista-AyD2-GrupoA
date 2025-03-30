package com.codenbugs.gateway.models.ads;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "periods")
@Data
public class Period {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private BigDecimal cost = BigDecimal.ZERO;

}
