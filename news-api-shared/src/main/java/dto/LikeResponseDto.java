package dto;

import enums.LikeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeResponseDto {
    private UUID likeableId;
    private int likesCount;
    private int dislikesCount;
    private LikeType likeType;
}
