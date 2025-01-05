package com.producerConsumer.Backend.Service.simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.producerConsumer.Backend.Service.Model.Link;
import com.producerConsumer.Backend.Service.Model.shape;

public class Project {
    private Map<String, shape> shapes=new HashMap<>();

    public Project() {
    }
    
    public void addShape(shape shape){
        shapes.put(shape.getId(),shape);
        return;
    }
    public Map<String, shape> getShapes() {
        return shapes;
    }
    public void setShapes(Map<String, shape> shapes) {
        this.shapes = shapes;
    }
    public ProjectMemento createMemento(){
        return new ProjectMemento(shapes);
    }
    public void restoreFromMemento(ProjectMemento memento){
        shapes=new HashMap<>(memento.getShapes());
    }
}
