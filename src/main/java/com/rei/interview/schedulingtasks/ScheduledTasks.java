package com.rei.interview.schedulingtasks;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.rei.interview.inventory.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private InventoryService inventoryService;

    @Autowired
    public ScheduledTasks(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void loadInventoryWeekly() throws IOException {
        inventoryService.readInInventory();
        log.info("Inventory updated at: {}", dateFormat.format(new Date()));
    }
}
