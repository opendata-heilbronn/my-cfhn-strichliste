package de.codeforheilbronn.mycfhn.strichliste.controller;

import de.codeforheilbronn.mycfhn.strichliste.auth.Authenticated;
import de.codeforheilbronn.mycfhn.strichliste.auth.Authorized;
import de.codeforheilbronn.mycfhn.strichliste.model.api.ProductCreateModel;
import de.codeforheilbronn.mycfhn.strichliste.model.api.ProductModel;
import de.codeforheilbronn.mycfhn.strichliste.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping
    public List<ProductModel> getProducts() {
        return productService.getProducts();
    }

    @PostMapping
    @Authenticated
    @Authorized(groups = {"boardMembers", "infrastructureAdmins"})
    public ProductModel createProduct(@RequestBody ProductCreateModel product) {
        return productService.createProduct(product);
    }


}
