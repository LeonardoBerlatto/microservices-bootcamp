package br.com.photoapp.api.usermanagement.web.representation.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CreateUserRequest {

    @Size(min = 3, message = "First name must contain at least 3(three) characters.")
    @NotBlank(message = "First name must not be null.")
    private String firstName;

    @Size(min = 2, message = "Last name must contain at least 2(two) characters.")
    @NotBlank(message = "Last name must not be null.")
    private String lastName;

    @Size(min = 4, message = "Username must contain at least 4(four) characters.")
    @NotBlank(message = "Username must not be null.")
    private String username;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email name must not be null.")
    private String email;

    @Size(min = 6, message = "Password must contain at least 6(six) characters.")
    @NotBlank(message = "Password name must not be null.")
    private String password;

    @Past(message = "Invalid birth date.")
    @NotNull(message = "Birth date must not be null.")
    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate birthDate;

}
