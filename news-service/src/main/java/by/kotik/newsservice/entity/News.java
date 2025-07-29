package by.kotik.newsservice.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "news")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "news_id", nullable = false, unique = true)
    private UUID newsId;
    @Column(name = "author_id", nullable = false)
    private UUID authorId;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "preview_image_url")
    private String previewImageUrl;
    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private ZonedDateTime createdAt;
    @Column(name = "likes_count", nullable = false)
    private int likesCount;
    @Column(name = "dislikes_count", nullable = false)
    private int dislikesCount;
    @Column(name = "comments_count", nullable = false)
    private int commentsCount;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "news_categories",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Category> categories = new ArrayList<>();
    @OneToMany(mappedBy = "news")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Like> likes = new ArrayList<>();
}
