package dn.mp_notifications.api.dto.mapper.impl;

import dn.mp_notifications.api.dto.NotificationDto;
import dn.mp_notifications.api.dto.mapper.CustomNotificationMapper;
import dn.mp_notifications.domain.entity.Notification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomNotificationMapperImpl implements CustomNotificationMapper {


    @Override
    public Notification notificationDtoToNotification(NotificationDto notificationDto) {
        if ( notificationDto == null ) {
            return null;
        }

        Notification.NotificationBuilder notification = Notification.builder();

        notification.id( notificationDto.getId() );
        notification.title( notificationDto.getTitle() );
        notification.userId( notificationDto.getUserId() );
        notification.senderId( notificationDto.getSenderId() );
        notification.message( notificationDto.getMessage() );
        notification.createdAt( notificationDto.getCreatedAt() );
        notification.orderId( notificationDto.getOrderId() );
        notification.status( notificationDto.getStatus() );
        notification.pageNumber( notificationDto.getPageNumber() );
        notification.pageSize( notificationDto.getPageSize() );

        return notification.build();
    }

    @Override
    public List<NotificationDto> mapToDtoList(List<Notification> entities) {
        if ( entities == null ) {
            return null;
        }

        List<NotificationDto> list = new ArrayList<NotificationDto>( entities.size() );
        for ( Notification notification : entities ) {
            list.add( mapToDto( notification ) );
        }

        return list;
    }

    @Override
    public List<Notification> mapToDtoEntity(List<NotificationDto> entities) {
        if ( entities == null ) {
            return null;
        }

        List<Notification> list = new ArrayList<Notification>( entities.size() );
        for ( NotificationDto notificationDto : entities ) {
            list.add( notificationDtoToNotification( notificationDto ) );
        }

        return list;
    }

    @Override
    public NotificationDto mapToDto(Notification entity) {
        if ( entity == null ) {
            return null;
        }

        NotificationDto notificationDto = new NotificationDto();

        notificationDto.setOrderId( entity.getOrderId() );
        notificationDto.setStatus( entity.getStatus() );
        notificationDto.setId( entity.getId() );
        notificationDto.setTitle( entity.getTitle() );
        notificationDto.setUserId( entity.getUserId() );
        notificationDto.setSenderId( entity.getSenderId() );
        notificationDto.setMessage( entity.getMessage() );
        notificationDto.setCreatedAt( entity.getCreatedAt() );
        notificationDto.setPageNumber( entity.getPageNumber() );
        notificationDto.setPageSize( entity.getPageSize() );

        return notificationDto;
    }
}
