package by.kotik.userservice.mapper;

import by.kotik.userservice.dto.UserCreationDto;
import by.kotik.userservice.dto.UserDto;
import by.kotik.userservice.entity.Role;
import by.kotik.userservice.entity.User;
import dto.UserAuthorizationDto;
import dto.UserDetailsDto;
import dto.UserRegistrationTransitiveDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserProfileMapper.class, RoleMapper.class})
public interface UserMapper {
    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "userProfile", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User userCreationDtoToUser(UserCreationDto userCreationDto);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapRoleToString")
    UserDetailsDto userToUserDetailsDto(User user);

    UserCreationDto transitiveDtoToUserCreationDto(UserRegistrationTransitiveDto transitiveDto);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapRoleToString")
    UserAuthorizationDto userToUserAuthorizationDto(User user);

    @Named("mapRoleToString")
    default List<String> mapRoleToString(List<Role> roles) {
        return roles.stream()
                .map(Role::getRoleName)
                .toList();
    }

}
