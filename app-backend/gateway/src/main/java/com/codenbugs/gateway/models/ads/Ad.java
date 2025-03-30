package com.codenbugs.gateway.models.ads;

import com.codenbugs.gateway.models.Label;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ads")
@Data
public class Ad {

    @Id
    private Integer id;
    private String content;
    private String path;

    @Column(name = "number_views")
    private Integer numberViews = 0;
    @Column(name = "date_created")
    private LocalDate dateCreated = LocalDate.now();
    @Column(name = "date_ended")
    private LocalDate dateEnded = LocalDate.now();

    @Column(name = "is_enabled", columnDefinition = "TINYINT")
    private Boolean isEnabled = true;

    @ManyToOne
    private Period period;

    @ManyToOne
    private AdType adType;

    @ManyToMany
    @JoinTable(
            name = "ad_has_labels",
            joinColumns = @JoinColumn(name = "FK_Ad"),
            inverseJoinColumns = @JoinColumn(name = "FK_Label")
    )
    private List<Label> labels = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "ad_has_categories",
            joinColumns = @JoinColumn(name = "FK_Ad"),
            inverseJoinColumns = @JoinColumn(name = "FK_Category")
    )
    private List<Category> categories = new ArrayList<>();
}
