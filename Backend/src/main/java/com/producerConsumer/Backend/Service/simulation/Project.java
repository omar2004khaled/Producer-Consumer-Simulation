package com.producerConsumer.Backend.Service.simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.producerConsumer.Backend.Service.Model.Link;
import com.producerConsumer.Backend.Service.Model.shape;

public class Project {
    private Map<String, shape> shapes=new HashMap<>();
    private List<Link> links=new ArrayList<>();

    public Project() {
    }
    
    public void addShape(shape shape){
        shapes.put(shape.getId(),shape);
        return;
    }
    public void addLink(Link link){
        links.add(link);
        return;
    }
    public Map<String, shape> getShapes() {
        return shapes;
    }
    public void setShapes(Map<String, shape> shapes) {
        this.shapes = shapes;
    }
    public List<Link> getLinks() {
        return links;
    }
    public void setLinks(List<Link> links) {
        this.links = links;
    }
    public ProjectMemento createMemento(){
        return new ProjectMemento(shapes,links);
    }
    public void restoreFromMemento(ProjectMemento memento){
        shapes=new HashMap<>(memento.getShapes());
        links=List.copyOf(memento.getLinks());
    }
}
