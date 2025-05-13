package pers.nefedov.subscriptions.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AddSubscriptionDto {
    @NotNull
    @Min(1)
    @Schema(description = "ID подписки", example = "1")
    Long subscriptionId;
}
