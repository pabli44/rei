package com.rei.interview.inventory;

import com.rei.interview.util.Cache;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;

@Repository
public class InventoryRepository {

    private Map<String, Inventory> inventories = new Cache<>();

    public Collection<Inventory> getAll() {
        return inventories.values();
    }

}
