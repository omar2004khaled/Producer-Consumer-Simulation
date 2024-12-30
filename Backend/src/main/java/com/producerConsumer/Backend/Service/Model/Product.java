package com.producerConsumer.Backend.Service.Model;

public class Product {
    private static int index=0;
    private String color;
    private static String[] coloStrings = {"Red", "Green", "Blue", "Yellow", "Black", "White", "Pink", "Purple", "Orange", "Brown"};

    public Product() {
        int randomIndex = (index++) % coloStrings.length;
        this.color = coloStrings[randomIndex];
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

}
