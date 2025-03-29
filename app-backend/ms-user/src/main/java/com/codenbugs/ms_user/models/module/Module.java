package com.codenbugs.ms_user.models.module;

import com.codenbugs.ms_user.models.page.Page;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "modules")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String path;

    @Column(name = "is_enabled",columnDefinition = "TINYINT")
    private Boolean isEnabled;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "module")
    private List<Page> pages;
}
