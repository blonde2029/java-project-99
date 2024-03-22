package hexlet.code.dto.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email
    @Column(unique = true)
    @NotBlank
    private String email;
    @Size(min = 3)
    private String password;
}
