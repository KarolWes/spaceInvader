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
        scene.setOnKeyReleased(controller.handleKeyRelesed);

        stage.setTitle("Space Invader 2.0");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {

//        Spacecraft ship = new Spacecraft(10, scene_x/2, scene_y-50);
//        System.out.println(ship.getLive());
//        ship.move(90, 5);
//        ship.move(270, 10);
//        ship.hurt(3);
//        System.out.println(ship.getLive());
//        ship.hurt(10);
//        System.out.println(ship.getLive());
//
//
//        Bullet bullet = new Bullet(2, 15, 0, 0);
//        bullet.move();
//        bullet.move();
//        bullet.move();

        launch();
    }
}