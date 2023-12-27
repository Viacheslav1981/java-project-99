package hexlet.code.dto.user;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {
    @Email
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
