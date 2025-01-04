package com.producerConsumer.Backend.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
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

    @MessageMapping("/simulation")
    @SendTo("/topic/updates")
    public void  simulation(@RequestBody List<shapeDTO> dto) throws InterruptedException {
        serviceFacade.startSimulation(dto);
    }
    @PostMapping("/addProduct")
    public void addProduct(){
        serviceFacade.addProduct();
    }
    @PostMapping("/deleteSimulation")
    public void deleteSimulation(){
        serviceFacade.deleteSimulation();
    }
    @PostMapping("/endSimulation")
    public void endSimulation(){
        serviceFacade.endSimulation();
    }
    @PostMapping("/update")
    public List<shapeDTO> updateSimulation(){
        List<shapeDTO> dtos = new ArrayList<>();
        for(Map.Entry<String, shape> entry : serviceFacade.getProject().getShapes().entrySet()){
            shapeDTO dto = new shapeDTO();
            dto.name = entry.getValue().getName();
            dto.x = entry.getValue().getX();
            dto.y = entry.getValue().getY();
            dto.color = entry.getValue().getColor();
            dto.id = entry.getValue().getId();
            dto.text = entry.getValue().getText();
            dtos.add(dto);
        }
        return dtos;
    }
}
 