package com.producerConsumer.Backend.Service.Model;
public class shapeFactory {
    public static shape getType(shapeDTO dto) {
        if (dto.name.equals("machines")) {
            int min = 5;
            int max = 20;
            int randomTime = (int)Math.floor(Math.random() *(max - min + 1) + min);
            return new Machine(dto,randomTime);
        } else if (dto.name.equals("queues")) {
            return new Queue(dto);
        } else {
            return null;
        }
    }
}
