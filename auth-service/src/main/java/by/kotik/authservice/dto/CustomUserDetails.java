package by.kotik.authservice.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

public class CustomUserDetails extends User {
    private String email;
    private UUID userId;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
                             String email, UUID userId) {
        super(username, password, authorities);
        this.email = email;
        this.userId = userId;
    }

    /*public CustomUserDetails(UserDetailsDto userDetailsDto) {
        super(userDetailsDto.getUsername(), userDetailsDto.getPassword(),
                userDetailsDto.getRoles().stream()
                        .map(SimpleGrantedAuthority::new)
                                            .toList());
        this.email = userDetailsDto.getEmail();
        this.userId = userDetailsDto.getUserId();
    }*/

    public String getEmail() {
        return email;
    }

    public UUID getUserId() {
        return userId;
    }
}
