package by.kotik.userservice.mapper;

import by.kotik.userservice.dto.RoleDto;
import by.kotik.userservice.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toDto(Role role);
    Role toEntity(RoleDto roleDto);

    String toString(Role role);
}
