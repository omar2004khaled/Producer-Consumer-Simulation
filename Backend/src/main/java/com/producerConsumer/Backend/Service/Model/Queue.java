package com.producerConsumer.Backend.Service.Model;

import com.producerConsumer.Backend.Service.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Queue extends shape implements Observer, Runnable {

    private List<String> machinestoList = new ArrayList<>();
    private List<String> machinesfreeList = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private Thread thread;
    private Map<String, Machine> machinesMap; // Map to store all machines by their IDs

    public Queue() {
    }

    public Queue(shapeDTO dto, Map<String, Machine> machinesMap) {
        super(dto);
        this.machinestoList = dto.outMachines;
        this.machinesMap = machinesMap;
    }

    @Override
    public void update(Queue queue) {
        System.out.println("Queue " + getId() + " received an update from a machine.");
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
            if (!machinesfreeList.isEmpty()) {
                String machineId = machinesfreeList.get(0);
                Machine machine = machinesMap.get(machineId);
                if (machine != null) {
                    machine.setProduct(super.getProducts().get(0));
                    super.getProducts().remove(0);
                }
            }
        }
    }

    public void addMachine(String id) {
        machinestoList.add(id);
    }

    public void machinesfree(String id) {
        if (machinestoList.contains(id)) {
            machinesfreeList.add(id);
            Machine machine = machinesMap.get(id);
            if (machine != null) {
                machine.notifyObservers(this); // Notify that the machine is free
            }
        }
    }

    public void machinesbusy(String id) {
        machinesfreeList.remove(id);
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

    public List<String> getMachinestoList() {
        return machinestoList;
    }

    public void setMachinestoList(List<String> machinestoList) {
        this.machinestoList = machinestoList;
    }

    public List<String> getMachinesfreeList() {
        return machinesfreeList;
    }

    public void setMachinesfreeList(List<String> machinesfreeList) {
        this.machinesfreeList = machinesfreeList;
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
        List<Machine> machines = new ArrayList<>();
        for (String id : machinestoList) {
            Machine machine = machinesMap.get(id);
            if (machine != null) {
                machines.add(machine);
            }
        }
        return machines;
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
        List<Machine> machines = new ArrayList<>();
        for (String id : machinesfreeList) {
            Machine machine = machinesMap.get(id);
            if (machine != null) {
                machines.add(machine);
            }
        }
        return machines;
    }
}