package com.codenbugs.ms_user.models.labels;

import com.codenbugs.ms_user.models.magazine.Magazine;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "labels")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "magazine_has_labels",
            joinColumns = @JoinColumn(name = "FK_Label"),
            inverseJoinColumns = @JoinColumn(name = "FK_Magazine")
    )
    private List<Magazine> magazines;
}
