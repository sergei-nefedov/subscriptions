package pers.nefedov.subscriptions.service;

import pers.nefedov.subscriptions.dto.UserSubscriptionDto;

import java.util.List;

public interface UserSubscriptionService {
    UserSubscriptionDto addSubscriptionToUser(Long userId, Long aLong);

    List<UserSubscriptionDto> getSubscriptions(Long userId);

    void deleteSubscription(Long userId, Long subId);

}
