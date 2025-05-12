package pers.nefedov.subscriptions.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pers.nefedov.subscriptions.entity.Subscription;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query("""
    SELECT s, COUNT(DISTINCT us.user.id) as userCount
    FROM Subscription s
    JOIN s.userSubscriptions us
    GROUP BY s.id
    ORDER BY userCount DESC
    LIMIT 3
    """)
    List<Object[]> findTop3PopularSubscriptions();
}


