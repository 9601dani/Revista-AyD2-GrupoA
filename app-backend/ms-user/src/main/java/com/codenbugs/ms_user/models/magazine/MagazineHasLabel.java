package com.codenbugs.ms_user.models.magazine;

import com.codenbugs.ms_user.models.labels.Label;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "magazine_has_labels")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MagazineHasLabel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "FK_Label")
    private Label label;

    @ManyToOne
    @JoinColumn(name = "FK_Magazine")
    private Magazine magazine;




}
