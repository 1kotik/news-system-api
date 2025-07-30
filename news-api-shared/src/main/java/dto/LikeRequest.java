package dto;

import enums.LikeType;
import lombok.Data;

@Data
public class LikeRequest {
    private LikeType likeType;
}
