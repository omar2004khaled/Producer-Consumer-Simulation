package com.producerConsumer.Backend.Service.simulation;

import java.util.List;
import java.util.concurrent.TimeUnit;
import com.producerConsumer.Backend.Service.Model.Product;
import com.producerConsumer.Backend.Service.Model.shapeDTO;
import com.producerConsumer.Backend.Service.Model.shapeFactory;
import com.producerConsumer.Backend.Service.Model.Queue;
import com.producerConsumer.Backend.Service.Model.shape;

public class ServiceSimulation {
    private static volatile ServiceSimulation simulate = null;
    private Project project = new Project();
    private Caretaker caretaker = new Caretaker();
    private int productIn = 0;  
    private Boolean flag = false;

    private ServiceSimulation() {
    }
    public void setProductIn(int productIn) {
        this.productIn = productIn;
    }

    public void saveState() { 
        caretaker.setProjectMemento(project.createMemento());
    }

    public void restoreState() {
        project.restoreFromMemento(caretaker.getProjectMemento());
    }

    public static synchronized ServiceSimulation getInstance() {
        if (simulate == null) {
            simulate = new ServiceSimulation();
        }
        return simulate;
    }

    public void addProduct() {
        productIn++;
    }

    public void removeProduct() {
        if (productIn > 0) {
            productIn--;
        }
    }

  public void buildProject(List<shapeDTO> shapeDTOs) {
    for (shapeDTO dto : shapeDTOs) {
        shape newShape = shapeFactory.getType(dto);
        if (newShape != null) {
            System.out.println("Adding shape with ID: " + newShape.getId());
            this.project.addShape(newShape);
        } else {
            System.err.println("Shape creation failed for DTO: " + dto);
        }
     }
   }
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getRandomTime() {
        int minimum = 1;
        int maximum = 5;
        return (int) Math.floor(Math.random() * (maximum - minimum + 1) + minimum);
    }

    public void runSimulation() throws InterruptedException {
        while (!flag) {
            TimeUnit.SECONDS.sleep(getRandomTime());
            if (productIn > 0) {
                Queue queue = (Queue) this.project.getShapes().get("1");
                if (queue != null) {
                    saveState(); 
                    queue.addProduct(new Product());
                    productIn--;
                } else {
                    System.err.println("Queue with ID '0' does not exist!");
                }
            }
        }
        flag = false;
    }
    public void stopSimulation() {
        this.productIn = 0;
        this.flag = true;
    }
    public void start() {
        this.flag = false;
    }
}