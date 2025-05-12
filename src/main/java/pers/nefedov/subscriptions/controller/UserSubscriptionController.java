package pers.nefedov.subscriptions.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pers.nefedov.subscriptions.dto.AddSubscriptionRequest;
import pers.nefedov.subscriptions.dto.UserSubscriptionDto;
import pers.nefedov.subscriptions.service.UserSubscriptionService;

import java.util.List;

@RestController
@RequestMapping("/v1/users/{userId}/subscriptions")
@RequiredArgsConstructor
@Tag(name = "User subscription APIs", description = "API для управления подписками пользователей")
public class UserSubscriptionController {
    private final UserSubscriptionService userSubscriptionService;

    @PostMapping
    @Operation(
            summary = "Добавление подписки пользователю",
            description = "Добавляет подписку пользователю по его ID. ID подписки передается в теле запроса"
    )
    public ResponseEntity<UserSubscriptionDto> addSubscription(
            @PathVariable @Positive @Schema(description = "ID пользователя",
                    example = "1") Long userId,
            @RequestBody AddSubscriptionRequest request) {

        UserSubscriptionDto dto = userSubscriptionService.addSubscriptionToUser(
                userId,
                request.subscriptionId()
        );

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @GetMapping
    @Operation(
            summary = "Получение всех подписок пользователя",
            description = "Возвращает все подписки пользователя по его ID"
    )
    public ResponseEntity<List<UserSubscriptionDto>> getSubscriptions(
            @PathVariable @Positive @Schema(description = "ID пользователя",
                    example = "1") Long userId) {

        return new ResponseEntity<>(userSubscriptionService.getSubscriptions(userId), HttpStatus.OK);
    }

    @DeleteMapping("/{subId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Удаление подписки пользователя",
            description = "Удаляет подписку (по ID подписки) пользователя по его ID"
    )
    public void deleteSubscription(
            @PathVariable @Positive @Schema(description = "ID пользователя",
                    example = "1") Long userId, @PathVariable @Positive @Schema(description = "ID подписки",
                    example = "1") Long subId) {
        userSubscriptionService.deleteSubscription(userId, subId);

    }
}
