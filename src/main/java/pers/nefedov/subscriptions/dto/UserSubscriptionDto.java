package pers.nefedov.subscriptions.dto;

import java.time.LocalDateTime;
public record UserSubscriptionDto(
        Long userId,
        Long subscriptionId,
        LocalDateTime subscribedAt
) {}
