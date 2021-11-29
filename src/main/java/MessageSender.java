

import lombok.AllArgsConstructor;

import java.io.OutputStream;
import java.io.PrintWriter;

@AllArgsConstructor
public class MessageSender {
    private final OutputStream outputStream;
    private final PrintWriter printWriter;

    public MessageSender(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.printWriter = new PrintWriter(outputStream);
    }

    public void sendMessage(String message){
        printWriter.println(message);
        printWriter.flush();
    }
}
