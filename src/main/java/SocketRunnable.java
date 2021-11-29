import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.net.Socket;

@AllArgsConstructor
public class SocketRunnable implements Runnable{
    private final Socket socket;

    @SneakyThrows
    @Override
    public void run() {
        MessageReceiver receiverFromServer = new MessageReceiver(socket.getInputStream());

        String messageFromServer;
        String userName = "";
        if ((messageFromServer = receiverFromServer.readMessage()).contains(" failed")) {
            System.out.println(messageFromServer);
            System.exit(0);
        } else {
            System.out.println(messageFromServer);
            userName = messageFromServer.split(" ")[2];
        }
        while ((messageFromServer = receiverFromServer.readMessage()) != null) {
            if (messageFromServer.split(" ")[1].charAt(0) == '@' && messageFromServer.toUpperCase().contains(userName)) {
                System.out.println(messageFromServer);
            } else if (messageFromServer.split(" ")[1].charAt(0) != '@') {
                System.out.println(messageFromServer);
            }
        }
    }
}
