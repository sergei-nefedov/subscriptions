package pers.nefedov.subscriptions.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
@RequiredArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String serviceName;

    @Column(nullable = false)
    private String serviceUrl;

    @OneToMany(mappedBy = "subscription")
    private List<UserSubscription> userSubscriptions;
}
