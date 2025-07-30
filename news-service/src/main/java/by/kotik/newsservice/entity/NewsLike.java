package by.kotik.newsservice.entity;

import enums.LikeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "likes")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class NewsLike {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "like_id", nullable = false, unique = true)
    private UUID likeId;
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id", referencedColumnName = "news_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private News news;
    @Enumerated(value = EnumType.STRING)
    private LikeType likeType;

    public NewsLike(UUID userId, News news, LikeType likeType) {
        this.userId = userId;
        this.news = news;
        this.likeType = likeType;
    }
}
