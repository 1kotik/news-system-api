package by.kotik.userservice.controller;

import by.kotik.userservice.dto.RoleDto;
import by.kotik.userservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/roles")
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }
}
