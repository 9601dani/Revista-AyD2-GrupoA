package com.codenbugs.ms_user.models.magazine;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "FK_Suscription")
    private Suscription suscription;

    @ManyToOne
    @JoinColumn(name = "FK_Magazine")
    private Magazine magazine;

    private String content;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;
}
