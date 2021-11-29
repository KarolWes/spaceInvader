package com.example.spaceinvader;

import com.example.spaceinvader.crafts.Spacecraft;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class Bullet extends Movable implements Hitting{
    private final int damage;
    private final double speed;
    private int x;
    private int y;
    private double angle;
    private final int xSize;
    private final int ySize;
    private boolean generated;
    @FXML
    private Rectangle skin;

    private final int shooter; // 1 if player; 0 if alien

    public int getDamage() {
        return damage;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getSpeed() {
        return speed;
    }

    public Bullet(int damage, double speed, int x, int y, double angle, int shooter) {
        this(damage, speed, x,y, shooter);
        this.angle = angle;
        skin.setRotate(-angle);
    }

    public Bullet(int damage, double speed, int x, int y, int shooter) {
        this.generated = false;
        this.damage = damage;
        this.speed = speed;
        this.x = x;
        this.y = y;
        this.shooter = shooter;
        this.angle = 0;
        xSize = 3;
        ySize = 20;
        skin = new Rectangle();
        skin.setY(y);
        skin.setX(x);
        skin.setWidth(xSize);
        skin.setHeight(ySize);
        skin.setRotate(angle);
        if (shooter== 1) {
            skin.setFill(Color.GREEN);
        } else {
            skin.setFill(Color.HOTPINK);
        }
    }

    public boolean isGenerated() {
        return generated;
    }

    public void setGenerated(boolean generated) {
        this.generated = generated;
    }

    public Rectangle getSkin() {
        return skin;
    }

    public Spacecraft hit(List<Spacecraft> targets)
    {
        for (Spacecraft craft: targets)
        {
                if (hit(craft.getSkinLoc(), this.skin)) {
                    return craft;
                }
        }
        return null;
    }

    public void move()
    {
        move(180*shooter);
    }

    public void move(double dir){
        List<Integer> pos = move((int)(angle), speed, x, y);
        x = pos.get(0);
        y = pos.get(1);
        skin.setX(x);
        skin.setY(y);
    }

    @Override
    public boolean hit(Node target, Node myself) {
        {
            if(myself.intersects(target.getBoundsInLocal())){
                System.out.println("BOOM!");
                return true;
            }
            return false;
        }
    }
}
