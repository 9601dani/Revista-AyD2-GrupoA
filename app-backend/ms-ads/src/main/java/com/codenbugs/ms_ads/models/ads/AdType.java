package com.codenbugs.ms_ads.models.ads;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ad_types")
@Data
public class AdType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
