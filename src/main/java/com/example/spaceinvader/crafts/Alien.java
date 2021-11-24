package com.example.spaceinvader.crafts;

import com.example.spaceinvader.Bullet;
import com.example.spaceinvader.Hitting;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Alien extends Spacecraft implements Hitting {
    private double speed;
    private double mainSpeed;
    private int freq;
    private int orderCount;
    private boolean generated;
    private List <Integer> moves;

    public Alien(int live, double speed, int freq, int x, int y, Image skin) {
        super(live, x, y, 0, skin);
        this.speed = speed;
        mainSpeed = speed;
        this.freq = freq;
        this.moves = new ArrayList<>();
        this.generated = false;
        hp.setFill(Color.LIME);
    }

    public boolean isGenerated() {
        return generated;
    }

    public void setGenerated(boolean generated) {
        this.generated = generated;
    }

    protected void generateMoves(){
        return;
    }

    protected List<Integer> getMoves() {
        return moves;
    }

    public int getFreq() {
        return freq;
    }

    public void move(int i) {
        int nump = (int) (Math.random()*100);
        if (nump < freq)
        {
            attack();
        }
        super.move(moves.get(i), this.speed);

    }
    public int getOrderCount()
    {
        return moves.size();
    }

    @Override
    public Bullet attack() {
        return super.attack(this.getX()+this.getxSize()/2, this.getY()+this.getySize());
    }

    @Override
    public boolean hit(Node target, Node myself) {
        {
            if(myself.intersects(target.getBoundsInLocal())){
                return true;
            }
            return false;
        }
    }

    public void multipleSpeed(double v){
        this.speed=v*mainSpeed;
    }
}
