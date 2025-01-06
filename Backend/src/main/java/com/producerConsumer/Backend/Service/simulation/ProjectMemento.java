package com.producerConsumer.Backend.Service.simulation;

import java.util.Map;
import com.producerConsumer.Backend.Service.Model.shape;

public class ProjectMemento {
    private final Map<String, shape> shapes;
    private final int productIn;

    public ProjectMemento(Map<String, shape> shapes, int productIn) {
        this.shapes = shapes;
        this.productIn = productIn;
    }

    public Map<String, shape> getShapes() {
        return shapes;
    }

    public int getProductIn() {
        return productIn;
    }
}