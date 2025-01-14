package dn.mp_orders.domain.service;

import dn.mp_orders.api.dto.CommentDto;
import dn.mp_orders.api.dto.OrderDto;
import dn.mp_orders.domain.entity.CommentEntity;

import java.util.List;
import java.util.Set;

public interface CommentService {

    OrderDto addCommentForOrder(String orderId, CommentDto comment);


    void editComment(String orderId, CommentDto comment);

    CommentEntity findCommentById(String id);



    Double getRatingByComments(List<CommentEntity> comments);
}
