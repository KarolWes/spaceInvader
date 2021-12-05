package com.example.spaceinvader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static int scene_x = 1200;
    public static int scene_y = 800;
    public static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        scene = new Scene(fxmlLoader.load(), scene_x, scene_y);
        Controller controller = fxmlLoader.getController();
        scene.setOnKeyPressed(controller.handleKeyPressed);
        scene.setOnKeyReleased(controller.handleKeyReleased);
        stage.setTitle("Space Invader 2.0");
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void stop(){
    }

    public static void main(String[] args) {
        launch();
    }
}