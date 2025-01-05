import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Client connected");

        // Example data
        String machines = "[{\"id\":1,\"color\":\"red\"},{\"id\":2,\"color\":\"blue\"}]";
        String queues = "[{\"id\":1,\"noOfProducts\":10},{\"id\":2,\"noOfProducts\":5}]";

        // Send data to the client
        session.sendMessage(new TextMessage("{\"machines\":" + machines + ",\"queues\":" + queues + "}"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    System.out.println("Client disconnected");
  }
}