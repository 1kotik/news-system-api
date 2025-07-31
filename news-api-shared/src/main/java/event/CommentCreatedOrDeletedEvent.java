package event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreatedOrDeletedEvent {
    private UUID newsId;
    private UUID commentId;
    private boolean created;
}
