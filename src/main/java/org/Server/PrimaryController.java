package org.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.lang.String.*;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PrimaryController{
    HashMap<String, String> cityTemp;
    @FXML
    Label informationFromServer;
    public void initialize() {
        /**
         * Словарь городов
         */
        cityTemp = new HashMap<String, String>();
        cityTemp.put("Воронеж", "28");
        cityTemp.put("Липецк", "30");
        cityTemp.put("Москва", "32");
        cityTemp.put("Киев", "34");
        informationFromServer.setText(cityTemp.toString() + "\n");
    }

    public void switchToStartServer(ActionEvent actionEvent) {
            while (true) {
                try {
                    System.out.println("asd");
                    ServerSocket server = new ServerSocket(8001);
                    /**
                     *Создание сокета для подключения
                     */
                    informationFromServer.setText(informationFromServer.getText() + "\nСервер запущен");
                    /**
                     * Ожидание подключения
                     */
                    informationFromServer.setText(informationFromServer.getText() + "\nОжидание подключения");
                    Socket socket = server.accept();

                    /**
                     * Поток для отправки сообщения
                     */
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())
                    );
                    /**
                     * Поток для получения сообщения
                     */
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));

                    /**
                     * messageClient строка полученная от клиента
                     */
                    String messageClient = reader.readLine();
                    informationFromServer.setText(informationFromServer.getText() + "Получение сообщения от клиента" + messageClient);
                    if (cityTemp.get(messageClient) != null) {
                        writer.write("Температура в городе " + messageClient + " равна " + cityTemp.get(messageClient));
                        writer.newLine();
                        writer.flush();
                    } else {
                        writer.write("Данного города нет");
                        writer.newLine();
                        writer.flush();
                        String newTempCity = reader.readLine();
                        String[] massive = newTempCity.split(" ");
                        cityTemp.put(massive[0], massive[1]);
                    }

                    /**
                     * Закрытие потоков
                     */
                    socket.close();
                    reader.close();
                    writer.close();
                    server.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }
    public void switchToStopServer(ActionEvent actionEvent) {

    }
}
