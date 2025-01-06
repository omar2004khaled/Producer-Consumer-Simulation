package com.producerConsumer.Backend.Service;

import com.producerConsumer.Backend.Service.Model.Machine;

public interface Observer {
    void update(Machine machine);
} 