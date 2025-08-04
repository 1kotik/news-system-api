package by.kotik.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPublicInfoDto {
    private String publicName;
    private String bio;
    private String location;
}
