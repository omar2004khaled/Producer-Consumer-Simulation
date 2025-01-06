package com.producerConsumer.Backend.Service.simulation;

import java.util.HashMap;
import java.util.Map;
import com.producerConsumer.Backend.Service.Model.shape;

public class Project {
    private Map<String, shape> shapes = new HashMap<>();
    private int productIn;

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

    public void setProductIn(int productIn) {
        this.productIn = productIn;
    }

    public int getProductIn() {
        return productIn;
    }

    public ProjectMemento createMemento() {
        return new ProjectMemento(shapes, productIn);
    }

    public void restoreFromMemento(ProjectMemento memento) {
        shapes = new HashMap<>(memento.getShapes());
        productIn = memento.getProductIn();
    }
}