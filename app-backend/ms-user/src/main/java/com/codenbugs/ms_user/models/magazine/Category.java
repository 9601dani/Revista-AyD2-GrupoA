package com.codenbugs.ms_user.models.magazine;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "magazine_has_categories",
            joinColumns = @JoinColumn(name = "FK_Category"),
            inverseJoinColumns = @JoinColumn(name = "FK_Magazine")
    )
    private List<Magazine> magazines = new ArrayList<>();

}
