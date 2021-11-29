package com.example.spaceinvader.crafts;

import com.example.spaceinvader.Bullet;
import javafx.scene.image.Image;

import java.io.File;

public class Destroyer extends Alien{
    public Destroyer(int x, int y) {
        super(25, 7, (int)(Math.random()*3+2), x, y, new Image(new File("src/main/resources/com/example/spaceinvader/raider72.png").toURI().toString()));
        generateMoves();
    }

    @Override
    public void attack() {
        fired.add(new Bullet(5, 15,this.getX()+this.getxSize()/2, this.getY()+this.getySize(),-30, flag ));
        fired.add(new Bullet(5, 15,this.getX()+this.getxSize()/2, this.getY()+this.getySize(),30, flag ));
        fired.add(new Bullet(5, 15,this.getX()+this.getxSize()/2, this.getY()+this.getySize(),0, flag ));
    }

    @Override
    public void generateMoves(){
        super.getMoves().add(0);
        super.getMoves().add(-1);
        super.getMoves().add(-1);
        super.getMoves().add(270);
        super.getMoves().add(270);
        super.getMoves().add(270);
        super.getMoves().add(270);
        super.getMoves().add(-1);
        super.getMoves().add(90);
        super.getMoves().add(90);
        super.getMoves().add(90);
        super.getMoves().add(90);
        super.getMoves().add(90);
        super.getMoves().add(90);
        super.getMoves().add(90);
        super.getMoves().add(90);
        super.getMoves().add(-1);
        super.getMoves().add(270);
        super.getMoves().add(270);
        super.getMoves().add(270);
        super.getMoves().add(270);
    }
}
