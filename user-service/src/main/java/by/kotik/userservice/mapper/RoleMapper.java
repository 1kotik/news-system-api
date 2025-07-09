package by.kotik.userservice.mapper;

import by.kotik.userservice.dto.RoleDto;
import by.kotik.userservice.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toDto(Role role);
    Role toEntity(RoleDto roleDto);
}
