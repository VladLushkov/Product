package com.store.storehouse.service;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.store.storehouse.dto.*;
import com.store.storehouse.model.Product;
import com.store.storehouse.repository.ProductRepository;
import com.store.storehouse.repository.ProductTypeRepository;
import com.store.storehouse.service.kafka.HistoryKafkaProducer;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    private final HistoryKafkaProducer historyKafkaProducer;
    private final JsonMapper jsonMapper;

    @Transactional
    public void save(final ProductDto dto) {
        if (productRepository.findProductByName(dto.getName()).isPresent()) {
            throw new RuntimeException("Товар уже имеется на складе");
        }
        productTypeRepository.getProductTypeByNameAndIsEnableTrue(dto.getType())
                .orElseThrow(() -> new RuntimeException("Тип с таким товаром отсутствует"));

        Product product = new Product();
        product.setName(dto.getName());
        product.setType(dto.getType());
        product.setCount(dto.getCount());
        product = productRepository.save(product);
        historyKafkaProducer.sendMessage(createKafkaProducerMessage(product, product.getCount(), "CREATE"), product.getId().toString());
    }

    @Transactional
    public void addProducts(final ProductDto dto) {
        Product product = productRepository.findProductByName(dto.getName())
                .orElseThrow(() -> new RuntimeException("Данного товара нет на складе"));
        productTypeRepository.getProductTypeByNameAndIsEnableTrue(dto.getType())
                .orElseThrow(() -> new RuntimeException("Тип с таким товаром отсутствует"));

        var oldCount = product.getCount();
        product.setCount(product.getCount() + dto.getCount());
        product = productRepository.save(product);
        historyKafkaProducer.sendMessage(createKafkaProducerMessage(product, oldCount, "UPDATE"), product.getId().toString());
    }

    public ArrayList<ProductTypeDto> getProductTypes() {
        var typesList = new ArrayList<ProductTypeDto>();
        productTypeRepository.findAll().forEach(e -> {
            ProductTypeDto dto = new ProductTypeDto();
            dto.setId(e.getId());
            dto.setName(e.getName());
            typesList.add(dto);
        });
        return typesList;
    }

    @SneakyThrows
    private String createKafkaProducerMessage(Product product, Integer oldCount, String operationName) {
        HistoryProducerMessageDto dto = new HistoryProducerMessageDto();
        dto.setProductName(product.getName());
        dto.setOperationType(operationName);
        dto.setOldCount(oldCount);
        dto.setNewCount(product.getCount());
        return jsonMapper.writeValueAsString(dto);
    }
}
