package dn.mp_orders.domain.service.impl;

import dn.mp_orders.api.dto.CommentDto;
import dn.mp_orders.api.dto.OrderDto;
import dn.mp_orders.api.mapper.CommentMapper;
import dn.mp_orders.api.mapper.OrderMapper;
import dn.mp_orders.domain.entity.CommentEntity;
import dn.mp_orders.domain.entity.OrderEntity;
import dn.mp_orders.domain.exception.CommentNotFoundException;
import dn.mp_orders.domain.exception.OrderNotFound;
import dn.mp_orders.domain.repository.CommentRepository;
import dn.mp_orders.domain.repository.OrderRepository;
import dn.mp_orders.domain.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final OrderRepository orderRepository;



    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(cacheNames = "comment",key = "#comment.id")
    public OrderDto addCommentForOrder(String orderId, CommentDto comment) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(()->new OrderNotFound(
                        MessageFormat.format("Order with id {0} not found",orderId)));

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(UUID.randomUUID().toString());
        commentEntity.setText(comment.getText());
        commentEntity.setOrderId(order.getId());
        commentEntity.setUserId(comment.getUserId());
        commentEntity.setRating(comment.getRating());

        if (comment.getText() == null || comment.getText().isEmpty()) {
            throw new IllegalArgumentException("Comment text cannot be null or empty");
        }
        if (comment.getRating() < 1 || comment.getRating() > 5) {
            throw new IllegalArgumentException("Comment rating must be between 1 and 5");
        }

        Set<String> commentIds = order.getCommentIds();
        if (commentIds == null) {
            commentIds = new HashSet<>();
        }
        commentIds.add(commentEntity.getId());
        order.setCommentIds(commentIds);
        commentRepository.save(commentEntity);
        orderRepository.save(order);
        log.info("CommentFor Order, Comment Text: {} , {}",commentEntity.getOrderId(),commentEntity.getText());
        log.info("Order comment ids: {} ",order.getCommentIds());
        return mapToDto(order);
    }





    @Override
    public void editComment(String orderId, CommentDto comment) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(()->new OrderNotFound(
                        MessageFormat.format("Order with id {0} not found",orderId)));

        commentRepository.findById(comment.getId())
                   .ifPresentOrElse(com -> {
                    com.setText(comment.getText());
                    com.setRating(comment.getRating());
                    com.setOrderId(order.getId());
                    commentRepository.save(com);
                }, () -> {
                    throw new CommentNotFoundException("Комментарий с ID " + comment.getId() + " не найден");
                });

    }

    @Override
    public CommentEntity findCommentById(String id) {
        return commentRepository.findById(id).
                orElseThrow(()-> new CommentNotFoundException
                        (MessageFormat.format("Comment not found with id: {0}",id)));
    }


    @Override
    public Double getRatingByComments(List<CommentEntity> comments) {
        return comments
                .stream()
                .mapToDouble(CommentEntity::getRating)
                .average()
                .orElseThrow(() -> new CommentNotFoundException("Comment rating cannot be null or empty"));
    }


    private OrderDto mapToDto(OrderEntity order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setName(order.getName());
        orderDto.setMessage(order.getMessage());
        orderDto.setStatus(order.getStatus());
        orderDto.setPrice(order.getPrice());
        orderDto.setWarehouseId(order.getWarehouseId());
        var comments = commentRepository.findAllById(order.getCommentIds());
        orderDto.setComments((List<CommentEntity>) comments);
        return orderDto;
    }
}
