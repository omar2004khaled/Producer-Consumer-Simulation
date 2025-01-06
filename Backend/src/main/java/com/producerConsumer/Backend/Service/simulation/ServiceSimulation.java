package com.producerConsumer.Backend.Service.simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import com.producerConsumer.Backend.Service.Model.Product;
import com.producerConsumer.Backend.Service.Model.shapeDTO;
import com.producerConsumer.Backend.Service.Model.shapeFactory;
import com.producerConsumer.Backend.Service.Model.Queue;
import com.producerConsumer.Backend.Service.Model.shape;
import com.producerConsumer.Backend.Service.Model.Machine;
import com.producerConsumer.Backend.Controller.MyWebSocketHandler;

public class ServiceSimulation {

    private static volatile ServiceSimulation simulate = null;
    private Project project = new Project();
    private Caretaker caretaker = new Caretaker();
    private int productIn = 0;
    private Boolean flag = false;
    private Map<String, Queue> queues = new HashMap<>(); // Map to store queues by their IDs
    private Map<String, Machine> machines = new HashMap<>(); // Map to store machines by their IDs
    private Queue lastQueue;
    private Queue firstQueue;
    private MyWebSocketHandler webSocketHandler;

    private ServiceSimulation() {
    }

    public void setWebSocketHandler(MyWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    public void setProductIn(int productIn) {
        this.productIn = productIn;
        project.setProductIn(productIn);
    }

    public void saveState() {
        caretaker.setProjectMemento(project.createMemento());
    }

    public void restoreState() {
        project.restoreFromMemento(caretaker.getProjectMemento());
        productIn = project.getProductIn();
    }

    public static synchronized ServiceSimulation getInstance() {
        if (simulate == null) {
            simulate = new ServiceSimulation();
        }
        return simulate;
    }

    public void addProduct() {
        productIn++;
        project.setProductIn(productIn);
        notifyWebSocketHandler();
    }

    public void removeProduct() {
        if (productIn > 0) {
            productIn--;
            project.setProductIn(productIn);
            notifyWebSocketHandler();
        }
    }

    public void buildProject(List<shapeDTO> shapeDTOs) {
        for (shapeDTO dto : shapeDTOs) {
            shape newShape = shapeFactory.getType(dto, queues, machines, webSocketHandler); // Pass the maps of queues and machines
            if (newShape != null) {
                System.out.println("Adding shape with ID: " + newShape.getId());
                this.project.addShape(newShape);
            } else {
                System.err.println("Shape creation failed for DTO: " + dto);
            }
        }
        notifyWebSocketHandler();
    }

    public synchronized void resimulate() throws InterruptedException {
        ProjectMemento memento = caretaker.getProjectMemento();
        if (memento != null) {
            // Clear current state and restore from memento
            project = new Project();
            project.restoreFromMemento(memento);
    
            // Recreate shapes and preserve their products
            List<Product> products = new ArrayList<>();
            for (Map.Entry<String, shape> entry : memento.getShapes().entrySet()) {
                if (entry.getValue().getName().equals("Machine")) {
                    // Handle machines differently by resetting product states
                    products.addAll(entry.getValue().getProducts());
                    entry.getValue().setColor("white"); // Default color
                    entry.getValue().setProducts(new ArrayList<>());
                } else {
                    // Non-machine shapes
                    products.addAll(entry.getValue().getProducts());
                    entry.getValue().setProducts(new ArrayList<>());
                }
    
                shape newShape = entry.getValue();
                project.addShape(newShape);
                System.out.println("Recreated shape with ID: " + newShape.getId());
            }
    
            // Recreate product count
            productIn = memento.getProductIn();
            System.out.println("Recreated products count: " + productIn);
    
            // Notify WebSocket handler to update the client
            notifyWebSocketHandler();
    
            // Process the products restored from the memento
            while (!flag) {
                // Wait if no products are available
                if (productIn <= 0) {
                    System.out.println("Waiting for products to become available...");
                    wait();  // Wait until products are available
                }
    
                // Sleep for a random time before processing the next product
                TimeUnit.SECONDS.sleep(getRandomTime());
                if (productIn > 0) {
                    Queue queue = simulate.firstQueue;  // Get the first queue by ID
                    if (queue != null) {
                        saveState();
                        // Add a product from the restored list to the queue
                        queue.addProduct(products.remove(0));  // Remove the first product
                        productIn--;
                        project.setProductIn(productIn);
                        System.out.println("Queue products: " + simulate.getFirstQueue().getProducts());
                        notifyWebSocketHandler();
                    } else {
                        System.err.println("Queue with ID '1' does not exist!");
                    }
                }
            }
            flag = false;
        } else {
            System.err.println("No memento found to resimulate.");
        }
    }
    

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getRandomTime() {
        int minimum = 5;
        int maximum = 20;
        return (int) Math.floor(Math.random() * (maximum - minimum + 1) + minimum);
    }

    public synchronized void runSimulation() throws InterruptedException {
        while (!flag) {
            // Sleep for a random time before trying to add a product
            TimeUnit.SECONDS.sleep(getRandomTime());
            
            // Check if there are products to process
            if (productIn > 0) {
                Queue queue = simulate.firstQueue; // Get the first queue by ID
                if (queue != null) {
                    saveState();
                    // Add a new product to the queue
                    queue.addProduct(new Product());
                    productIn--;  // Decrement the product count
                    project.setProductIn(productIn);  // Update the product count in the project
                    System.out.println("Queue products: " + simulate.getFirstQueue().getProducts());
                    notifyWebSocketHandler();  // Notify the client via WebSocket
                } else {
                    System.err.println("Queue with ID '1' does not exist!");
                }
            }
        }
        flag = false;
    }
    public void stopSimulation() {
        this.productIn = 0;
        this.flag = true;
        notifyWebSocketHandler();
    }

    public void start() {
        this.flag = false;
        notifyWebSocketHandler();
    }

    public Map<String, Queue> getQueues() {
        return queues;
    }

    public void setQueues(Map<String, Queue> queues) {
        this.queues = queues;
        notifyWebSocketHandler();
    }

    public Map<String, Machine> getMachines() {
        return machines;
    }

    public void setMachines(Map<String, Machine> machines) {
        this.machines = machines;
        notifyWebSocketHandler();
    }

    public static ServiceSimulation getSimulate() {
        return simulate;
    }

    public static void setSimulate(ServiceSimulation simulate) {
        ServiceSimulation.simulate = simulate;
    }

    public Queue getLastQueue() {
        return lastQueue;
    }

    public void setLastQueue(Queue lastQueue) {
        this.lastQueue = lastQueue;
        notifyWebSocketHandler();
    }

    public Queue getFirstQueue() {
        return firstQueue;
    }

    public void setFirstQueue(Queue firstQueue) {
        this.firstQueue = firstQueue;
        notifyWebSocketHandler();
    }

    private void notifyWebSocketHandler() {
        if (webSocketHandler != null) {
            try {
                webSocketHandler.broadcastUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}