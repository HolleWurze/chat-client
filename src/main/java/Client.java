import lombok.SneakyThrows;

import java.net.Socket;

public class Client {
    private final static String IP = "localhost";
    private final static int PORT = 8080;


    @SneakyThrows
    public void start() {
        Socket socket = new Socket(IP, PORT);

        if (socket.isConnected()) {
            MessageSender messageSender = new MessageSender(socket.getOutputStream());
            MessageReceiver messageConsoleReceiver = new MessageReceiver(System.in);
            registrationOrAuthorization(messageConsoleReceiver, messageSender);

            new Thread(new SocketRunnable(socket)).start();

            String messageFromConsole;
            while ((messageFromConsole = messageConsoleReceiver.readMessage()) != null) {
                messageSender.sendMessage(messageFromConsole);
            }
        }
    }

    public void registrationOrAuthorization(MessageReceiver messageReceiver, MessageSender messageSender) {
        System.out.println("---------------------------");
        System.out.println("Добро пожаловать в наш чат!");
        System.out.println("---------------------------");
        System.out.println("Нажмите \n -1 для авторизации  \n -2 для регистрации");

        int numFromConsole = Integer.parseInt(messageReceiver.readMessage().trim());
        if (numFromConsole == 1) {
            String name = getString(messageReceiver, "Введите имя").toUpperCase();
            String password = getString(messageReceiver, "Введите пароль");
            messageSender.sendMessage("Authorization " + name + " " + password);
        } else if (numFromConsole == 2) {
            String name = getString(messageReceiver, "Введите имя").toUpperCase();
            String password = getString(messageReceiver, "Введите пароль");
            messageSender.sendMessage("Registration " + name + " " + password);
        } else {
            System.err.println("Введите 1 или 2");
            registrationOrAuthorization(messageReceiver, messageSender);
        }

    }

    private String getString(MessageReceiver messageReceiver, String s) {
        System.out.println(s);
        return messageReceiver.readMessage().trim();
    }
}

