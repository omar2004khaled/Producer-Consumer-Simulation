package com.producerConsumer.Backend.Service;

import com.producerConsumer.Backend.Service.Model.Queue;

public interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers(Queue queue);
}