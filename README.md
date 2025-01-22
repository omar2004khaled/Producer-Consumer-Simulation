# Producer-Consumer-Simulation
# **Producer-Consumer Simulation**  

## **Overview**  
A Java Spring Boot & React-based simulation of an assembly line with machines (producers/consumers) and queues, using concurrency, observer, and snapshot patterns.  

### **Key Features**  
- **Concurrent Processing**: Machines process items in parallel.  
- **Undo/Redo & Save/Load**: Manage and resume simulations.  
- **Visual Indicators**: Machines change color during processing.  

## **How to Run**  
1. Clone the repo:  
   ```
   git clone https://github.com/omar2004khaled/Producer-Consumer-Simulation.git  
   ```  
2. **Run Backend** in an IDE.  
3. **Run Frontend**:  
   ```
   npm install  
   npm run dev  
   ```

## **Design Patterns**  
- **Observer**: Machines notify queues.  
- **Concurrency**: Parallel processing.  
- **Factory**: Creates machines/queues dynamically.  
- **Snapshot**: Saves/restores states.  
- **Singleton**: Manages a single instance.  

## **User Guide**  
- **Control Panel**: Add queues/machines, start/stop, undo/redo, save/load.  
- **Rules**:  
  - Must start & end with a queue.  
  - No self-links or similar type connections.  

[**UML Diagram**](https://www.figma.com/board/CIyYvt7PkoY4VOvDyXGKSu/Producer-Consumer-Simulation?node-id=0)  

