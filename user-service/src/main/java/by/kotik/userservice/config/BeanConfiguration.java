package by.kotik.userservice.config;

import by.kotik.userservice.mapper.RoleMapper;
import by.kotik.userservice.mapper.UserMapper;
import by.kotik.userservice.mapper.UserProfileMapper;
import by.kotik.userservice.repository.RoleRepository;
import by.kotik.userservice.repository.UserProfileRepository;
import by.kotik.userservice.repository.UserRepository;
import by.kotik.userservice.service.DefaultRoleService;
import by.kotik.userservice.service.DefaultUserProfileService;
import by.kotik.userservice.service.DefaultUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    /*private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;


    @Bean(name = "defaultUserService")
    public DefaultUserService defaultUserService() {
        return new DefaultUserService(userRepository, userMapper, defaultRoleService());
    }

    @Bean(name = "defaultRoleService")
    public DefaultRoleService defaultRoleService() {
        return new DefaultRoleService(roleRepository, roleMapper);
    }

    @Bean(name = "defaultUserProfileService")
    public DefaultUserProfileService defaultUserProfileService() {
        return new DefaultUserProfileService(userProfileRepository, userProfileMapper);
    }*/
}
