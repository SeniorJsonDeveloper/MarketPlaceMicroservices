package dn.mp_warehouse.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Random;
import java.util.concurrent.*;

@Slf4j
@Service
@RequiredArgsConstructor

public class MessageService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbit.topic.name}")
    private String topicName;

    @Value("${rabbit.routingKey.name}")
    private String routingKey;

//    private final Random messageCount = new Random();



    @Async
    @TransactionalEventListener
    public void sendMessage(String message) {
        if (message == null || message.isEmpty()) {
            log.warn("Message is null or empty. Skipping sending.");
            return;
        }
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        try {
            CompletionService<Void> completionService = new ExecutorCompletionService<>(executorService);
            int messageCount = 228;
            for (int i = 0; i < messageCount; i++) {
                int messageId = i + 1;
                completionService.submit(() -> {
                    rabbitTemplate.convertAndSend(topicName, routingKey, "Message: " + messageId);
                    log.info("Message count is: {} ", messageCount);
                    return null;
                });
            }
            for (int i = 0; i < messageCount; i++) {
                completionService.take();
            }
        } catch (Exception e) {
            log.error("Exception occurred during message sending: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to send messages", e);
        } finally {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }

        }
    }
    private int calculateMessageCount(String message) {
        return message != null ? message.length() : 0; // Пример: количество символов в сообщении
    }


}
