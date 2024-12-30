package com.producerConsumer.Backend.Service.Model;

import java.util.ArrayList;

public class Link {
    private String to;
    private String from;
    private ArrayList<Double> points;

    public Link() {
    }
    public Link(String to, String from, ArrayList<Double> points) {
        this.to = to;
        this.from = from;
        this.points = points;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public ArrayList<Double> getPoints() {
        return points;
    }
    public void setPoints(ArrayList<Double> points) {
        this.points = points;
    }
}
