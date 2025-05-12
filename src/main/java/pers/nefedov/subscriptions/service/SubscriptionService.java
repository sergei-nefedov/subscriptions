package pers.nefedov.subscriptions.service;

import pers.nefedov.subscriptions.dto.SubscriptionDto;

import java.util.List;

public interface SubscriptionService {
    List<SubscriptionDto> getTop3Subscriptions();
}
