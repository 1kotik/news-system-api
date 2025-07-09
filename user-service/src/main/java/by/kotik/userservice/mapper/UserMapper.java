package by.kotik.userservice.mapper;

import by.kotik.userservice.dto.UserCreationDto;
import by.kotik.userservice.dto.UserDto;
import by.kotik.userservice.dto.UserNicknameAndEmailDto;
import by.kotik.userservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserProfileMapper.class, RoleMapper.class})
public interface UserMapper {
    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "userProfile", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User userCreationDtoToUser(UserCreationDto userCreationDto);

    UserNicknameAndEmailDto userCreationDtoToUserNicknameAndEmailDto(UserCreationDto userCreationDto);
}
