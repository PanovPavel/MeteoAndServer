package org.example;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;

public class ClientController {
    @FXML
    TextField CitiTextField;
    @FXML
    Label getAnswerCity;
    public void SpendCitiWithClientOnServer(ActionEvent actionEvent) {
        try {
            /*Подключение к сокету*/
            Socket socket = new Socket("localhost", 8001);
            /*Поток отправки сообщения на сервер*/
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );
            /*Получение сообщения с потока*/
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            writer.write(CitiTextField.getText());
            writer.newLine();
            writer.flush();
            /*Получение ответа с сервера*/
            String messageServer = reader.readLine();
            System.out.println(messageServer);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    getAnswerCity.setText(messageServer);
                }
            });
            if (messageServer.equals("Данного города нет")){
                writer.write(CitiTextField + " 28");
                writer.flush();
                writer.close();
            }
            writer.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
