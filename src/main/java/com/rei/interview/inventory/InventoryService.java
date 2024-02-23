package com.rei.interview.inventory;

import com.rei.interview.location.LocationService;
import com.rei.interview.product.Product;
import com.rei.interview.rs.Location;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final LocationService locationService;
    private final InventoryRepository inventoryRepository;

    public InventoryService(final LocationService locationService, final InventoryRepository inventoryRepository) {
        this.locationService = locationService;
        this.inventoryRepository = inventoryRepository;
    }

    @PostConstruct
    public void readInInventory() throws IOException {
        try(Reader in = new InputStreamReader(getClass().getResourceAsStream("/product_inventory.csv"))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader("productId", "location", "quantity")
                    .withFirstRecordAsHeader()
                    .parse(in);

            for (CSVRecord record : records) {
                String productId = record.get("productId");
                String location = record.get("location");
                int quantity = Integer.valueOf(record.get("quantity"));
                System.out.println(productId + "\t" + location + "\t" + quantity);
            }
        }
    }

    public boolean hasInventoryOnline(Product product, int quantity) {
         return inventoryRepository.getAll().stream()
                                   .filter(inv -> inv.getLocation().equals(Location.ONLINE))
                    .flatMap(inventory -> inventory.getProducts().stream())
                    .filter(pro -> pro.equals(product)).collect(Collectors.toList()).size()>=quantity;
    }

    public boolean hasInventoryInNearbyStores(Product product, int quantity, Location currentLocation) {
        if(locationService.getNearbyStores(currentLocation).size()>0){
            return inventoryRepository.getAll().stream()
                                      .filter(inventory -> inventory.getLocation().equals(currentLocation))
                                      .flatMap(inventory -> inventory.getProducts().stream())
                                      .filter(pro -> pro.equals(product))
                                      .collect(Collectors.toList()).size()>=quantity;
        }

        return false;
    }

}
