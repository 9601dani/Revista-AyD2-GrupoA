package com.codenbugs.ms_ads.models.ads;

import com.codenbugs.ms_ads.models.Label;
import com.codenbugs.ms_ads.models.categories.Category;
import com.codenbugs.ms_ads.models.periods.Period;
import com.codenbugs.ms_ads.models.users.User;
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
    @JoinColumn(name = "FK_Period")
    private Period period;

    @ManyToOne
    @JoinColumn(name = "FK_Ad_Types")
    private AdType adType;

    @ManyToOne
    @JoinColumn(name = "FK_User")
    private User user;

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
