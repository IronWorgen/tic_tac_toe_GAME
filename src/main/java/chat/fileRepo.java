package chat;

import lombok.Data;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class fileRepo implements Repository{
    private final Path filePath= Paths.get("Messages.txt");

    public fileRepo() throws IOException {
       if(!Files.exists(filePath)){
           Files.createFile(filePath);
       }
    }

    @Override
    public Map<String, String> getMessages() throws IOException {
        Stream<String> lines = Files.lines(filePath, StandardCharsets.UTF_8);

        return lines.collect(Collectors.toMap(
                e->{
                    String[] arr = e.split(":");
                    return String.join(" ", Arrays.copyOfRange(arr, 0,3));
                },
                e-> {
                    String[] arr = e.split(":");
                    return String.join(" ", Arrays.copyOfRange(arr, 3,arr.length));
                }));
    }

    @Override
    public void saveMessages(Map<String, String> newMessage) {
        for (Map.Entry<String, String> entry : newMessage.entrySet()) {
            java.time.LocalDate currentDate = java.time.LocalDate.now();
            java.time.LocalTime currentTime = java.time.LocalTime.now();
            try {
                Files.writeString(filePath, currentDate+" "+currentTime+" "+entry.getKey()+": "+entry.getValue() +"\n", StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
