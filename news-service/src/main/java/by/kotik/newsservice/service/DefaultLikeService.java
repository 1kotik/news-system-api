package by.kotik.newsservice.service;

import by.kotik.newsservice.dto.LikeRequest;
import by.kotik.newsservice.entity.Like;
import by.kotik.newsservice.entity.News;
import by.kotik.newsservice.helpers.enums.LikeType;
import by.kotik.newsservice.mapper.LikeMapper;
import by.kotik.newsservice.repository.LikeRepository;
import dto.UserAuthorizationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultLikeService implements LikeService {
    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;
    private final NewsService newsService;
    private final UserAuthorizationDto userAuthorizationDto;


    @Override
    @Transactional
    public void likeNews(UUID newsId, LikeRequest likeRequest) {
        UUID userId = userAuthorizationDto.getUserId();
        News news = newsService.findById(newsId);
        Optional<Like> likeOptional = likeRepository.getLikeByUserIdAndNews(userId, news);
        LikeType likeType = likeRequest.getLikeType();

        likeOptional.ifPresentOrElse(like -> changeLikeType(like, likeType, news),
                () -> setNewLike(userId, news, likeType));
    }

    private void changeLikeType(Like like, LikeType likeType, News news) {
        if (like.getLikeType().equals(likeType)) {
            likeRepository.delete(like);
            changeNewsLikesCount(news, likeType, -1);
        } else {
            LikeType oppositeLikeType = LikeType.getOppositeLikeType(likeType);
            like.setLikeType(likeType);
            changeNewsLikesCount(news, oppositeLikeType, -1);
            changeNewsLikesCount(news, likeType, 1);
        }
    }

    private void changeNewsLikesCount(News news, LikeType likeType, int delta) {
        if(likeType.equals(LikeType.LIKE)) {
            news.setLikesCount(news.getLikesCount() + delta);
        } else {
            news.setDislikesCount(news.getDislikesCount() + delta);
        }
    }

    private void setNewLike(UUID userId, News news, LikeType likeType) {
        Like like = new Like(userId, news, likeType);
        likeRepository.save(like);
        changeNewsLikesCount(news, likeType, 1);
    }
}
