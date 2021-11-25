package com.example.spaceinvader;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class Bonus extends Movable implements Hitting{
    private int speed;
    private int value;
    @FXML
    private Circle skin;
    private int x;
    private int y;
    private boolean generated = false;
    private List<Color>fillings;

    public Bonus(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        value = (int)(Math.random()*5);
        fillings = new ArrayList<>();
        fillings.add(Color.BLUE); // shield
        fillings.add(Color.SILVER); // double shot
        fillings.add(Color.BROWN); // slowdown
        fillings.add(Color.RED); // add hearts
        fillings.add(Color.ORANGE); // coin
        skin = new Circle();
        skin.setRadius(12.0);
        skin.setCenterX(x+skin.getRadius());
        skin.setCenterY(y+skin.getRadius());
        skin.setFill(fillings.get(value));
    }

    public void move(){
        List<Integer> pos = move(0, speed, x, y);
        x = pos.get(0);
        y = pos.get(1);
        skin.setCenterX(x+skin.getRadius());
        skin.setCenterY(y+skin.getRadius());
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

    public Circle getSkin() {
        return skin;
    }

    public void setGenerated(boolean generated) {
        this.generated = generated;
    }
}
