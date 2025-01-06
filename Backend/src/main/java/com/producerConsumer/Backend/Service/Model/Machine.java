package com.producerConsumer.Backend.Service.Model;

import com.producerConsumer.Backend.Service.Observer;
import com.producerConsumer.Backend.Controller.MyWebSocketHandler;
import java.util.List;
import java.util.Map;

public class Machine extends shape implements Observer, Runnable {
    private String queueId;
    private Product product;
    private int time;
    private Map<String, Queue> queues;
    private List<String> inQueues; // List of inQueues
    private boolean busy;
    private MyWebSocketHandler webSocketHandler;

    public Machine() {
    }

    public Machine(shapeDTO dto, int time, Map<String, Queue> queues, MyWebSocketHandler webSocketHandler) {
        super(dto);
        this.queueId = dto.nextQueue; // Assuming dto has nextQueueId
        this.time = time;
        this.queues = queues;
        this.inQueues = dto.inQueues; // Initialize inQueues from dto
        this.webSocketHandler = webSocketHandler;

        // Attach this machine to all inQueues
        for (String queueId : inQueues) {
            attach(queueId);
        }
    }

    @Override
    public void run() {
        try {
            busy = true;
            Thread.sleep(time * 1000);  // Simulate work duration
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    
        // Process product if available
        if (product != null) {
            System.out.println("Machine " + getId() + " is processing product.");
            Queue queue = queues.get(queueId);  // Get the queue by its ID
            if (queue != null) {
                queue.addProduct(product);  // Add the product to the queue
            }
            product = null;  // Reset the product in the machine
            setColor("white");  // Set machine color to white (indicating idle)
            notifyObservers(this, queues);  // Notify observers about the state change
   // Notify that the machine is free
            busy = false;  // Set machine to not busy
        }
        machineNotifyFree(); 
    }
    
    private void machineNotifyFree() {
        System.out.println("Machine " + getId() + " is now free and available.");
        Queue queue = queues.get(queueId);
        if (queue != null) {
            queue.machinesfree(getId());  // Notify that machine is free
            notifyObservers(this, queues);
        }
        notifyWebSocketHandler();  // Notify the client via WebSocket
    }

    public void machineNotifyBusy() {
        Queue queue = queues.get(queueId);
        if (queue != null) {
            queue.machinesbusy(getId());
        }
    }

    public Product getProduct() {
        return product;
    }

    public synchronized void setProduct(Product product) {
        this.product = product;
        System.out.println(product.getColor());
        this.setColor(product.getColor());
        if (webSocketHandler != null) {
            try {
                System.out.println("hello");
                webSocketHandler.broadcastUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        machineNotifyBusy();
        Thread thread = new Thread(this::run);
        thread.start();
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public void update(Machine machine) {
        // Handle updates from other machines if needed
    }

    @Override
    public void update(Queue queue) {
        // Handle updates from queues
        if (!queue.getProducts().isEmpty() && !busy) {
            setProduct(queue.getProducts().remove(0));
        }
    }

    private void notifyWebSocketHandler() {
        if (webSocketHandler != null) {
            try {
                webSocketHandler.broadcastUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}