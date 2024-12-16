package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    public static void main(String[] args) {
        int port = 8080; // Порт сервера
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Ждем подключения
                System.out.println("Новое соединение принято");

                try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    // 1. Запрос имени
                    out.println("Write your name:");
                    String name = in.readLine();
                    if (name == null) {
                        System.out.println("Клиент закрыл соединение");
                        continue;
                    }
                    System.out.println("Клиент представился: " + name);

                    // 2. Вопрос о возрасте с повторением
                    while (true) {
                        out.println("Are you child? (yes/no)");
                        String isChild = in.readLine();
                        if (isChild == null) {
                            System.out.println("Клиент закрыл соединение");
                            break;
                        }
                        System.out.println("Клиент ответил: " + isChild);

                        if ("yes".equalsIgnoreCase(isChild)) {
                            out.println("Welcome to the kids area, " + name + "! Let's play!");
                            break;
                        } else if ("no".equalsIgnoreCase(isChild)) {
                            out.println("Welcome to the adult zone, " + name + "! Have a good rest, or a good working day!");
                            break;
                        } else {
                            out.println("Wrong answer! Try again.");
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Ошибка в обработке соединения: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Не удалось запустить сервер: " + e.getMessage());
        }
    }
}
