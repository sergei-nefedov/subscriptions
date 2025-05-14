package pers.nefedov.subscriptions.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.nefedov.subscriptions.dto.UserCreationDto;
import pers.nefedov.subscriptions.dto.UserDto;
import pers.nefedov.subscriptions.exception.GlobalExceptionHandler;
import pers.nefedov.subscriptions.service.UserService;

@RestController
@Validated
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Tag(name = "User APIs", description = "API для управления пользователями")
public class UserController {
    private final UserService userService;

    @PostMapping
    @Operation(summary = "Создание пользователя", description = "Создает нового пользователя и возвращает данные зарегистрированного пользователя")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserCreationDto userCreationDto) {
        UserDto userDto = userService.createUser(userCreationDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение информации о пользователе", description = "Возвращает данные пользователя по его ID", responses = {@ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = UserDto.class))), @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class))), @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content(schema = @Schema(implementation = GlobalExceptionHandler.ErrorResponse.class)))})
    public ResponseEntity<UserDto> getUser(@PathVariable @NotNull @Min(1) @Schema(description = "ID пользователя",
            example = "1") Long id) {
        UserDto userDto = userService.getUser(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление пользователя", description = "Обновляет данные пользователя по его ID")
    public ResponseEntity<UserDto> updateUser(@PathVariable @NotNull @Min(1) @Schema(description = "ID пользователя", example = "1") Long id, @Valid @RequestBody UserCreationDto userCreationDto) {
        UserDto userDto = userService.updateUser(id, userCreationDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление пользователя", description = "Удаляет пользователя по его ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable @NotNull @Min(1) @Schema(description = "ID пользователя", example = "1") Long id) {
        userService.deleteUser(id);
    }

}
