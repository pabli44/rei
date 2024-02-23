package com.rei.interview.rs.product;

import com.rei.interview.product.Product;
import com.rei.interview.product.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductWebService {

    private ProductService productService;

    private ModelMapper modelMapper;

    @Autowired
    public ProductWebService(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @ResponseBody
    public String test() {
        return "test";
    }

    @GetMapping("/{productId}")
    @ResponseBody
    public ProductDto getProductById(@PathVariable("productId") String productId) {
        Product product = productService.getProduct(productId);
        if (product != null) {
            return modelMapper.map(product, ProductDto.class);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
    }

    @GetMapping("/brand/{brand}")
    @ResponseBody
    public List<ProductDto> getProductsByBrand(@PathVariable("brand") String brand) {
        List<Product> products = productService.getAllProducts().stream()
                                               .filter(p -> p.getBrand().equals(brand))
                                               .collect(Collectors.toList());
        if (products.size()>0) {
            return products.stream().map(p -> {
                ProductDto pdt = modelMapper.map(p, ProductDto.class);
                return pdt;
            }).collect(Collectors.toList());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Products with that brand not found");
        }
    }

    @PostMapping
    @ResponseBody
    public String addProduct(@RequestBody ProductDto productDto){
        productService.addProduct(modelMapper.map(productDto, Product.class));
        return "The Product was added!";
    }

    @PostMapping("/addProducts")
    @ResponseBody
    public String addProducts(@RequestBody List<ProductDto> productsDto){
        productsDto.forEach(p -> productService.addProduct(modelMapper.map(p, Product.class)));
        return String.format("The list of Products with size %s were added!", productsDto.size());
    }


}