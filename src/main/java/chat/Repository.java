package chat;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Repository {
    /**
     * получить историю сообщений с сервера
     * @return имя пользователя - сообщение
     */
    Map<String, String> getMessages() throws IOException;

    /**
     * создать сообщение
     * @param newMessage пользователь - сообщение
     */
    void saveMessages(Map<String, String> newMessage);

}
