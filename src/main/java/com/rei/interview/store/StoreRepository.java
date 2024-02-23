package com.rei.interview.store;

import com.rei.interview.util.Cache;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;

@Repository
public class StoreRepository {

    private Map<String, Store> stores = new Cache<>();

    public Collection<Store> getAll() {
        return stores.values();
    }

}
