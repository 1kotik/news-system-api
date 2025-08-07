package by.kotik.commentservice.controller;

import by.kotik.commentservice.service.CommentLikeService;
import dto.LikeRequest;
import dto.LikeResponseDto;
import enums.LikeType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments/like")
public class CommentLikeController {
    private final CommentLikeService commentLikeService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{commentId}")
    public ResponseEntity<LikeResponseDto> likeComment(@PathVariable UUID commentId,
                                                       @RequestBody LikeRequest likeRequest) {
        LikeResponseDto likeResponseDto = commentLikeService.likeComment(commentId, likeRequest);
        return ResponseEntity.ok(likeResponseDto);
    }
}
