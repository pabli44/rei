package com.rei.interview.checkout;

import com.rei.interview.inventory.InventoryService;
import com.rei.interview.product.Product;
import com.rei.interview.product.ProductService;
import com.rei.interview.rs.Location;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static com.rei.interview.TestConstants.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private ProductService productService;

    @Mock
    private InventoryService inventoryService;

    @Mock
    private CartRepository cartRepository;

    @Test
    public void testAddToCartWhenIsValidProductAndIsOnStock() throws Exception {
        Product product = createProduct(PRODUCT_ID1, PRODUCT_BRAND1, PRODUCT_DESCRIPTION1, PRICE1);

        when(productService.isValidProduct(product)).thenReturn(Boolean.TRUE);
        when(productService.isOnStock(inventoryService, product, QUANTITY, Location.SEATTLE)).thenReturn(Boolean.TRUE);

        Cart cart = cartService.addToCart(CART_ID, product, QUANTITY, Location.SEATTLE);

        assertTrue(cart.getProducts().size()>0);
        assertNotNull(cart.getCartId());
    }

    @Test(expected = Exception.class)
    public void testAddToCartWhenIsNotAValidProduct() throws Exception {
        Product product = createProduct(PRODUCT_ID1, PRODUCT_BRAND1, PRODUCT_DESCRIPTION1, PRICE1);

        when(productService.isValidProduct(product)).thenReturn(Boolean.FALSE);
        cartService.addToCart(CART_ID, product, QUANTITY, Location.SEATTLE);
    }

    @Test(expected = Exception.class)
    public void testAddToCartWhenIsNotOnStock() throws Exception {
        Product product = createProduct(PRODUCT_ID1, PRODUCT_BRAND1, PRODUCT_DESCRIPTION1, PRICE1);

        when(productService.isValidProduct(product)).thenReturn(Boolean.TRUE);
        when(productService.isOnStock(inventoryService, product, QUANTITY, Location.SEATTLE)).thenReturn(Boolean.FALSE);

        cartService.addToCart(CART_ID, product, QUANTITY, Location.SEATTLE);
    }


    private Product createProduct(String id, String brand, String description, BigDecimal price){
        Product product = new Product();
        product.setProductId(id);
        product.setBrand(brand);
        product.setDescription(description);
        product.setPrice(price);

        return product;
    }

}