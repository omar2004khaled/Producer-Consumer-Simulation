package com.producerConsumer.Backend.Service.Model;

import java.util.List;

public class shapeDTO {
    public String name;
    public double x;
    public double y;
    public String color;
    public String text;
    public String id;
    public List<String> inQueues;
    public String nextQueue;
    public List<String> inMachines;
    public List<String> outMachines;
}
