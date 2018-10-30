package de.codeforheilbronn.mycfhn.strichliste.service;

import de.codeforheilbronn.mycfhn.strichliste.model.api.ProductCreateModel;
import de.codeforheilbronn.mycfhn.strichliste.model.api.ProductModel;
import de.codeforheilbronn.mycfhn.strichliste.model.persistence.Product;
import de.codeforheilbronn.mycfhn.strichliste.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;

    public List<ProductModel> getProducts() {
        return productRepository.findAll()
            .stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public ProductModel createProduct(ProductCreateModel model) {
        Product product = new Product();
        product.setName(model.getName());
        product.setPrice(model.getPrice());
        return toModel(productRepository.save(product));
    }

    private ProductModel toModel(Product product) {
        return ProductModel.builder()
                .id(product.getId().toString())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
