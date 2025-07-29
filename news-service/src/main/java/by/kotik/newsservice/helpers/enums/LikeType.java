package by.kotik.newsservice.helpers.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum LikeType {
    DISLIKE("dislike"), LIKE("like");
    private final String likeType;

    public static LikeType getLikeType(String likeType) {
        return Arrays.stream(LikeType.values())
                .filter(type -> type.likeType.equals(likeType))
                .findFirst()
                .orElse(LIKE);
    }
}
