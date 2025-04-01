package com.codenbugs.ms_user.models.magazine;

import com.codenbugs.ms_user.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "suscription")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Suscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TINYINT", name = "is_like")
    private Boolean isLike;

    @ManyToOne
    @JoinColumn(name = "FK_User")
    private User user;

    @ManyToOne
    @JoinColumn(name = "FK_Magazine")
    private Magazine magazine;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "date_ended")
    private LocalDate dateEnded;

    @Column(columnDefinition = "TINYINT", name = "is_enabled")
    private Boolean isEnabled;

    private BigDecimal pay;
}
