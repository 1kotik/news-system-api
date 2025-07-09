package by.kotik.userservice.service;

import by.kotik.userservice.dto.RoleDto;
import by.kotik.userservice.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    List<RoleDto> findAll();
    Role getRoleByName(String roleName);
}
