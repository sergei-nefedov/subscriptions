package pers.nefedov.subscriptions.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserCreationDto {
    @NotEmpty
    @NotNull
    @Schema(description = "Имя пользователя", example = "Иван Иванов")
    @Size(min = 2, max = 20)
    private String name;
    @Email
    @NotNull
    @Schema(description = "Email пользователя", example = "user@somemail.com")
    private String email;
}
