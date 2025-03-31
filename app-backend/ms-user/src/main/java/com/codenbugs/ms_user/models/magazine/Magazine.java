package com.codenbugs.ms_user.models.magazine;

import com.codenbugs.ms_user.enums.MagazineType;
import com.codenbugs.ms_user.models.labels.Label;
import com.codenbugs.ms_user.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "magazines")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Magazine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name ="FK_User")
    private User user;

    private String description;

    @Column(columnDefinition = "TINYINT", name = "can_comment")
    private Boolean canComment;

    @Column(columnDefinition = "TINYINT", name = "can_like")
    private boolean canLike;

    @Column(columnDefinition = "TINYINT", name = "can_subscribe")
    private boolean canSubscribe;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('PAID', 'FREE')")
    private MagazineType type;

    private BigDecimal price;

    @Column(name = "date_created")
    private LocalDateTime dateCreated = LocalDateTime.now();

    @Column(columnDefinition = "TINYINT")
    private boolean isEnabled;

    @OneToMany(mappedBy = "magazine", fetch = FetchType.LAZY)
    private List<Document> documents;

    @ManyToMany(mappedBy = "magazines", fetch = FetchType.LAZY)
    private List<Category> categories;

    @ManyToMany(mappedBy = "magazines", fetch = FetchType.LAZY)
    private List<Label> labels;
}
