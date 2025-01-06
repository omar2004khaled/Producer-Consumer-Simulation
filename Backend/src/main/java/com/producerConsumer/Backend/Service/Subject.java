package com.producerConsumer.Backend.Service;

import com.producerConsumer.Backend.Service.Model.Machine;
import com.producerConsumer.Backend.Service.Model.Queue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Subject {
    protected List<String> observerIds = new ArrayList<>(); // List of observer IDs

    public void attach(String observerId) {
        observerIds.add(observerId);
    }

    public void detach(String observerId) {
        observerIds.remove(observerId);
    }

    public void notifyObservers(Machine machine, Map<String, Queue> queues) {
        for (String observerId : observerIds) {
            Queue queue = queues.get(observerId);
            if (queue != null) {
                queue.update(machine);
            }
        }
    }

    public void notifyObservers(Queue queue, Map<String, Machine> machines) {
        for (String observerId : observerIds) {
            Machine machine = machines.get(observerId);
            if (machine != null) {
                machine.update(queue);
            }
        }
    }
}