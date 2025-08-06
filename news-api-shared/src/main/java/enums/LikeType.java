package enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum LikeType {
    DISLIKE("dislike"), LIKE("like");
    private final String likeType;

    @JsonCreator
    public static LikeType getLikeType(String likeType) {
        return Arrays.stream(LikeType.values())
                .filter(type -> type.likeType.equals(likeType.toLowerCase()))
                .findFirst()
                .orElse(LIKE);
    }

    public static LikeType getOppositeLikeType(LikeType likeType) {
        if(likeType == DISLIKE) {
            return LIKE;
        }
        return DISLIKE;
    }

    @Override
    @JsonValue
    public String toString() {
        return likeType;
    }
}
