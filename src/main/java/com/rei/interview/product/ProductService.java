package com.rei.interview.product;

import com.rei.interview.inventory.InventoryService;
import com.rei.interview.rs.Location;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public boolean isValidProduct(Product product) {
        return StringUtils.isNumeric(product.getProductId()) && product.getProductId().length() == 6
                && !product.getBrand().isEmpty() && !product.getDescription().isEmpty()
                && product.getPrice() != null && product.getPrice().compareTo(BigDecimal.ZERO) == 1;
    }

    public boolean isOnStock(InventoryService inventoryService, Product product, int quantity, Location location) {
        return inventoryService.hasInventoryOnline(product, quantity)
                || inventoryService.hasInventoryInNearbyStores(product, quantity, location);
    }

    public Product getProduct(String productId) {
        return productRepository.getProduct(productId);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(productRepository.getAll());
    }

    public void addProduct(Product product){
        productRepository.addProduct(product);
    }

    /**
     * Populates the product repository with data from products.txt
     *
     * @throws IOException
     */
    @PostConstruct
    public void populateProducts() throws IOException {
        try(Reader in = new InputStreamReader(getClass().getResourceAsStream("/products.csv"))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader("productId", "brand", "description", "price")
                    .withFirstRecordAsHeader()
                    .parse(in);

            for (CSVRecord record : records) {
                Product product = new Product();
                product.setProductId(record.get("productId"));
                product.setBrand(record.get("brand"));
                product.setDescription(record.get("description"));
                product.setPrice(new BigDecimal(record.get("price")));
                logger.info(product.toString());
                productRepository.addProduct(product);
            }
        }

        logger.info("Products loaded into product repository");
    }

}
