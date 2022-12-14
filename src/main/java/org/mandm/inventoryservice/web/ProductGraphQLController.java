package org.mandm.inventoryservice.web;

import org.mandm.inventoryservice.entities.Category;
import org.mandm.inventoryservice.entities.Product;
import org.mandm.inventoryservice.dto.ProductRequestDTO;
import org.mandm.inventoryservice.repository.CategoryRepository;
import org.mandm.inventoryservice.repository.ProductRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class ProductGraphQLController {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ProductGraphQLController(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    @QueryMapping
    public List<Product>  productList(){
        return productRepository.findAll();
    }

    @QueryMapping
    public Product productById(@Argument String id) {
        return productRepository.findById(id).orElseThrow(
                ()->new RuntimeException(String.format("Product %s not found ",id))
        );
    }
    @QueryMapping
    public List<Category> categories(){
        return categoryRepository.findAll();
    }

    @QueryMapping
    public Category categoryById(@Argument Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(()->new RuntimeException(String.format("Category %s not found",id)));
    }

    @MutationMapping
    public Product saveProduct(@Argument ProductRequestDTO product){
        Category category=categoryRepository.findById(product.getCategoryId()).orElse(null);
        Product productToSave=new Product();
        productToSave.setId(UUID.randomUUID().toString());
        productToSave.setName(product.getName());
        productToSave.setPrice(product.getPrice());
        productToSave.setQuantity(product.getQuantity());
        productToSave.setCategory(category);
        return productRepository.save(productToSave);
    }

    @MutationMapping
    public Product updateProduct(@Argument String id, @Argument ProductRequestDTO product){
        Category category=categoryRepository.findById(product.getCategoryId()).orElse(null);
        Product productToSave=new Product();
        productToSave.setId(id);
        productToSave.setName(product.getName());
        productToSave.setPrice(product.getPrice());
        productToSave.setQuantity(product.getQuantity());
        productToSave.setCategory(category);
        return productRepository.save(productToSave);
    }

    @MutationMapping
    public void deleteProduct(@Argument String id) {
        productRepository.deleteById(id);
    }

}
