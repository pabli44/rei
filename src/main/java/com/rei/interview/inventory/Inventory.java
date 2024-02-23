package com.rei.interview.inventory;

import com.rei.interview.product.Product;
import com.rei.interview.rs.Location;

import java.util.List;

public class Inventory {

    private List<Product> products;
    private double totalValue;
    private Location location;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
