package dn.mp_orders.domain.service;

import dn.mp_orders.api.dto.CommentDto;
import dn.mp_orders.api.dto.OrderDto;
import dn.mp_orders.domain.entity.CommentEntity;

import java.util.List;

public interface CommentService  {

    void addCommentForOrder(String orderId, CommentDto comment);

    void editComment(String orderId, CommentDto comment);

    CommentDto findCommentById(Long id);

    Double getRatingByComments(List<CommentEntity> comments);

    List<CommentDto> findAllComments();
}
