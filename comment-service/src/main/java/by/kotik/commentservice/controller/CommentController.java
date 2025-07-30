package by.kotik.commentservice.controller;

import by.kotik.commentservice.dto.CommentContentDto;
import by.kotik.commentservice.dto.CommentDto;
import by.kotik.commentservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentDto>> findAll() {
        List<CommentDto> comments = commentService.findAll();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{newsId}")
    public ResponseEntity<List<CommentDto>> findByNewsId(@PathVariable UUID newsId) {
        List<CommentDto> comments = commentService.findByNewsId(newsId);
        return ResponseEntity.ok(comments);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{newsId}")
    public ResponseEntity<CommentDto> create(@RequestBody CommentContentDto commentContentDto,
                                             @PathVariable UUID newsId,
                                             @RequestParam(name = "parentId", required = false) UUID parentCommentId) {
        CommentDto commentDto = commentService.create(commentContentDto, newsId, parentCommentId);
        return ResponseEntity.ok(commentDto);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> update(@RequestBody CommentContentDto commentContentDto,
                                             @PathVariable UUID commentId) {
        CommentDto commentDto = commentService.update(commentContentDto, commentId);
        return ResponseEntity.ok(commentDto);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable UUID commentId) {
        commentService.delete(commentId);
        return ResponseEntity.noContent().build();
    }
}
