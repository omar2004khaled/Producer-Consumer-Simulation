package com.producerConsumer.Backend.Service.simulation;

import java.util.List;
import java.util.concurrent.TimeUnit;
import com.producerConsumer.Backend.Service.Model.Link;
import com.producerConsumer.Backend.Service.Model.Product;
import com.producerConsumer.Backend.Service.Model.shapeDTO;
import com.producerConsumer.Backend.Service.Model.shapeFactory;

public class ServiceSimulation {
    private static ServiceSimulation simulate=null;
    private Project project=new Project();
    private Caretaker caretaker=new Caretaker();
    private int proudctIn;
    private ServiceSimulation() {
    }
    public void saveSate(){
        caretaker.setProjectMemento(project.createMemento());
    }
    public void restoreState(){
        project.restoreFromMemento(caretaker.getProjectMemento());
    }
    public static synchronized ServiceSimulation getInstance(){
        if(simulate==null){
            simulate=new ServiceSimulation();
        }
        return simulate;
    }
    public void addProduct(){
        proudctIn++;
    }
    public void removeProduct(){
        proudctIn--;
    }
    public void buildProject(List<shapeDTO>shapeDTOs){
        for(shapeDTO dto : shapeDTOs){
            if(dto.name.equals("line")){
                // this.project.addLink((Link) shapeFactory.getType(dto));
            }
            this.project.addShape(shapeFactory.getType(dto));
        }
    }
    public  Project getProject(){
        return project;
    }
    public Project setProject(Project project){
        return this.project=project;
    }
    private Boolean flag = false;
    public int getRandomTime(){
        int minimum = 1;
        int maximum = 5;
        int randomtime = (int)Math.floor(Math.random() *(maximum - minimum + 1) + minimum);
        return randomtime;
    }
    public void runSimulation()throws InterruptedException{
        int input = proudctIn;
        Project project = new Project();
        while(!flag){
            TimeUnit.SECONDS.sleep(getRandomTime());
            if(proudctIn>0){
                ((com.producerConsumer.Backend.Service.Model.Queue) this.project.getShapes().get("0")).addProduct(new Product());
                simulate.saveSate();
                proudctIn--;
            }
        }
        flag = false;
    }
    public void stopSimulation(){
        this.proudctIn=0;
        this.flag = true;
    }
    public void start(){
        this.flag=false;
    } 
}
