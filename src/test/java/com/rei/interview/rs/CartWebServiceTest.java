package com.rei.interview.rs;

import com.rei.interview.checkout.Cart;
import com.rei.interview.checkout.CartService;
import com.rei.interview.product.Product;
import com.rei.interview.product.ProductService;
import com.rei.interview.rs.cart.AddItem;
import com.rei.interview.rs.cart.CartWebService;
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
public class CartWebServiceTest {

    @InjectMocks
    private CartWebService cartWebService;
    @Mock
    private ProductService productService;

    @Mock
    private CartService cartService;


    @Test
    public void shouldReturn200WhenSendingRequestCart() throws Exception {
        AddItem addItem = new AddItem();
        addItem.setProductId("123456");
        addItem.setQuantity(1);
        addItem.setLocation(Location.SEATTLE);

        Product product = createProduct(PRODUCT_ID1, PRODUCT_BRAND1, PRODUCT_DESCRIPTION1, PRICE1);

        when(cartService.addToCart(CART_ID, product, addItem.getQuantity(), addItem.getLocation())).thenReturn(new Cart());
        Cart cart = cartService.addToCart(CART_ID, product, addItem.getQuantity(), addItem.getLocation());
        assertTrue(cart.getCartId()!=null);
        assertNotNull(cart);
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
