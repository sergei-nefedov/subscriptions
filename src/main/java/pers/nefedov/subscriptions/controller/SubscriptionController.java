package pers.nefedov.subscriptions.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pers.nefedov.subscriptions.dto.SubscriptionDto;
import pers.nefedov.subscriptions.service.SubscriptionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/subscriptions")
@Tag(name = "Subscription APIs", description = "API для управления подписками")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping("/top")
    @Operation(summary = "Получение топ-3 подписок", description = "Возвращает список 3-х самых популярных подписок")
    public ResponseEntity<List<SubscriptionDto>> getTopSubscriptions() {

        return new ResponseEntity<>(subscriptionService.getTop3Subscriptions(), HttpStatus.OK);
    }

}
