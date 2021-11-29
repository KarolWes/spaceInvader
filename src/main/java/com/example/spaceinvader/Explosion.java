package com.example.spaceinvader;

import javafx.scene.Node;
import javafx.scene.shape.Circle;

public class Explosion implements Hitting{
    private int x;
    private int y;
    private Circle skin;
    private int radius;
    private int lim_radius = 100;
    private int dmg;
    private boolean expired = false;


    public Explosion(int x, int y) {
        this.x = x;
        this.y = y;
        this.dmg = 20;
        this.radius = 20;
        this.skin = new Circle();
        skin.setRadius(radius);
        skin.setCenterX(this.x);
        skin.setCenterY(this.y);
        skin.setOpacity(0.5);
    }

    @Override
    public boolean hit(Node target, Node myself) {
        if(getSkin().intersects(target.getBoundsInLocal())){
            return true;
        }
        else{return false;}
    }

    public void resize(){
        if(radius < lim_radius){
            radius+=10;
            expired = false;
        }
        else{
            skin.setVisible(false);
            expired = true;
        }
    }

    public Circle getSkin() {
        return skin;
    }

    public boolean isExpired() {
        return expired;
    }
}
