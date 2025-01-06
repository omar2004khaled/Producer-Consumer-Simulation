package com.producerConsumer.Backend.Service.Model;

import com.producerConsumer.Backend.Service.Observer;
import com.producerConsumer.Backend.Service.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Machine extends shape implements Observer, Runnable {
    private String queueId;
    private Product product;
    private int time;
    private Map<String, Queue> queues;
    private List<String> inQueues; // List of inQueues
    private boolean busy;

    public Machine() {
    }

    public Machine(shapeDTO dto, int time, Map<String, Queue> queues) {
        super(dto);
        this.queueId = dto.nextQueue; // Assuming dto has nextQueueId
        this.time = time;
        this.queues = queues;
        this.inQueues = dto.inQueues; // Initialize inQueues from dto

        // Attach this machine to all inQueues
        for (String queueId : inQueues) {
            attach(queueId);
        }
    }

    @Override
    public void run() {
        try {
            busy = true;
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        if (product != null) {
            System.out.println("Machine " + getId());
            Queue queue = queues.get(queueId);
            if (queue != null) {
                queue.addtoProduct(product);
            }
            product = null;
            machineNotifyFree();
            busy = false;
        }
    }

    private void machineNotifyFree() {
        System.out.println("Machine " + getId() + " is now free and available.");
        Queue queue = queues.get(queueId);
        if (queue != null) {
            queue.machinesfree(getId());
            notifyObservers(this,queues);
        }
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

    public void setProduct(Product product) {
        this.product = product;
        System.out.println(product.getColor());
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
}