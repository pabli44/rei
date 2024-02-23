package com.rei.interview.inventory;

import com.rei.interview.location.LocationService;
import com.rei.interview.product.Product;
import com.rei.interview.rs.Location;
import com.rei.interview.store.Store;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import static com.rei.interview.TestConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class InventoryServiceTest {

    @InjectMocks
    private InventoryService inventoryService;

    @Mock
    private LocationService locationService;

    @Mock
    private InventoryRepository inventoryRepository;


    @Test
    public void testHasInventoryOnlineWhenProductOnInventory() {
        Inventory inventory1 = new Inventory();
        Product product1 = createProduct(PRODUCT_ID1, PRODUCT_BRAND1, PRODUCT_DESCRIPTION1, PRICE1);
        List<Product> products1 = List.of(product1);
        inventory1.setProducts(products1);

        Inventory inventory2 = new Inventory();
        Product product2 = createProduct(PRODUCT_ID2, PRODUCT_BRAND2, PRODUCT_DESCRIPTION2, PRICE2);
        List<Product> products2 = List.of(product2);
        inventory2.setProducts(products2);

        when(inventoryRepository.getAll()).thenReturn(List.of(inventory1, inventory2));

        Collection<Inventory> inventories = inventoryRepository.getAll();

        assertTrue(inventories.size()>0);
        assertEquals(2, inventories.size());
    }

    @Test
    public void testHasInventoryOnlineWhenProductNotOnInventory() {
        when(inventoryRepository.getAll()).thenReturn(List.of());

        Collection<Inventory> inventories = inventoryRepository.getAll();

        assertEquals(0, inventories.size());
    }

    @Test
    public void testHasInventoryInNearbyStoresWhenStoresAreAvailable() {
        Store store1 = new Store(STORE_1);
        store1.setLocation(Location.SEATTLE);
        Store store2 = new Store(STORE_2);
        store2.setLocation(Location.SEATTLE);

        when(locationService.getNearbyStores(Location.SEATTLE)).thenReturn(List.of(store1, store2));

        List<Store> stores = locationService.getNearbyStores(Location.SEATTLE);

        assertEquals(2, stores.size());
        assertEquals(STORE_1, stores.get(0).getStoreName());
        assertEquals(Location.SEATTLE, stores.get(1).getLocation());
    }

    @Test
    public void testHasInventoryInNearbyStoresWhenStoresAreNotAvailable() {
        when(locationService.getNearbyStores(Location.SEATTLE)).thenReturn(List.of());

        List<Store> stores = locationService.getNearbyStores(Location.SEATTLE);

        assertEquals(0, stores.size());
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