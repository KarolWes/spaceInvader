package com.example.spaceinvader;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class EndDialog {

    @FXML
    private Button okButton;
    @FXML
    private TextField nameField;
    private String name;
    private int p;

    public void initialize(){
        name = "";
        p = -1;
    }
    public void initData(int points){
        this.p = points;
    }

    public void handleOnClicked(MouseEvent mouseEvent) {
        name = nameField.getText();
        System.out.println("Player " + name + " gets " + p + " points");
        Stage thisStage = (Stage) okButton.getScene().getWindow();
        thisStage.close();
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("LeaderBoard.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            LeaderBoard leaderBoardController = fxmlLoader.getController();
            leaderBoardController.initData(p, name);
            Stage stage = new Stage();
            stage.setTitle("Leader board");
            stage.setScene(scene);
            stage.show();

        }catch (IOException e)
        {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
    }
}
