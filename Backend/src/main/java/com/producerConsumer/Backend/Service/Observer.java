package com.producerConsumer.Backend.Service;

import com.producerConsumer.Backend.Service.Model.Queue;

public interface Observer {
    void update(Queue queue);
}