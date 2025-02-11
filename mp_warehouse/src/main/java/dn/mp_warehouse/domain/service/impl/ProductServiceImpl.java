package dn.mp_warehouse.domain.service.impl;

import dn.mp_warehouse.api.dto.mapper.ProductMapper;
import dn.mp_warehouse.api.dto.product.ProductFilterDto;
import dn.mp_warehouse.api.dto.product.ProductInputDto;
import dn.mp_warehouse.api.dto.product.ProductOutDto;
import dn.mp_warehouse.api.exception.ProductNotFoundException;
import dn.mp_warehouse.domain.entity.ProductEntity;
import dn.mp_warehouse.domain.repository.ProductRepository;
import dn.mp_warehouse.domain.repository.specification.ProductSpecification;
import dn.mp_warehouse.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final KafkaTemplate<String,Object> kafkaTemplate;


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
                    productRepository.save(o);
                    kafkaTemplate.send((Message<?>) o);
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
