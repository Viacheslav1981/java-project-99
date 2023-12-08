package hexlet.code.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class UserUpdateDTO {
    @Email
    private String email;

    private String firstName;
    private String lastName;

    @NotNull(message = "Password must be longer than 3 characters")
    @Size(min = 3)
    private String password;
}