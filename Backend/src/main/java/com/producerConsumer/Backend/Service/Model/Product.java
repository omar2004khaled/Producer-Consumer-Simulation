package com.producerConsumer.Backend.Service.Model;

public class Product {
    private static int index = 0;
    private String color;
    private static String[] colorStrings = {"Red", "Green", "Blue", "Yellow", "Black", "Pink", "Purple", "Orange", "Brown"};

    public Product() {
        int randomIndex = (index++) % colorStrings.length;
        this.color = colorStrings[randomIndex];
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}