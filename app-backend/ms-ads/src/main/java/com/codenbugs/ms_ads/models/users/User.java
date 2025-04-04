package com.codenbugs.ms_ads.models.users;

import com.codenbugs.ms_ads.models.Label;
import com.codenbugs.ms_ads.models.ads.Ad;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_has_labels",
            joinColumns = @JoinColumn(name = "FK_User"),
            inverseJoinColumns = @JoinColumn(name = "FK_Label")
    )
    private List<Label> labels = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Ad> ads = new ArrayList<>();
}
