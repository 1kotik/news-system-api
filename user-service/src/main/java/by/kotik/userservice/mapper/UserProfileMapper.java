package by.kotik.userservice.mapper;

import by.kotik.userservice.dto.UserProfileDto;
import by.kotik.userservice.entity.User;
import by.kotik.userservice.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    @Mapping(source = "user", target = "userId", qualifiedByName = "getUserId")
    UserProfileDto toDto(UserProfile userProfile);

    @Mapping(target = "user", ignore = true)
    UserProfile toEntity(UserProfileDto userProfileDto);

    @Mapping(target = "profileId", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateUser(UserProfileDto userProfileDto, @MappingTarget UserProfile userProfile);

    @Named("getUserId")
    default UUID getUserId(User user) {
        return user.getUserId();
    }
}
