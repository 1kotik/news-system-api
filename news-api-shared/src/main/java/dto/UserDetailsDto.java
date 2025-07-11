package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {
    private UUID userId;
    private String password;
    private String username;
    private String email;
    private List<String> roles = new ArrayList<>();
}
