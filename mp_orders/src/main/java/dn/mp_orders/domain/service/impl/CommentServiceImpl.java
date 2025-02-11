package dn.mp_orders.domain.service.impl;

import dn.mp_orders.api.dto.CommentDto;
import dn.mp_orders.api.dto.OrderDto;
import dn.mp_orders.api.exception.CommentNotFoundException;
import dn.mp_orders.api.exception.OrderNotFound;
import dn.mp_orders.api.mapper.OrderMapper;
import dn.mp_orders.domain.entity.CommentEntity;
import dn.mp_orders.domain.repository.CommentRepository;
import dn.mp_orders.domain.repository.OrderRepository;
import dn.mp_orders.domain.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "comments",key = "#result.id")
    public OrderDto addCommentForOrder(String orderId, CommentDto comment) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(()->new OrderNotFound(
                        MessageFormat.format("Order with id {0} not found",orderId)));

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(UUID.randomUUID().toString());
        commentEntity.setText(comment.getText());
        commentEntity.setUserId(comment.getUserId());
        commentEntity.setRating(comment.getRating());

        validateComment(comment);
        return orderMapper.toDto(order);
    }

    private void validateComment(CommentDto comment) {
        try {
            if (comment.getText() == null || comment.getText().isEmpty()) {
                throw new IllegalArgumentException("Comment text cannot be null or empty");
            }
            if (comment.getRating() < 1 || comment.getRating() > 5) {
                throw new IllegalArgumentException("Comment rating must be between 1 and 5");
            }
        } catch (Exception e){
            log.info("Exception is: {}",e.getMessage());
        }
        finally {
            log.info("Comment validated");
        }
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
                    com.setOrder(order);
                    commentRepository.save(com);
                }, () -> {
                    throw new CommentNotFoundException("Комментарий с ID " + comment.getId() + " не найден");
                });

    }

    @Override
    public CommentEntity findCommentById(String id) {
        return commentRepository.findById(id)
                .orElseThrow(()-> new CommentNotFoundException
                        (MessageFormat.format("Comment not found with id: {0}",id)));
    }


    @Override
    public Double getRatingByComments(List<CommentEntity> comments) {
        if (comments == null || comments.isEmpty()) {
            return 0.0;
        }
        return comments.stream()
                .mapToDouble(CommentEntity::getRating)
                .average()
                .orElse(0.0);
    }


//    public OrderDto mapToDto(OrderEntity order) {
//        OrderDto orderDto = new OrderDto();
//        orderDto.setId(order.getId());
//        orderDto.setName(order.getName());
//        orderDto.setMessage(order.getMessage());
//        orderDto.setStatus(order.getStatus());
//        orderDto.setPrice(order.getPrice());
//        orderDto.setWarehouseId(order.getWarehouseId());
//        var commentText = order.getCommentIds().stream()
//                .map(CommentEntity::getText)
//                .toList();
//        var comments = commentRepository.findAllById(order.getCommentIds());
//        orderDto.setComments((List<CommentEntity>) comments);
//        List<CommentEntity> commentList = commentRepository.findAllByOrderId(order.getId());
//        Double ratingFromComments = getRatingByComments(commentList);
//        orderDto.setRating(ratingFromComments);
//        return orderDto;
//    }
}
