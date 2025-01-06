package com.producerConsumer.Backend.Service.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.producerConsumer.Backend.Service.Observer;
import com.producerConsumer.Backend.Service.Subject;

public class Queue extends shape implements Subject, Runnable {

    private Map<String, Machine> machinestoMap = new HashMap<>();
    private Map<String, Machine> machinesfreeMap = new HashMap<>();
    private List<Observer> observers = new ArrayList<>(); // List of observers (machines)
    private List<Product> products = new ArrayList<>();
    private Thread thread;

    public Queue() {
    }

    public Queue(shapeDTO dto) {
        super(dto);
    }
    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }
    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }
    @Override
    public void notifyObservers(Machine machine) {
        for (Observer observer : observers) {
            observer.update(machine);
        }
    }
    public void addtoProduct(Product product) {
        super.getProducts().add(product);
        if (super.getProducts().size() == 1) {
            this.thread = new Thread(this::run);
            thread.start();
        }
    }
    @Override
    public void run() {
        while (!super.getProducts().isEmpty()) {
            try {
                Thread.sleep(1000); // Simulate processing time
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            if (!machinesfreeMap.isEmpty()) {
                Machine machine = machinesfreeMap.entrySet().iterator().next().getValue();
                machine.setProduct(super.getProducts().get(0));
                super.getProducts().remove(0);
                notifyObservers(machine);
            }
        }
    }
    public void addMachine(String id, Machine machine) {
        machinestoMap.put(id, machine);
    }
    public void machinesfree(String id) {
        Machine machine = machinestoMap.get(id);
        if (machine != null) {
            machinesfreeMap.put(id, machine);
            notifyObservers(machine); // Notify that the machine is free
        }
    }
    public void machinesbusy(String id) {
        machinesfreeMap.remove(id);
    }
    public void addProduct(Product product) {
        this.products.add(product);
    }
    public void removeProduct(Product product) {
        this.products.remove(product);
    }
    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public Map<String, Machine> getMachinestoMap() {
        return machinestoMap;
    }
    public void setMachinestoMap(Map<String, Machine> machinestoMap) {
        this.machinestoMap = machinestoMap;
    }
    public Map<String, Machine> getMachinesfreeMap() {
        return machinesfreeMap;
    }
    public void setMachinesfreeMap(Map<String, Machine> machinesfreeMap) {
        this.machinesfreeMap = machinesfreeMap;
    }
    public List<Observer> getObservers() {
        return observers;
    }
    public void setObservers(List<Observer> observers) {
        this.observers = observers;
    }
    public Thread getThread() {
        return thread;
    }
    public void setThread(Thread thread) {
        this.thread = thread;
    }
    public static Queue findFirstQueue(List<Queue> queues) {
            for (Queue queue : queues) {
                if (queue.getInMachines().isEmpty()) {
                    return queue;
                }
            }
            return null;
        }
        public List<Machine> getInMachines() {
            return new ArrayList<>(machinestoMap.values());  
        }
    public static Queue findLastQueue(List<Queue> queues) {
        for (Queue queue : queues) {
            if (queue.getOutMachines().isEmpty()) {
                return queue;
            }
        }
        return null; 
    }
    public List<Machine> getOutMachines() {
        return new ArrayList<>(machinesfreeMap.values());  
    }
}
