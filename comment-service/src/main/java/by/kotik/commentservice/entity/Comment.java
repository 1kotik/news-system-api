package by.kotik.commentservice.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "comment_id", nullable = false, unique = true)
    private UUID commentId;
    @Column(name = "news_id", nullable = false)
    private UUID newsId;
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private ZonedDateTime createdAt;
    @Column(name = "likes_count", nullable = false)
    private int likesCount;
    @Column(name = "dislikes_count", nullable = false)
    private int dislikesCount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id", referencedColumnName = "comment_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Comment parentComment;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "parentComment", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Comment> childComments = new ArrayList<>();
    @OneToMany(mappedBy = "comment")
    private List<CommentLike> likes = new ArrayList<>();
}
