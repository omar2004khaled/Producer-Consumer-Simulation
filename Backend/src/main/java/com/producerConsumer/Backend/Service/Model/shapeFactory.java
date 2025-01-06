package com.producerConsumer.Backend.Service.Model;

import com.producerConsumer.Backend.Service.simulation.ServiceSimulation;

import java.util.Map;

import com.producerConsumer.Backend.Controller.MyWebSocketHandler;

public class shapeFactory {
    public static shape getType(shapeDTO dto, Map<String, Queue> queues, Map<String, Machine> machines, MyWebSocketHandler webSocketHandler) {
        ServiceSimulation simulation = ServiceSimulation.getInstance();

        if (dto.name.equals("Machine")) {
            int min = 5;
            int max = 20;
            int randomTime = (int) Math.floor(Math.random() * (max - min + 1) + min);
            Machine machine = new Machine(dto, randomTime, queues, webSocketHandler);
            machines.put(dto.id, machine); // Add the machine to the map with its ID
            return machine;
        } else if (dto.name.equals("Queue")) {
            Queue queue = new Queue(dto, machines);
            queues.put(dto.id, queue); // Add the queue to the map with its ID
            if (dto.inMachines.isEmpty()) {
                simulation.setFirstQueue(queue);
            }
            if (dto.outMachines.isEmpty()) {
                simulation.setLastQueue(queue);
            }
            return queue;
        } else {
            return null;
        }
    }
}