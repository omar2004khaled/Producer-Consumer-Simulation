package com.producerConsumer.Backend.Service;

import com.producerConsumer.Backend.Service.Model.Machine;
import com.producerConsumer.Backend.Service.Model.Queue;

public interface Observer {
    void update(Machine machine);
    void update(Queue queue);
}