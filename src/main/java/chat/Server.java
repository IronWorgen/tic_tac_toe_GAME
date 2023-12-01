package chat;

import lombok.Data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Data
public class Server {
    private String address = "1.1.1.1";
    private Repository repository ;
    public boolean serverStatus = true;

    public Server() {
        try {
            this.repository = new fileRepo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Map<String ,String > getMessages() throws IOException {
        return repository.getMessages();
    }

    public void sendMessage(Map<String ,String > message){
        repository.saveMessages(message);
    }

}
