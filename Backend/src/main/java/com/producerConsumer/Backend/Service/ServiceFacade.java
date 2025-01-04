package com.producerConsumer.Backend.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.producerConsumer.Backend.Service.Model.shapeDTO;
import com.producerConsumer.Backend.Service.simulation.Project;
import com.producerConsumer.Backend.Service.simulation.ServiceSimulation;

@Service
public class ServiceFacade {
    private ServiceSimulation simulate =ServiceSimulation.getInstance();
    public ServiceFacade(){}
    public void startSimulation(List<shapeDTO>dto) throws  InterruptedException {
        simulate.start();
        simulate.buildProject(dto);
        simulate.runSimulation();
    }
    public void  addProduct(){
        simulate.addProduct();
    }
    public void deleteSimulation(){
        simulate.removeProduct();
    }
    public void endSimulation(){
        simulate.stopSimulation();
    }
    public Project getProject(){
        return simulate.getProject();
    }
}
