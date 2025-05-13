package pers.nefedov.subscriptions.mapper;

import org.springframework.stereotype.Component;
import pers.nefedov.subscriptions.dto.UserSubscriptionDto;
import pers.nefedov.subscriptions.entity.Subscription;
import pers.nefedov.subscriptions.entity.User;
import pers.nefedov.subscriptions.entity.UserSubscription;
import pers.nefedov.subscriptions.entity.UserSubscriptionId;

import java.time.LocalDateTime;

@Component
public class UserSubscriptionMapperImpl implements UserSubscriptionMapper {
    @Override
    public UserSubscriptionDto toUserSubscriptionDto(UserSubscription userSubscription) {
        return new UserSubscriptionDto(
                userSubscription.getId().getUserId(),
                userSubscription.getId().getSubscriptionId(),
                userSubscription.getSubscribedAt()
        );
    }

    @Override
    public UserSubscription toUserSubscription(User user, Subscription subscription) {
        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setId(new UserSubscriptionId(user.getId(), subscription.getId()));
        userSubscription.setUser(user);
        userSubscription.setSubscription(subscription);
        userSubscription.setSubscribedAt(LocalDateTime.now());
        return userSubscription;
    }
}
