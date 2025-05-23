package pers.nefedov.subscriptions.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.nefedov.subscriptions.entity.Subscription;
import pers.nefedov.subscriptions.entity.User;
import pers.nefedov.subscriptions.entity.UserSubscription;
import pers.nefedov.subscriptions.entity.UserSubscriptionId;

import java.util.List;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, UserSubscriptionId> {
    boolean existsByUserAndSubscription(User user, Subscription subscription);

    List<UserSubscription> getAllByUser_Id(Long userId);

    UserSubscription findByUserAndSubscription(User user, Subscription subscription);
}
