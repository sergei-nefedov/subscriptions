package pers.nefedov.subscriptions.dto;

public record SubscriptionDto(
        Long id,
        String name,
        Long subscribersCount
) {}
