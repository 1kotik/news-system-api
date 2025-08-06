package by.kotik.userservice.mapper;

import by.kotik.userservice.dto.UserProfileDto;
import by.kotik.userservice.dto.UserPublicInfoDto;
import by.kotik.userservice.entity.User;
import by.kotik.userservice.entity.UserProfile;
import by.kotik.userservice.repository.UserRepository;
import dto.UserPreviewDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Optional;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    @Mapping(source = "user", target = "userId", qualifiedByName = "getUserId")
    UserProfileDto toDto(UserProfile userProfile);

    @Mapping(target = "user", ignore = true)
    UserProfile toEntity(UserProfileDto userProfileDto);

    @Mapping(target = "profileId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    void updateUser(UserPublicInfoDto userPublicInfoDto, @MappingTarget UserProfile userProfile);

    @Mapping(source = "avatar", target = "avatarUrl")
    @Mapping(source = "userProfile", target = "displayedName", qualifiedByName = "getDisplayedName")
    @Mapping(source = "user", target = "userId", qualifiedByName = "getUserId")
    UserPreviewDto toUserPreviewDto(UserProfile userProfile, @Context UserRepository userRepository);

    @Named("getUserId")
    default UUID getUserId(User user) {
        return user.getUserId();
    }

    @Named("getDisplayedName")
    default String getDisplayedName(UserProfile userProfile, @Context UserRepository userRepository) {
        String publicName = userProfile.getPublicName();
        UUID userId = userProfile.getUser().getUserId();

        if (publicName != null && !publicName.isEmpty()) {
            return publicName;
        }

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            return user.get().getUsername();
        }

        return "Deleted User";
    }
}
