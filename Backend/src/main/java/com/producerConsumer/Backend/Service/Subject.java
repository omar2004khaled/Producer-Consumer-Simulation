package com.producerConsumer.Backend.Service;

import com.producerConsumer.Backend.Service.Model.Machine;

public interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers(Machine machine);
}

