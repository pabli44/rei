package com.rei.interview.location;

import com.rei.interview.rs.Location;
import com.rei.interview.store.Store;
import com.rei.interview.store.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private StoreRepository storeRepository;

    public LocationService(StoreRepository storeRepository){
        this.storeRepository = storeRepository;
    }

    public List<Store> getNearbyStores(Location location) {
        return storeRepository.getAll().stream().filter(loc -> loc.equals(location)).collect(Collectors.toList());
    }

}
