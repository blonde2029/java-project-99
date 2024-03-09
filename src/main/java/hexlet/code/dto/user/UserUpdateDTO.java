package hexlet.code.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class UserUpdateDTO {
    @NotNull
    private JsonNullable<String> firstName;
    @NotNull
    private JsonNullable<String> lastName;


    @Email
    @NotBlank
    private JsonNullable<String> email;

    @NotNull
    @Size(min = 3)
    private JsonNullable<String> passwordDigest;
}
