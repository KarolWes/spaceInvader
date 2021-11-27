package com.example.spaceinvader;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Bonus extends Movable implements Hitting{
    private int speed;
    private int value;
    @FXML
    private ImageView skin;
    private int x;
    private int y;
    private boolean generated = false;
    private List<String>fillings;

    public Bonus(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;

        fillings = new ArrayList<>();
        fillings.add("src/main/resources/com/example/spaceinvader/bonus_shield.png"); // shield
        fillings.add("src/main/resources/com/example/spaceinvader/bonus_double.png"); // shield
        fillings.add("src/main/resources/com/example/spaceinvader/bonus_time.png"); // shield
        fillings.add("src/main/resources/com/example/spaceinvader/bonus_heart.png"); // shield
        fillings.add("src/main/resources/com/example/spaceinvader/bonus_coin.png"); // shield
        value = (int)(Math.random()*5);
        skin = new ImageView((new File(fillings.get(value)).toURI().toString()));
        skin.setX(this.x+0.5*skin.getFitHeight());
        skin.setY(this.y+0.5*skin.getFitWidth());
    }

    public void move(){
        List<Integer> pos = move(0, speed, x, y);
        x = pos.get(0);
        y = pos.get(1);
        skin.setX(this.x+0.5*skin.getFitHeight());
        skin.setY(this.y+0.5*skin.getFitWidth());
    }

    @Override
    public boolean hit(Node target, Node myself) {
        if(myself.intersects(target.getBoundsInLocal())){
            return true;
        }
        else{
            return false;
        }
    }

    public int getValue() {
        return value;
    }

    public boolean isGenerated() {
        return generated;
    }

    public ImageView getSkin() {
        return skin;
    }

    public void setGenerated(boolean generated) {
        this.generated = generated;
    }
}
