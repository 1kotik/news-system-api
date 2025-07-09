package by.kotik.userservice.service;

import by.kotik.userservice.dto.RoleDto;
import by.kotik.userservice.entity.Role;
import by.kotik.userservice.mapper.RoleMapper;
import by.kotik.userservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class DefaultRoleService implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Transactional(readOnly = true)
    public List<RoleDto> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Role getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new NoSuchElementException("Role Not Found"));
    }
}
