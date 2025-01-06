package com.producerConsumer.Backend.Service.Model;

import com.producerConsumer.Backend.Service.Observer;

public class Machine extends shape implements Observer, Runnable {
    private Queue queue; 
    private Product product; 
    private int time;  
    public Machine() {
    }
    public Machine(shapeDTO dto, int time) {
        super(dto);
        this.queue = dto.nextQueue; 
        this.time = time;
    }
    @Override
    public void update(Machine machine) {
        System.out.println("Machine " + machine.getId() + " received a product from the queue.");
    }
    @Override
    public void run() {
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        if (product != null) {
            System.out.println("Machine " + getId());
            queue.addtoProduct(product);  
            product = null;  
            machineNotifyFree(); 
        }
    }
    private void machineNotifyFree() {
        System.out.println("Machine " + getId() + " is now free and available.");
        queue.machinesfree(getId()); 
    }
    public void machineNotifyBusy() {
        queue.machinesbusy(getId()); 
    }
    public Queue getQueue() {
        return queue;
    }
    public void setQueue(Queue queue) {
        this.queue = queue;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;  
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
}
