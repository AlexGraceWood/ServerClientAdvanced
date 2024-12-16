package client;

import java.io.*;
import java.net.Socket;

public class ClientApp {
    public static void main(String[] args) {
        String host = "netology.homework"; // Новый адрес подключения
        int port = 8080; // Порт сервера

        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            // 1. Сервер: Write your name
            System.out.println("Сервер: " + in.readLine());
            String name = console.readLine();
            out.println(name); // Отправляем имя серверу

            while (true) {
                // 2. Сервер задаёт вопрос: Are you child? (yes/no)
                String serverQuestion = in.readLine();
                if (serverQuestion == null || serverQuestion.startsWith("Welcome")) {
                    // Завершаем диалог, если сервер отправил финальное сообщение
                    System.out.println("Сервер: " + serverQuestion);
                    break;
                }
                System.out.println("Сервер: " + serverQuestion);

                // Читаем ответ клиента
                System.out.print("Ваш ответ: ");
                String clientResponse = console.readLine();
                out.println(clientResponse); // Отправляем серверу

                // 3. Сервер отвечает в зависимости от ответа
                String serverResponse = in.readLine();
                if (serverResponse == null) {
                    System.out.println("Сервер разорвал соединение.");
                    break;
                }
                System.out.println("Сервер: " + serverResponse);
            }
        } catch (IOException e) {
            System.err.println("Ошибка подключения: " + e.getMessage());
        }
    }
}
