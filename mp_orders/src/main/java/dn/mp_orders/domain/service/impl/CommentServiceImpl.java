package dn.mp_orders.domain.service.impl;

import dn.mp_orders.api.dto.CommentDto;
import dn.mp_orders.api.dto.OrderDto;
import dn.mp_orders.api.exception.CommentNotFoundException;
import dn.mp_orders.api.exception.OrderNotFound;
import dn.mp_orders.api.mapper.CommentMapper;
import dn.mp_orders.api.mapper.OrderMapper;
import dn.mp_orders.domain.entity.CommentEntity;
import dn.mp_orders.domain.entity.OrderEntity;
import dn.mp_orders.domain.repository.CommentRepository;
import dn.mp_orders.domain.repository.OrderRepository;
import dn.mp_orders.domain.service.CommentService;
import dn.mp_orders.domain.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@CacheConfig(cacheManager = "cacheManager")
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    private final CommentMapper commentMapper;

    private final ApplicationEventPublisher eventPublisher;

    private final Map<String,CommentEntity> commentMap = new ConcurrentHashMap<>();

    @Override
    @Cacheable(value = "comments")
    public List<CommentDto> findAllComments() {
        return commentMapper.toDtoList(commentRepository.findAll());
    }

    @Override
    public void addCommentForOrder(String orderId, CommentDto comment) {
//        var order = orderService.findOrderById(orderId);
//        log.info("Order found: {}", order);
//        CommentEntity commentEntity = commentMapper.toEntity(comment);
//        commentEntity.setText(comment.getText());
//        commentEntity.setRating(comment.getRating());
//        commentEntity.setId(commentEntity.getId());
//        commentEntity.setOrder(orderMapper.toEntity(order));
//        commentRepository.save(commentEntity);
//        order.getComments().add(commentEntity);
//        commentMap.put(orderId,commentEntity);
//        log.info("Comment saved: {}", commentEntity);

    }

    @Override
    public void editComment(String orderId, CommentDto comment) {

    }

    @Override
    public CommentDto findCommentById(Long id) {
        return commentMapper.toDto(commentRepository.findById(id)
                .orElseThrow(()->new CommentNotFoundException("Comment not found")));
    }

    @Override
    public Double getRatingByComments(List<CommentEntity> comments) {
        return comments.stream()
                .mapToDouble(CommentEntity::getRating)
                .average()
                .orElse(0.0);

    }


}
