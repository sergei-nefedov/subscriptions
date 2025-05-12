package pers.nefedov.subscriptions.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import pers.nefedov.subscriptions.dto.SubscriptionDto;
import pers.nefedov.subscriptions.entity.Subscription;
import pers.nefedov.subscriptions.exception.DataRetrievalException;
import pers.nefedov.subscriptions.repo.SubscriptionRepository;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public List<SubscriptionDto> getTop3Subscriptions() {
        MDC.put("operation", "getTop3Subscriptions");
        try {
            log.debug("Fetching top 3 popular subscriptions");

            List<Object[]> rawResults = subscriptionRepository.findTop3PopularSubscriptions();

            List<SubscriptionDto> result = rawResults.stream()
                    .map(res -> {
                        Subscription sub = (Subscription) res[0];
                        Long count = (Long) res[1];
                        log.trace("Processing subscription: {} with count {}", sub.getId(), count);
                        return new SubscriptionDto(sub.getId(), sub.getServiceName(), count);
                    })
                    .toList();

            log.info("Successfully retrieved {} top subscriptions", result.size());
            return result;
        } catch (Exception e) {
            log.error("Failed to fetch top subscriptions: {}", e.getMessage());
            throw new DataRetrievalException("Could not retrieve subscriptions", e);
        } finally {
            MDC.remove("operation");
        }
    }
}

