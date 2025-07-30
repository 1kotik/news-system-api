package by.kotik.commentservice.entity;

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
@Table(name = "comment_likes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "like_id", nullable = false, unique = true)
    private UUID likeId;
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "like_type", nullable = false)
    private LikeType likeType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", referencedColumnName = "comment_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Comment comment;

    public CommentLike(UUID userId, Comment comment, LikeType likeType) {
        this.userId = userId;
        this.comment = comment;
        this.likeType = likeType;
    }
}
