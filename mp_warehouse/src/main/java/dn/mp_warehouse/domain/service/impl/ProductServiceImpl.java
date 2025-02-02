package dn.mp_warehouse.domain.service.impl;

import dn.mp_warehouse.api.dto.ProductFilterDto;
import dn.mp_warehouse.api.dto.ProductInputDto;
import dn.mp_warehouse.api.dto.ProductOutDto;
import dn.mp_warehouse.api.dto.mapper.ProductMapper;
import dn.mp_warehouse.api.exception.ProductNotFoundException;
import dn.mp_warehouse.domain.ProductEntity;
import dn.mp_warehouse.domain.repository.ProductRepository;
import dn.mp_warehouse.domain.repository.WareHouseRepository;
import dn.mp_warehouse.domain.repository.specification.ProductSpecification;
import dn.mp_warehouse.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final RabbitTemplate rabbitTemplate;

    private final ProductMapper productMapper;

    private final WareHouseRepository wareHouseRepository;


    @Override
    public Page<ProductEntity> withSort(ProductFilterDto productFilterDto) {
        return productRepository.findAll(
                ProductSpecification.withFilter(productFilterDto),
                productFilterDto.getPageModelDto().getPageRequest()
        );
    }

    @Override
    public ProductOutDto findById(String id) {
        return productMapper.toDto(productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException(
                        MessageFormat.format("Товар с идентификатором {0} не найден",id)
                )));
    }

    @Override
    public ProductOutDto findByName(String name) {
        return productMapper.toDto(
                productRepository.findByProductName(name)
                        .orElseThrow(()->new ProductNotFoundException(
                                MessageFormat.format("Товар с названием {0} не найден",name)
                        ))
        );
    }

    @Override
    public ProductOutDto save(ProductInputDto productInputDto) {
        return null;
    }

    @Override
    public void update(String id, ProductInputDto productInputDto) {
         productRepository.findById(id).ifPresentOrElse(o->{
                    o.setProductName(productInputDto.getProductName());
                    o.setDescription(productInputDto.getDescription());
                    o.setCategory(productInputDto.getCategory());
                    o.setBrand(productInputDto.getBrand());
                    o.setCost(productInputDto.getCost());
                    o.setBuyerId(productInputDto.getBuyerId());
                    o.setSellerId(productInputDto.getSellerId());
                    Map<String,ProductEntity> map = new HashMap<>();
//                    var wareHouseId = wareHouseRepository.findById(o.getWarehouse().getId())
//                                    .orElseThrow(()->new ResourceNotFoundException(""));
//                    map.put(wareHouseId.getId(),o);
                    productRepository.save(o);
                    rabbitTemplate.convertAndSend(o);
                },()-> {
                    throw new ProductNotFoundException("");
         });
    }

    @Override
    public void deleteById(String id) {
        ProductEntity product = productMapper.toEntity(findById(id));
        if (product != null) {
            productRepository.delete(product);
        }

    }

    @Override
    public void deleteAll(List<String> ids) {
        productRepository.deleteAllById(ids);
    }

    @Override
    public Long getCount(String id) {
        ProductEntity product = productMapper.toEntity(findById(id));
        return product.getCountOfProducts();
    }
}
