package com.example.spaceinvader.crafts;

import com.example.spaceinvader.Explosion;
import javafx.scene.image.Image;

import java.io.File;

public class Bomb extends Alien{
    private Explosion explosion;

    public Bomb(int x, int y) {
        super(15, 10, 0, x, y, new Image(new File("src/main/resources/com/example/spaceinvader/invader72.png").toURI().toString()));
        generateMoves();
    }

    @Override
    protected void generateMoves() {
        super.getMoves().add(-1);
        super.getMoves().add(-1);
        super.getMoves().add(0);
    }

    @Override
    public void die() {
        explosion = new Explosion((int)(this.x + this.skinLoc.getFitWidth()/2), (int)(this.y - this.skinLoc.getFitHeight()/2));
    }

    public Explosion getExplosion() {
        return explosion;
    }

    @Override
    public void move(int i) {
        if(live > 0){
            super.move(i);
        }
        else {
            explosion.resize();
        }

    }
}
