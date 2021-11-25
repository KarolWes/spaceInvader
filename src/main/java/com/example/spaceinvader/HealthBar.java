package com.example.spaceinvader;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HealthBar {
    private final int maxHealth;
    private int actHealth;
    private final double width = 60.0;

    @FXML
    private Rectangle skin;
    private int x;
    private int y;

    public HealthBar(int maxHealth, int x, int y, Color fill) {
        this.maxHealth = maxHealth;
        this.actHealth = maxHealth;
        this.skin = new Rectangle();
        this.x = x;
        this.y = y;
        this.skin.setX(this.x);
        this.skin.setY(this.y);
        this.skin.setHeight(10);
        this.skin.setWidth(width);
        this.skin.setFill(fill);
    }

    public Rectangle getSkin() {
        return skin;
    }

    public double getWidth() {
        return width;
    }

    public void reduceHealth(int dmg){
        double r;
        if(this.actHealth > dmg)
        {
            this.actHealth-=dmg;
            r = dmg*1.0/maxHealth*width;
            this.skin.setWidth(width*actHealth/maxHealth);
            move((int)(-r/2), 0);

        }else{
            this.skin.setVisible(false);
        }
    }
    public void move(int dist_x, int dist_y){
        this.x = x-dist_x;
        this.y = y-dist_y;
        this.skin.setX(x);
        this.skin.setY(y);

    }


    public int getY() {
        return y;

    }

    public void setY(int y) {
        this.y = y;
        this.skin.setY(y);
    }
    public void setFill(Color color)
    {
        this.skin.setFill(color);
    }

    public int getMaxHealth() {
        return maxHealth;
    }
    public void resetMax(){
        double r = (maxHealth-actHealth)*1.0/maxHealth*width;
        this.actHealth = maxHealth;
        this.skin.setWidth(width);
        move((int)(-r/2),0);
    }
}
