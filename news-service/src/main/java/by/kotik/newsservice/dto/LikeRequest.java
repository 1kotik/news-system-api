package by.kotik.newsservice.dto;

import by.kotik.newsservice.helpers.enums.LikeType;
import lombok.Data;

@Data
public class LikeRequest {
    private LikeType likeType;
}
