package by.kotik.authservice.mapper;

import by.kotik.authservice.dto.CustomUserDetails;
import by.kotik.authservice.dto.UserRegistrationDto;
import dto.UserAuthorizationDto;
import dto.UserDetailsDto;
import dto.UserRegistrationTransitiveDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "roles", target = "authorities", qualifiedByName = "mapRolesToAuthorities")
    CustomUserDetails userDetailsDtoToCustomUserDetails(UserDetailsDto userDetailsDto);

    @Mapping(source = "authorities", target = "roles", qualifiedByName = "mapAuthoritiesToRoles")
    UserAuthorizationDto customUserDetailsDtoToUserAuthorizationDto(CustomUserDetails customUserDetails);

    UserRegistrationTransitiveDto toTransitiveDto(UserRegistrationDto userRegistrationDto);

    @Named("mapRolesToAuthorities")
    default Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<String> roles) {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Named("mapAuthoritiesToRoles")
    default List<String> mapAuthoritiesToRoles(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::toString)
                .toList();
    }
}
