package com.example.spaceinvader.crafts;

import com.example.spaceinvader.Bullet;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.File;

public class Player extends Spacecraft {

    private int instance = 0;
    private boolean doubleshot;
    private boolean shielded;
    private int smallCooldown;
    private int sCdLimit;
    private double speed;
    @FXML
    private Circle shield;
    public Player(int live, int x, int y) {
        super(live, x, y, 1, new Image(new File("src/main/resources/com/example/spaceinvader/player72.png").toURI().toString()));
        hp.setY(this.y+75);
        smallCooldown = 0;
        sCdLimit = 10;
        speed = 10;
        doubleshot = false;
        shielded = false;
        shield = new Circle();
        shield.setRadius(40);
        shield.setCenterX(this.x+this.getxSize()/2);
        shield.setCenterY(this.y + this.getySize()/2);
        shield.setFill(Color.TEAL);
        shield.setOpacity(0.7);
        shield.setVisible(false);
    }

    public int getInstance() {
        return instance;
    }

    public int getSmallCooldown() {
        return smallCooldown;
    }
    public void decreaseSCd() {
        smallCooldown--;
    }

    @Override
    public Bullet attack() {
        instance ^= 1;
        smallCooldown = sCdLimit;
        //return super.attack(600, this.getY()-20);
        return super.attack(this.getX()+instance*this.getxSize(), this.getY()-20);
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isDoubleshot() {
        return doubleshot;
    }

    public void setDoubleshot(boolean doubleshot) {
        this.doubleshot = doubleshot;
    }

    public boolean isShielded() {
        return shielded;
    }

    public void setShielded(boolean shielded) {
        this.shielded = shielded;
        shield.setVisible(shielded);
    }

    public Circle getShield() {
        return shield;
    }

    @Override
    public void move(int dir, double dist) {
        super.move(dir, dist);
        shield.setCenterX(this.x+this.getxSize()/2);
        shield.setCenterY(this.y + this.getySize()/2);
    }
    public void regenerate(){
        this.live = hp.getMaxHealth();
        hp.reduceHealth((-1)*hp.getMaxHealth());
    }
}
