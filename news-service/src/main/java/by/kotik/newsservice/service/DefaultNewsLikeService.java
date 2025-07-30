package by.kotik.newsservice.service;

import by.kotik.newsservice.entity.News;
import by.kotik.newsservice.entity.NewsLike;
import by.kotik.newsservice.mapper.NewsLikeMapper;
import by.kotik.newsservice.repository.NewsLikeRepository;
import dto.LikeRequest;
import dto.UserAuthorizationDto;
import enums.LikeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultNewsLikeService implements NewsLikeService {
    private final NewsLikeRepository likeRepository;
    private final NewsLikeMapper newsLikeMapper;
    private final NewsService newsService;
    private final UserAuthorizationDto userAuthorizationDto;


    @Override
    @Transactional
    public void likeNews(UUID newsId, LikeRequest likeRequest) {
        UUID userId = userAuthorizationDto.getUserId();
        News news = newsService.findById(newsId);
        Optional<NewsLike> likeOptional = likeRepository.getLikeByUserIdAndNews(userId, news);
        LikeType likeType = likeRequest.getLikeType();

        likeOptional.ifPresentOrElse(like -> changeLikeType(like, likeType, news),
                () -> setNewLike(userId, news, likeType));
    }

    private void changeLikeType(NewsLike newsLike, LikeType likeType, News news) {
        if (newsLike.getLikeType().equals(likeType)) {
            likeRepository.delete(newsLike);
            changeNewsLikesCount(news, likeType, -1);
        } else {
            LikeType oppositeLikeType = LikeType.getOppositeLikeType(likeType);
            newsLike.setLikeType(likeType);
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
        NewsLike newsLike = new NewsLike(userId, news, likeType);
        likeRepository.save(newsLike);
        changeNewsLikesCount(news, likeType, 1);
    }
}
