package com.producerConsumer.Backend.Service.simulation;

import java.util.List;
import java.util.Map;

import com.producerConsumer.Backend.Service.Model.Link;
import com.producerConsumer.Backend.Service.Model.shape;

public class ProjectMemento {
    private final Map<String, shape> shapes;
    public ProjectMemento(Map<String, shape> shapes) {
        this.shapes = shapes;
    }
    public Map<String, shape> getShapes() {
        return shapes;
    }
}
