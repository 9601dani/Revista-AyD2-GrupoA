package com.codenbugs.ms_user.models.magazine;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="magazine_has_categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MagazineHasCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "FK_Category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "FK_Magazine")
    private Magazine magazine;

}
