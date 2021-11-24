package com.example.spaceinvader.crafts;

import com.example.spaceinvader.Bullet;
import com.example.spaceinvader.HealthBar;
import com.example.spaceinvader.Movable;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Spacecraft extends Movable {

    protected int live;
    protected int x;
    protected int y;
    protected final int flag; // 1 - player; 0 - alien;
    protected List<Bullet> fired;
    @FXML
    protected Image skin;
    @FXML
    protected ImageView skinLoc;
    protected HealthBar hp;

    public int getxSize() {
        double xSize = this.skin.getWidth();
        return (int)xSize;
    }

    public int getySize() {
        double ySize = this.skin.getHeight();
        return (int)ySize;
    }

    protected Spacecraft(int live, int x, int y, int flag,  Image skin) {
        this.x = (int) (x-skin.getWidth()/2);
        this.y = y;
        this.live = live;
        this.flag = flag;
        this.skin =skin;
        this.skinLoc = new ImageView(skin);
        this.skinLoc.setX(this.x);
        this.skinLoc.setY(this.y);

        fired = new ArrayList<>();
        hp = new HealthBar(live, this.getX()+this.getxSize()/2-30, this.y - 50, Color.RED);
    }

    public int getLive() {
        return live;
    }

    public boolean hurt(int dmg){
        live -= dmg;
        hp.reduceHealth(dmg);
        if(live <= 0){
            return true;
        }
        return false; //true if dies

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ImageView getSkinLoc() {
        return skinLoc;
    }

    public List<Bullet> getFired() {
        return fired;
    }

    public Bullet attack(){
        return attack(this.x, this.y);
    }
    public Bullet attack(int x, int y){
        Bullet b = new Bullet(5, 15, x, y, flag);
        fired.add(b);
        return b;
    }

    public void move(int dir, double dist) {
        List<Integer> pos = move(dir, dist, x, y);
        hp.move(x-pos.get(0),y-pos.get(1));
        x = pos.get(0);
        y = pos.get(1);
        skinLoc.setX(x);
        skinLoc.setY(y);

    }
    public void die(){
        for(Bullet b: fired){
            b.getSkin().setVisible(false);
        }
        getFired().clear();
        this.skinLoc.setVisible(false);
        this.hp.getSkin().setVisible(false);
    }

    public HealthBar getHp() {
        return hp;
    }
}
