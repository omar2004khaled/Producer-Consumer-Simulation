package com.producerConsumer.Backend.Service.simulation;

import java.util.HashMap;
import java.util.Map;

import com.producerConsumer.Backend.Service.Model.Queue;
import com.producerConsumer.Backend.Service.Model.shape;

public class Project {
    private Map<String, shape> shapes = new HashMap<>();
    public void addShape(shape newShape) {
        if (newShape != null) {
            shapes.put(newShape.getId(), newShape);
        } else {
            System.err.println("Attempted to add a null shape");
        }
    }
    public Map<String, shape> getShapes() {
        return shapes;
    }
    public ProjectMemento createMemento(){
        return new ProjectMemento(shapes);
    }
    public void restoreFromMemento(ProjectMemento memento){
        shapes=new HashMap<>(memento.getShapes());
    }
    public Queue findFirstQueue() {
        for (shape s : shapes.values()) {
            if (s instanceof Queue queue) { 
                if (queue.getInMachines().isEmpty()) {
                    return queue;
                }
            }
        }
        return null; 
    }
    public Queue findLastQueue() {
        for (shape s : shapes.values()) {
            if (s instanceof Queue queue) { 
                if (queue.getOutMachines().isEmpty()) {
                    return queue;
                }
            }
        }
        return null;
    }
}
