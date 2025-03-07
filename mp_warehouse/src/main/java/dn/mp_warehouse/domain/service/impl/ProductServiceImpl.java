package dn.mp_warehouse.domain.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import dn.mp_warehouse.api.dto.product.ProductInfoForBucket;
import dn.mp_warehouse.api.dto.product.lists.ListProductRequireInfoForOrder;
import dn.mp_warehouse.api.dto.product.lists.ProductRequireInfoForOrder;
import dn.mp_warehouse.api.exception.ProductNotFoundException;
import dn.mp_warehouse.domain.entity.ProductEntity;
import dn.mp_warehouse.domain.repository.ProductRepository;
import dn.mp_warehouse.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheManager = "cacheManager")
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${spring.kafka.topic.name}")
    private String topic;




    @Override
    public Map<String, ListProductRequireInfoForOrder> productRequireInfoForOrder(final Long warehouseId)  {
        final String bucketName = "Корзина";
        ProductRequireInfoForOrder productRequireInfoForOrder = new ProductRequireInfoForOrder();
        if (productRepository.findByWarehouse_Id(warehouseId).isEmpty()) {
            throw new ProductNotFoundException(MessageFormat
                    .format("Products with warehouseId {0} not found", warehouseId)
                    .toUpperCase());
        }
        var productIds = productRepository.findByWarehouse_Id(warehouseId)
                .stream()
                .map(ProductEntity::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        productRequireInfoForOrder.setProductIds(productIds);
        List<ProductInfoForBucket> pricesByProductNames = productRepository.findAllById(productIds)
                        .stream()
                        .map(p->{
                            var name = p.getProductName();
                            var price = p.getPrice();
                            var count = p.getCountOfProducts();
                            return new ProductInfoForBucket(name, price,count);
                        }).toList();
        productRequireInfoForOrder.setProductInfoForBuckets(pricesByProductNames);
        var totalPrice = pricesByProductNames
                .stream()
                .map(ProductInfoForBucket::getProductPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        productRequireInfoForOrder.setTotalPrice(totalPrice);
        var totalCount = (long) pricesByProductNames.size();
        productRequireInfoForOrder.setTotalCount(totalCount);
        ListProductRequireInfoForOrder listProductRequireInfoForOrder = new ListProductRequireInfoForOrder();
        listProductRequireInfoForOrder.setProductListResponse(List.of(productRequireInfoForOrder));
        if (listProductRequireInfoForOrder.getProductListResponse().isEmpty()) {
            throw new ProductNotFoundException(MessageFormat
                    .format("Products with warehouseId {0} not found", warehouseId)
                    .toUpperCase());
            }
        Map<String, ListProductRequireInfoForOrder> productMap = new ConcurrentHashMap<>();
        productMap.put(bucketName, listProductRequireInfoForOrder);
        productMap.forEach((k, v) -> log.info("Product name is {} and product require info is {}", k, v));
        try {
            var message = objectMapper.writeValueAsString(productMap);
            log.info(MessageFormat.format("Message is: {0}",message));
            kafkaTemplate.send(topic,message);
        }catch (IOException ioException){
            log.info("Exception in sending message: {}",ioException.getLocalizedMessage());
        }
        return productMap;
    }

    @Override
    public BigDecimal getPriceByProductId(Long id) {
        return productRepository.getPriceById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public Long getCountByProductId(Long id) {
        return productRepository.getCountById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
