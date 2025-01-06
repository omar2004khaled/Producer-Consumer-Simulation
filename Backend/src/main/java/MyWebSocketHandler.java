import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.producerConsumer.Backend.Service.simulation.ServiceSimulation;
import com.producerConsumer.Backend.Service.Model.Machine;
import com.producerConsumer.Backend.Service.Model.Queue;

import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    private final ServiceSimulation simulation = ServiceSimulation.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Client connected");

        // Get the current state of machines and queues
        Map<String, Machine> machinesMap = simulation.getMachines();
        Map<String, Queue> queuesMap = simulation.getQueues();

        // Convert machines and queues to JSON
        String machinesJson = objectMapper.writeValueAsString(machinesMap.values());
        String queuesJson = objectMapper.writeValueAsString(queuesMap.values().stream()
                .map(queue -> Map.of("id", queue.getId(), "noOfProducts", queue.getProducts().size()))
                .collect(Collectors.toList()));

        // Send data to the client
        session.sendMessage(new TextMessage("{\"machines\":" + machinesJson + ",\"queues\":" + queuesJson + "}"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Client disconnected");
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Handle incoming messages if needed
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println("WebSocket error: " + exception.getMessage());
    }
}