package com.producerConsumer.Backend.Service.Model;


public class shapeFactory {
    public shape getType(shapeDTO dto) {
        if (dto.name.equals("circle")) {
            int min = 2;
            int max = 10;
            int randomTime = (int)Math.floor(Math.random() *(max - min + 1) + min);
            return new Machine(dto,randomTime);
        } else if (dto.name.equals("rectangle")) {
            return new Queue(dto);
        } else {
            return null;
        }
    }
}
