package pers.nefedov.subscriptions.mapper;

import pers.nefedov.subscriptions.dto.UserSubscriptionDto;
import pers.nefedov.subscriptions.entity.Subscription;
import pers.nefedov.subscriptions.entity.User;
import pers.nefedov.subscriptions.entity.UserSubscription;

public interface UserSubscriptionMapper {
    UserSubscriptionDto toUserSubscriptionDto(UserSubscription userSubscription);
    UserSubscription toUserSubscription(User user, Subscription subscription);
}
