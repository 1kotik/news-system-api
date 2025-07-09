package by.kotik.userservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RoleDto {
    private UUID roleId;
    private String roleName;
}
