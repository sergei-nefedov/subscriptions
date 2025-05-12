package pers.nefedov.subscriptions.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "user_subscriptions")
public class UserSubscription {
    @EmbeddedId
    private UserSubscriptionId id;

    @ManyToOne
    @MapsId("userId")
    private User user;

    @ManyToOne
    @MapsId("subscriptionId")
    private Subscription subscription;

    @Column(name = "subscribed_at")
    private LocalDateTime subscribedAt;

//    @Column(nullable = false)
//    private LocalDate startDate;
//
//    @Column(nullable = false)
//    private LocalDate endDate;
//
//    @Column(nullable = false)
//    private BigDecimal price;
//
//    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
//    private Currency currency;

}
