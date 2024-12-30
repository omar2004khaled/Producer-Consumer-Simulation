package com.producerConsumer.Backend.Service.simulation;

import java.util.List;
import java.util.Map;

import com.producerConsumer.Backend.Service.Model.Link;
import com.producerConsumer.Backend.Service.Model.shape;

public class ProjectMemento {
    private final Map<String, shape> shapes;
    private final List<Link> links;
    public ProjectMemento(Map<String, shape> shapes, List<Link> links) {
        this.shapes = shapes;
        this.links = links;
    }
    public Map<String, shape> getShapes() {
        return shapes;
    }
    public List<Link> getLinks() {
        return links;
    }
}
