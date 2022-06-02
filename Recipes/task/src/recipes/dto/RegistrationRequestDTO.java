package recipes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import recipes.validation.CorrectEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDTO {
    @NotNull
    @NotBlank
    @CorrectEmail
    private String email;
    @NotNull @NotBlank
    @Size(min = 8)
    private String password;
}
