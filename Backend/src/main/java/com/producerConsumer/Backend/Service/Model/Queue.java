package com.producerConsumer.Backend.Service.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Queue extends shape implements Runnable {

    @Override
    public void run() {
        while(!super.getProducts().isEmpty()){
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            if(!machinesfreeMap.isEmpty()){
                machinesfreeMap.entrySet().iterator().next().getValue().setProduct(super.getProducts().get(0));
                super.getProducts().remove(0);

            }
        }
    }
    private Map<String, Machine> machinestoMap = new HashMap<>();
    private Map<String, Machine> machinesfreeMap = new HashMap<>();
    private String proudctinqueue;
    private List<Product> products =new ArrayList<>();
    private List<String>ids = new ArrayList<>();
    private Thread thread;
    
    public Queue() {
    }
    public Queue(shapeDTO dto) {
        super(dto);
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
    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public List<String> getIds() {
        return ids;
    }
    public void setIds(List<String> ids) {
        this.ids = ids;
    }
    public void addProduct(Product product) {
        this.products.add(product);
    }
    public void removeProduct(Product product) {
        this.products.remove(product);
    }
    public void addId(String id) {
        this.ids.add(id);
    }
    public void removeId(String id) {
        this.ids.remove(id);
    }
    public String getProudctinqueue() {
        return proudctinqueue;
    }
    public void setProudctinqueue(String proudctinqueue) {
        this.proudctinqueue = proudctinqueue;
    }
    public void machinesfree(String id){
        machinesfreeMap.put(id,machinestoMap.get(id)); 
        return;
    }
    public void machinesfree(Machine machine){
        machinesfreeMap.put(machine.getId(),machine); 
        return;
    }
    public void machinesbusy(String id){
        machinesfreeMap.remove(id); 
    }
    public void machinesbusy(Machine machine){
        machinesfreeMap.remove(machine.getId()); 
    }
    public void addtoProduct(Product product){
        super.getProducts().add(product);
        if(super.getProducts().size()==1){
            this.thread = new Thread(this::run);
            thread.start();
        }
    }
}