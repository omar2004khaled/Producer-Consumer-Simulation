package com.producerConsumer.Backend.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.producerConsumer.Backend.Service.ServiceFacade;
import com.producerConsumer.Backend.Service.Model.shape;
import com.producerConsumer.Backend.Service.Model.shapeDTO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin("*")
@RequestMapping("/produce")
public class Control {

    @Autowired
    private ServiceFacade serviceFacade;

    // @MessageMapping("/simulation") 
    // @SendTo("/topic/updates")
    @PostMapping("/simulation/{Prouctno}")
    public void simulation(@RequestBody List<shapeDTO> dto,@PathVariable int Prouctno) throws InterruptedException {
        serviceFacade.startSimulation(dto, Prouctno);
    }

    @PostMapping("/addProduct")
    public void addProduct() {
        serviceFacade.addProduct();
    }

    @PostMapping("/deleteSimulation")
    public void deleteSimulation() {
        serviceFacade.deleteSimulation();
    }

    @PostMapping("/endSimulation")
    public void endSimulation() {
        serviceFacade.endSimulation();
    }
    @PostMapping("/reSimulation")
    public void reSimulation() throws InterruptedException {
        serviceFacade.reSimulation();
    }
}
 