package com.producerConsumer.Backend.Service.Model;

import java.util.List;
import java.util.ArrayList;

public class Machine extends shape implements Runnable{

    @Override
    public void run() {
        try{
            Thread.sleep(time*1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        queue.addtoProduct(product);
        product=null;
        machineNotfiyfree();
    }
    private List<Queue> queues=new ArrayList<Queue>();
    private Queue queue;
    private Product product;
    private int time;

    public Machine() {
    }
    public Machine(shapeDTO dto,int time) {
        super(dto);
        this.time=time;
    }
    public List<Queue> getQueues() {
        return queues;
    }
    public void setQueues(List<Queue> queues) {
        this.queues = queues;
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
    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public void addQueue(Queue queue) {
        this.queues.add(queue);
    }
    public void removeQueue(Queue queue) {
        this.queues.remove(queue);
    }
    public void addProduct(Product product) {
        this.queue.addProduct(product);
    }
    public void removeProduct(Product product) {
        this.queue.removeProduct(product);
    }
    public void machineNotfiyfree(){
        this.setColor("#222");
        for(Queue queue : queues){
            queue.machinesfree(this.getId());
        }
    }
    public synchronized void setProduct(Product product){
        this.product=product; // Only one thread can execute this method at a time.
        machineNotfiyfree();
        Thread thread = new Thread(this::run);
        thread.start();
        this.setColor(product.getColor());
    }
    public void machineNotfiybusy(){
        for(Queue queue : queues){
            queue.machinesbusy(this.getId());
        }
    }
}
