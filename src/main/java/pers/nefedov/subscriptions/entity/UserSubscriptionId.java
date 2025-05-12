package pers.nefedov.subscriptions.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserSubscriptionId implements Serializable {
    private Long userId;
    private Long subscriptionId;
}
